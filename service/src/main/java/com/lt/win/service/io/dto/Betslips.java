package com.lt.win.service.io.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 注单表
 * </p>
 *
 * @author David
 * @since 2022-08-11
 */
@Data
@Accessors(chain = true)
@TableName("win_betslips")
//@ESMetaData(indexName = "betslips", number_of_replicas = 0, number_of_shards = 5, suffix = true)
public class Betslips extends Model<Betslips> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
   // @ESID
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 交易id
     */
    @TableField("transaction_id")
  // @ESMapping(datatype = DataType.text_type)
    @ApiModelProperty("交易id 注单号 对应三方拉单transaction_id")
    private String transactionId;

    @TableField("win_transaction_id")
  //  @ESMapping(datatype = DataType.text_type)
    @ApiModelProperty("开奖交易单号")
    private String winTransactionId;

    /**
     * 对应user表id
     */
    @TableField("xb_uid")
   // @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("用户id")
    private Integer xbUid;

    /**
     * 对应user表username
     */
    @TableField("xb_username")
    @ApiModelProperty("用户昵称")
   // @ESMapping(datatype = DataType.text_type)
    private String xbUsername;

    /**
     * 投注方式 1:现金，2:奖金 3:免费旋转 4:活动免费旋转
     */
    @TableField("amount_type")
   // @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("投注方式 1:现金，2:奖金 3:免费旋转 4:活动免费旋转; 体育注单中：1：单，2：多")
    private Integer amountType;


    @TableField("round_id")
   // @ESMapping(datatype = DataType.text_type)
    @ApiModelProperty("回合id")
    private String roundId;
    /**
     * 游戏id
     */
    @TableField("game_type_id")
    @ApiModelProperty("游戏类型id")
   // @ESMapping(datatype = DataType.text_type)
    private String gameTypeId;

    @TableField("game_name")
    @ApiModelProperty("游戏名称")
   // @ESMapping(datatype = DataType.text_type)
    private String gameName;

    /**
     * 游戏类型id
     */
    @TableField("game_id")
  //  @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("游戏id")
    private Integer gameId;


    /**
     * 体育类型id,滚球棒球
     */
    @TableField("sport_type")
  //  @ESMapping(datatype = DataType.keyword_type)
    @ApiModelProperty("体育类型id,多个用字符串隔开")
    private String sportType;


    /**
     * 游戏大类类型:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8-动物 9-快速 10-技能',
     */
    @TableField("game_group_id")
  //  @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("游戏大类类型id")
    private Integer gameGroupId;

    /**
     * 游戏平台id
     */
    @TableField("game_plat_id")
  //  @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("游戏平台id")
    private Integer gamePlatId;

    /**
     * 游戏开始时间
     */
    @TableField("dt_started")
  //  @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("游戏开始时间")
    private Integer dtStarted;

    /**
     * 游戏结束时间
     */
    @TableField("dt_completed")
  //  @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("游戏结束时间")
    private Integer dtCompleted;

    @TableField("create_time_str")
   // @ESMapping(datatype = DataType.text_type)
    @ApiModelProperty("注单创建时间 yyyymmdd")
    private String createTimeStr;

    /**
     * 投注
     */
    @TableField("stake")
   // @ESMapping(datatype = DataType.double_type)
    @ApiModelProperty("投注金额")
    private BigDecimal stake;
    /**
     * 有效投注金额
     */
    @TableField("valid_stake")
  //  @ESMapping(datatype = DataType.double_type)
    @ApiModelProperty("有效投注金额")
    private BigDecimal validStake;

    /**
     * 退款金额
     */
    @TableField("coin_refund")
  //  @ESMapping(datatype = DataType.double_type)
    @ApiModelProperty("退款金额")
    private BigDecimal coinRefund = BigDecimal.ZERO;

    /**
     * 退款金额
     */
    @TableField("coin_before")
   // @ESMapping(datatype = DataType.double_type)
    @ApiModelProperty("投注前金额")
    private BigDecimal coinBefore = BigDecimal.ZERO;


    /**
     * 派彩
     */
    @TableField("payout")
  //  @ESMapping(datatype = DataType.double_type)
    @ApiModelProperty("派彩金额")
    private BigDecimal payout = BigDecimal.ZERO;

    /**
     * 盈亏金额
     */
    @TableField("xb_profit")
    @ApiModelProperty("盈亏金额")
  //  @ESMapping(datatype = DataType.double_type)
    private BigDecimal xbProfit = BigDecimal.ZERO;

    /**
     * 注单状态 1:待开彩  2:完成  3: 退款
     */
    @TableField("xb_status")
   // @ESMapping(datatype = DataType.integer_type)
    @ApiModelProperty("注单状态 1:待开彩  2:完成  3: 退款")
    private Integer xbStatus;

    @TableField("created_at")
 //   @ESMapping(datatype = DataType.integer_type)
    private Integer createdAt;

    @TableField("updated_at")
  //  @ESMapping(datatype = DataType.integer_type)
    private Integer updatedAt;


    /**
     * 投注原始json
     */
    @TableField("bet_json")
    @ApiModelProperty("投注原始json")
   // @ESMapping(datatype = DataType.text_type)
    private String betJson;

    /**
     * 开彩原始json
     */
    @TableField("reward_json")
    @ApiModelProperty("开彩原始json")
   // @ESMapping(datatype = DataType.text_type)
    private String rewardJson;

    @TableField("refund_json")
    @ApiModelProperty("退款json")
   // @ESMapping(datatype = DataType.text_type)
    private String refundJson;
    /**
     * 写入成功失败
     */
    @TableField(exist = false)
    private Boolean result;


    @TableField(exist = false)
    private Long info;
    @TableField(exist = false)
    private Long betInfo;
    @TableField(exist = false)
    private Long winInfo;
    /**
     * 默认false,   false投注扣用户余额，true，投注不扣用户余额
     */
    @TableField(exist = false)
    private boolean isBonus;
    @TableField(exist = false)
    private Boolean changeBalance = true;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Betslips{" +
                "id=" + id +
                ", xbUid=" + xbUid +
                ", xbUsername=" + xbUsername +
                ", amountType=" + amountType +
                ", gameId=" + gameId +
                ", gameTypeId=" + gameTypeId +
                ", gamePlatId=" + gamePlatId +
                ", dtStarted=" + dtStarted +
                ", dtCompleted=" + dtCompleted +
                ", stake=" + stake +
                ", payout=" + payout +
                ", xbProfit=" + xbProfit +
                ", xbStatus=" + xbStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", betJson=" + betJson +
                ", rewardJson=" + rewardJson +
                ", refundJson=" + refundJson +
                "}";
    }
}
