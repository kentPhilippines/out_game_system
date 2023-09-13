package com.lt.win.service.server;

import com.lt.win.dao.generator.po.UserWallet;
import com.lt.win.service.io.bo.MemberBalanceBo;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.Betslips;

import java.math.BigDecimal;
import java.util.List;

/**
 * 游戏回调数据服务
 *
 * @author fangzs
 * @date 2022/8/11 18:07
 */
public interface IGameCallbackService {
    /**
     * 获取用户信息
     *
     * @param user 用户
     * @return 用户信息
     */
    MemberBalanceBo getBalance(BaseParams.HeaderInfo user, String currencyType);

    /**
     * 获取用户信息
     *
     * @param userId 用户
     * @return 用户信息
     */
    MemberBalanceBo getBalance(Integer userId, String currencyType);

    /**
     * 注单
     * roundId
     *
     * @param betslips 注单参数
     * @return 注单后返回
     * @throws Exception 异常
     */
    MemberBalanceBo bet(Betslips betslips) throws Exception;

    /**
     * 同一回合，多次投注
     *
     * @param betslips 投注
     * @param amount   金额
     * @return 余额
     */
    MemberBalanceBo bet(Betslips betslips, BigDecimal amount) throws Exception;


    /**
     * digi体育专用
     *
     * @param betslips
     * @return
     * @throws Exception
     */
    MemberBalanceBo betByDiGi(Betslips betslips) throws Exception;

    /**
     * 开奖
     * roundId
     *
     * @param betslips 对象
     * @param amount   开奖金额
     * @return 结果
     * @throws Exception 异常
     */
    MemberBalanceBo creditByRoundId(Betslips betslips, BigDecimal amount) throws Exception;

    /**
     * 注单
     * transactionId
     *
     * @param betslips 注单参数
     * @return 注单后返回
     * @throws Exception 异常
     */
    UserWallet betByTransactionId(Betslips betslips) throws Exception;

    /**
     * 开奖
     * transactionId
     *
     * @param betslips 对象
     * @param amount   开彩金额
     * @return 结果
     * @throws Exception 异常
     */
    MemberBalanceBo creditByTransactionId(Betslips betslips, BigDecimal amount) throws Exception;

    /**
     * 退款
     *
     * @param betslips betslips
     * @return 结果
     */
    boolean refund(Betslips betslips) throws Exception;


    /**
     * 批量注单
     *
     * @param list 注单参数
     * @return 注单后返回
     * @throws Exception 异常
     */
    List<Betslips> betBatchMysql(List<Betslips> list) throws Exception;

    /**
     * 批量注单
     *
     * @param list 注单参数
     * @return 注单后返回
     * @throws Exception 异常
     */
    void betBatchEs(List<Betslips> list) throws Exception;


    List<Betslips> updateBatchByBetIdForMysql(List<Betslips> list);

    boolean updateBatchByBetIdForEs(List<Betslips> list) throws Exception;

    /**
     * 投注开彩接口
     *
     * @param betslips 投注对象
     * @return 会员信息
     * @throws Exception 异常
     */
    MemberBalanceBo betWin(Betslips betslips) throws Exception;


    boolean debitByBatch(Betslips betslips, Integer outIn, int type, BigDecimal amount) throws Exception;

    /**
     * 根据roundId 撤单退款
     *
     * @param betslips betslips
     * @param coin     coin
     * @return res
     */
    UserWallet refundByRoundId(Betslips betslips, BigDecimal coin);

    /**
     * 根据transactionId 撤单退款
     *
     * @param betslips betslips
     * @param coin     coin
     * @return res
     */
    UserWallet refundByTransactionId(Betslips betslips, BigDecimal coin);

    /**
     * 直接派发免费奖金(老虎机场景) - 没有投注对应的订单号
     *
     * @param betslips 对象
     * @param amount   开彩金额
     * @return 结果
     */
    UserWallet bonus(Betslips betslips, BigDecimal amount);
}
