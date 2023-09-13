package com.lt.win.service.base;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.server.CoinLogService;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

import static com.lt.win.service.cache.KeyConstant.CURRENCY_PRICE;

/**
 * @Auther: wells
 * @Date: 2022/8/9 00:09
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCoinBase {
    private final UserWalletService userWalletServiceImpl;
    private final CoinLogService coinLogServiceImpl;
    private final UserService userServiceImpl;
    private final JedisUtil jedisUtil;
    private final NoticeBase noticeBase;
   // private final EsSaveBase esSaveBase;

    /**
     * 通过UID获取账户余额
     *
     * @param uid uid
     * @return res
     */
    public BigDecimal getUserCoin(Integer uid) {
        var wallet = userWalletServiceImpl.getById(uid);
        return Optional.ofNullable(wallet).isPresent() ? wallet.getCoin().setScale(4, RoundingMode.DOWN) : BigDecimal.ZERO;
    }


    /**
     * 通过UID获取账户余额
     *
     * @param uid uid
     * @return res
     */
    public UserWallet getUserWallet(Integer uid) {
        return userWalletServiceImpl.lambdaQuery()
                .eq(UserWallet::getId, uid)
                .oneOpt().orElseThrow(() -> new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID));
    }

    /**
     * @return boolean
     * @Description 用户金额变化
     * @Param [reqDto]
     **/
    public boolean userCoinChange(UserCoinChangeParams.UserCoinChangeReq reqDto) {
        log.info("记录主键冲突问题  {}   {}", reqDto.getReferId(), reqDto.getCategory());
        log.info("用户金额变化入参{}", JSONUtil.toJsonStr(reqDto));
        int outIn = Optional.ofNullable(reqDto.outIn).orElse(0);
        var now = DateUtils.getCurrentTime();
        BigDecimal updateCoin = reqDto.coin;
        if (outIn == 0) {
            updateCoin = updateCoin.abs().negate();
        }
        var userWallet = userWalletServiceImpl.lambdaQuery()
                .eq(UserWallet::getId, reqDto.getUid())
                .one();
        log.info("金额查询：{}", JSONUtil.toJsonStr(userWallet));
        if (Objects.isNull(userWallet)) {
            throw new BusinessException(CodeInfo.COIN_NOT_ENOUGH);
        }
        BigDecimal coinBefore = userWallet.getCoin();
        String username = userWallet.getUsername();
        //支出余额大于用户余额则不能扣款

        if (outIn == 0 && NumberUtil.isGreater(reqDto.coin.abs(),coinBefore)) {
            throw new BusinessException(CodeInfo.COIN_NOT_ENOUGH);
        }
        BigDecimal coinAfter = coinBefore.add(updateCoin);
        //修改用户金额
        updateConcurrentUserCoin(updateCoin, reqDto.getUid());
        // 解决并发等待问题
        if (reqDto.getCoin().compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }

        //日志入库
        CoinLog coinLog = new CoinLog();
        coinLog.setUid(reqDto.getUid());
        coinLog.setUsername(username);
        coinLog.setReferId(reqDto.getReferId());
        //日志类型
        coinLog.setCategory(reqDto.getCategory());
        coinLog.setCoin(reqDto.getCoin().abs());
        coinLog.setCoinBefore(coinBefore);
        coinLog.setCoinAfter(coinAfter);
        coinLog.setStatus(1);
        coinLog.setExternalTxId(reqDto.getExternalTxId());
        coinLog.setCoinReal(reqDto.getCoinReal());
        coinLog.setPlatId(reqDto.getPlatId());
        coinLog.setGameId(reqDto.getGameId());
        coinLog.setOutIn(outIn);
        coinLog.setCreatedAt(now);
        coinLog.setUpdatedAt(now);
        boolean flag = coinLogServiceImpl.save(coinLog);
        if (flag) {
         //   esSaveBase.saveAndES(coinLog, coinAfter);
        }
        noticeBase.coinChange(reqDto.getUid(), coinAfter);
        return flag;
    }

    /**
     * @return void
     * @Description 修改佣金金额
     * @Param [reqDto]
     **/
    public boolean changeCommCoin(UserCoinChangeParams.UserCoinChangeReq reqDto) {
        int outIn = Optional.ofNullable(reqDto.outIn).orElse(0);
        //佣金扣款
        User user = userServiceImpl.getById(reqDto.getUid());
        if (Objects.isNull(user)) {
            throw new BusinessException(CodeInfo.COIN_NOT_ENOUGH);
        }
        BigDecimal coinBefore = user.getCoinCommission();
        BigDecimal updateCoin = reqDto.coin;
        //支出余额大于用户余额则不能扣款
        if (outIn == 0 && reqDto.coin.abs().compareTo(coinBefore) > 0) {
            throw new BusinessException(CodeInfo.COIN_NOT_ENOUGH);
        }
        if (outIn == 0) {
            updateCoin = updateCoin.abs().negate();
        }
        var updateUserWrapper = new LambdaUpdateWrapper<User>();
        updateUserWrapper.setSql("coin_commission = coin_commission + " + updateCoin)
                .set(User::getUpdatedAt, DateUtils.getCurrentTime())
                .eq(User::getId, reqDto.getUid());
        return userServiceImpl.update(updateUserWrapper);
    }


    /**
     * @return void
     * @Description 修改钱包金额
     * @Param [updateCoin, uid, now, oldCoin]x
     **/
    public void updateConcurrentUserCoin(BigDecimal updateCoin, Integer uid) {
        var updateUserWrapper = new LambdaUpdateWrapper<UserWallet>();
        updateUserWrapper.setSql("coin = coin + " + updateCoin + ",version = version + 1")
                .set(UserWallet::getUpdatedAt, DateNewUtils.now())
                .set(UserWallet::getModifyAt, DateNewUtils.timestamp())
                .eq(UserWallet::getId, uid);
        if (updateCoin.compareTo(BigDecimal.ZERO) < 0) {
            updateUserWrapper.last("and  coin >= " + updateCoin.abs());
        }
        userWalletServiceImpl.update(updateUserWrapper);
    }


    /**
     * @return java.math.BigDecimal
     * @Description 通过币种获取汇率
     * @Param [currency] USDTBRL
     **/
    public BigDecimal getPriceByCurrencyCache(String currency) {
        BigDecimal price = BigDecimal.ZERO;
        try {
            String data = jedisUtil.get(CURRENCY_PRICE + ":" + currency);
            if (Strings.isEmpty(data)) {
                price = getPriceByCurrency(currency);
                jedisUtil.setex(CURRENCY_PRICE + ":" + currency, 180, price.toString());
            } else {
                price = new BigDecimal(data);
            }
        } catch (Exception e) {
            log.error("通过币种获取汇率异常:" + e.getMessage());
        }
        return price;
    }

    public BigDecimal getPriceByCurrency(String currency) {
        BigDecimal price = BigDecimal.ZERO;
        try {
            String url = "https://api.binance.com/api/v3/ticker/price";
            Map<String, Object> paramsMap = new TreeMap<>();
            paramsMap.put("symbol", currency);
            String response = HttpUtil.createGet(url).form(paramsMap).execute().body();
            log.info(response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            price = jsonObject.getBigDecimal("price");
            log.info("price =" + price);
        } catch (Exception e) {
            log.error("通过币种获取汇率异常:" + e.getMessage());
        }
        return price;
    }
}
