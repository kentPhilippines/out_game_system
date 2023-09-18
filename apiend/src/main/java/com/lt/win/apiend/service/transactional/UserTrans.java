package com.lt.win.apiend.service.transactional;

import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.dao.generator.service.UserWalletService;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.PasswordUtils;
import com.lt.win.utils.TextUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Random;

/**
 * @author: David
 * @date: 15/06/2020
 */
@Service
@Slf4j
public class UserTrans {
    Random random = new Random();
    @Resource
    private UserService userServiceImpl;
    @Resource
    private UserCache userCache;
    @Resource
    private ConfigCache configCache;
    @Resource
    private UserWalletService userWalletServiceImpl;

    /**
     * 用户注册
     *
     * @param dto dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(@NotNull UserParams.RegisterReqDto dto) {
        User agent = null;

        // 1.通过输入推广码开关 和 用户输入推广码查找上级记录,作为当前注册账号的直属上级
        var needInviteCode = configCache.registerInviteCode();
        if (needInviteCode == ConstData.ONE && Optional.ofNullable(dto.getPromoCode()).isEmpty()) {
            throw new BusinessException(CodeInfo.PROMO_CODE_INVALID);
        } else if (Optional.ofNullable(dto.getPromoCode()).isPresent()) {
            agent = userServiceImpl.lambdaQuery()
                    .eq(User::getPromoCode, dto.getPromoCode().toUpperCase())
                    .one();
            if (Optional.ofNullable(agent).isEmpty()) {
                throw new BusinessException(CodeInfo.PROMO_CODE_INVALID);
            }
        }

        // 插入User表数据
        int index = random.nextInt(9) + 1;
        int time = DateNewUtils.now();

        User userPo = new User();
        var promoCode = TextUtils.generateRandomString(6).toUpperCase();
        while (userServiceImpl.lambdaQuery().eq(User::getPromoCode, promoCode).one() != null) {
            promoCode = TextUtils.generateRandomString(6).toUpperCase();
        }
        userPo.setPromoCode(promoCode);
        userPo.setUsername(dto.getUsername().toLowerCase());
        userPo.setAvatar(String.format("/avatar/%s.png", index));
        userPo.setAreaCode(dto.getAreaCode());
        userPo.setMobile(generateChinesePhoneNumber());

        userPo.setEmail(dto.getEmail());
        userPo.setPasswordHash(PasswordUtils.generatePasswordHash(dto.getPassword()));
        if (Optional.ofNullable(agent).isPresent()) {
            // 设置上级层级关系
            userPo.setSupUid1(agent.getId());
            userPo.setSupUsername1(agent.getUsername());
            userPo.setSupUid2(agent.getSupUid1());
            userPo.setSupUid3(agent.getSupUid2());
            userPo.setSupUid4(agent.getSupUid3());
            userPo.setSupUid5(agent.getSupUid4());
            userPo.setSupUid6(agent.getSupUid5());
            if (agent.getSupLevelTop() == ConstData.ZERO) {
                // 上级代理是顶级代理
                userPo.setSupUsernameTop(agent.getUsername());
                userPo.setSupUidTop(agent.getId());
                userPo.setSupLevelTop(1);
            } else if (agent.getSupLevelTop() > ConstData.ZERO) {
                // 上级代理有顶级代理
                userPo.setSupLevelTop(agent.getSupLevelTop() + 1);
                userPo.setSupUsernameTop(agent.getSupUsernameTop());
                userPo.setSupUidTop(agent.getSupUidTop());
            }

            // 非代理角色 设置为会员代理
            if (agent.getIsPromoter() == 0 && agent.getSupLevelTop() != ConstData.ZERO) {
                agent.setIsPromoter(1);
                userServiceImpl.updateById(agent);
            }
        }
        userPo.setCreatedAt(time);
        userPo.setUpdatedAt(time);
        userServiceImpl.save(userPo);

        // 初始化用户所有币种钱包
        var wallet = new UserWallet();
        wallet.setId(userPo.getId());
        wallet.setUsername(userPo.getUsername());
        wallet.setCreatedAt(time);
        wallet.setUpdatedAt(time);
        userWalletServiceImpl.save(wallet);
        // 更新上级代理缓存
        userCache.delUserCache(userPo.getId());
    }

    public static String generateChinesePhoneNumber() {
        Random random = new Random();

        // 中国手机号码的第二位可以是3、4、5、6、7、8、9中的一个数字
        int secondDigit = 3 + random.nextInt(7);

        // 生成剩下的9位数字
        StringBuilder phoneNumber = new StringBuilder("1");
        phoneNumber.append(secondDigit);
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(random.nextInt(10));
        }

        return phoneNumber.toString();
    }
}
