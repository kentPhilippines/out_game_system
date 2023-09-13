package com.lt.win.service.io.game;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lt.win.utils.Decimal2Serializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Digitain 投注相关
 *
 * @author fangzs
 * @date 2022/9/1 01:01
 */
public interface DigitainBase {
    /**
     * 注单
     */
    @Data
    class BetBo implements Serializable {

        private static final long serialVersionUID = -3131033802754054715L;
        /**
         * 操作id
         */
        private String operatorId;
        /**
         * 时间戳
         */
        private String timestamp;
        /**
         * 签名
         */
        private String signature;
        /**
         * 当True时，如果任何投注项目失败，合作伙伴应拒绝所有项目
         */
        private Boolean allOrNone;
        /**
         * RGS系统中的游戏提供商标识符
         */
        private Integer providerId;
        /**
         * 投注详情
         */
        private List<BetItem> items;
    }

    /**
     * 注单明细
     */
    @Data
    class BetItem implements Serializable {

        private static final long serialVersionUID = 8844272017691841638L;
        /**
         * 用户token
         */
        private String token;
        /**
         * 玩家id
         */
        private String playerId;
        /**
         * 游戏id
         */
        private String gameId;
        /**
         * 回合id
         */
        private String roundId;
        /**
         * 交易id
         */
        private String txId;
        /**
         * 投注交易id
         */
        private String betTxId;

        private String winTxId;

        private BigDecimal amendAmount;
        /**
         *
         */
        private Integer betOperationType;

        private Integer winOperationType;
        /**
         *
         */
        private Integer operationType;
        /**
         * 货币类型
         */
        private String currencyId;
        /**
         * 投注金额，美分
         */
        private BigDecimal betAmount;
        /**
         * 开彩金额，美分
         */
        private BigDecimal winAmount;

        /**
         * true 忽略令牌过期时间
         */
        private Boolean ignoreExpiry;
        /**
         * 对应中奖交易的佣⾦⾦额信息
         */
        private BigDecimal rake;
        /**
         * 退款使用
         */
        private String originalTxId;
        /**
         * 免费旋转优惠 ID
         */
        private String bonusTicketId;

        private Long info;
        /**
         * 元数据,我方自定义
         */
        private String metadata;
        /**
         * 如果为真，所有奖金将通过一个调用无效，否则奖金将逐一取消
         */
        private Boolean isCombineRounds;

        private Boolean changeBalance;

        private Long betInfo;

        private Long winInfo;

        private Boolean refundRound;
    }


    /**
     * 投注返回
     *
     * @author fangzs
     * @date 2022/9/3 15:32
     */
    @Data
    @Accessors(chain = true)
    class DigitainBetResponseDto implements Serializable {

        private static final long serialVersionUID = -4605941726888198459L;

        private String timestamp;

        private String signature;

        private Integer errorCode;

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal balance = BigDecimal.ZERO;
        @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
        private String externalTxId;
        @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
        private List<BetResponseItem> items;
    }

    /**
     * 投注返回
     *
     * @author fangzs
     * @date 2022/9/3 15:32
     */
    @Data
    @Accessors(chain = true)
    class DigitainErrorResponseDto implements Serializable {

        private static final long serialVersionUID = -4605941726888198459L;

        private String timestamp;

        private String signature;

        private Integer errorCode;

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal balance;
    }

    /**
     * 投注返回
     */
    @Data
    @Accessors(chain = true)
    class BetResponseItem implements Serializable {

        private static final long serialVersionUID = 6527322335962268121L;

        private String externalTxId;
        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal balance;

        private Long info;

        private Long betInfo;

        private Long winInfo;

        private Integer errorCode;

        private String metadata;
    }

    /**
     * 变更
     */
    @Data
    @Accessors(chain = true)
    class ChargeRequestBo implements Serializable {
        private static final long serialVersionUID = 2302936949529774886L;
        /**
         * 操作id
         */
        private String operatorId;
        /**
         * 时间戳
         */
        private String timestamp;
        /**
         * 签名
         */
        private String signature;

        /**
         * RGS系统中的游戏提供商标识符
         */
        private Integer providerId;
        /**
         * 用户token
         */
        private String token;
        /**
         * 玩家id
         */
        private String playerId;
        /**
         * 游戏id
         */
        private String gameId;
        /**
         * 交易id
         */
        private String txId;
        /**
         *
         */
        private Integer operationType;
        /**
         * 货币类型
         */
        private String currencyId;

        private BigDecimal chargeAmount;
        /**
         * true 则真是修改余额
         */
        private Boolean changeBalance;

        private String metadata;

    }

    @Data
    class CheckTxStatusBo implements Serializable {
        /**
         * 操作id
         */
        private String operatorId;
        /**
         * 时间戳
         */
        private String timestamp;
        /**
         * 签名
         */
        private String signature;

        /**
         * RGS系统中的游戏提供商标识符
         */
        private Integer providerId;

        private String externalTxId;

        private String providerTxId;
    }

    /**
     * 检查
     *
     * @author fangzs
     * @date 2022/9/3 15:32
     */
    @Data
    @Accessors(chain = true)
    class CheckTxStatusResponseDto implements Serializable {

        private static final long serialVersionUID = -4605941726888198459L;

        private String timestamp;

        private String signature;

        private Integer errorCode;

        private boolean txStatus;

        private int operationType;
        private String txCreationDate;
        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal balanceBefore = BigDecimal.ZERO;
        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal balanceAfter = BigDecimal.ZERO;
        private String currencyId;
        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal amount = BigDecimal.ZERO;

        @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
        private String externalTxId;
    }


    /**
     * 检查
     *
     * @author fangzs
     * @date 2022/9/3 15:32
     */
    @Data
    @Accessors(chain = true)
    class PromoWinResponseDto implements Serializable {

        private static final long serialVersionUID = -4605941726888198459L;

        private String timestamp;

        private String signature;

        private Integer errorCode;

        @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
        private String externalTxId;

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal balance = BigDecimal.ZERO;

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal bonusBalance = BigDecimal.ZERO;

        private String currencyId;

        private String info;

        private String metadata;
    }


    @Data
    @Accessors(chain = true)
    class PromoWinRequestBo implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * RGS系统中的游戏提供商标识符
         */
        private Integer providerId;
        /**
         * 游戏id
         */
        private String gameId;
        /**
         * 玩家id
         */
        private String playerId;
        /**
         *
         */
        private Integer operationType;

        private BigDecimal promoWinAmount;
        /**
         * 货币类型
         */
        private String currencyId;
        /**
         * 交易id
         */
        private String txId;
        /**
         * 操作id
         */
        private String operatorId;
        /**
         * 时间戳
         */
        private String timestamp;
        /**
         * 签名
         */
        private String signature;

        private BigDecimal winAmount;

    }
}
