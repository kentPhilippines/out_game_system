package com.lt.win.service.io.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 账变明细
 * </p>
 *
 * @author David
 * @since 2022-08-14
 */
@Data
@TableName("win_coin_log")
//@ESMetaData(indexName = "coin_log_es", number_of_replicas = 0, number_of_shards = 5, suffix = true)
public class CoinLog extends Model<CoinLog> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
   // @ESID
    private Long id;

    /**
     * UID
     */
    @TableField("uid")
  // @ESMapping(datatype = DataType.integer_type)
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
   // @ESMapping(datatype = DataType.text_type)
    private String username;

    /**
     * 类型:1-存款 2-提款 3-投注 4-派彩 5-返水 6-佣金 7-活动(奖励) 8-系统调账
     */
    @TableField("category")
   // @ESMapping(datatype = DataType.integer_type)
    private Integer category;

    /**
     * 类型 0-支出 1-收入
     */
    @TableField("out_in")
  //  @ESMapping(datatype = DataType.integer_type)
    private Integer outIn;

    /**
     * 关联ID
     */
    @TableField("refer_id")
  //  @ESMapping(datatype = DataType.long_type)
    private Long referId;

    /**
     * 金额
     */
    @TableField("coin")
  //  @ESMapping(datatype = DataType.double_type)
    private BigDecimal coin;

    /**
     * 实际到账金额
     */
    @TableField("coin_real")
   // @ESMapping(datatype = DataType.double_type)
    private BigDecimal coinReal;

    /**
     * 前金额
     */
    @TableField("coin_before")
   // @ESMapping(datatype = DataType.double_type)
    private BigDecimal coinBefore;

    /**
     * 帐变后金额
     */
    @TableField("coin_after")
  //  @ESMapping(datatype = DataType.double_type)
    private BigDecimal coinAfter;


    /**
     * 三方游戏平台ID
     */
    @TableField("plat_id")
  //  @ESMapping(datatype = DataType.integer_type)
    private Integer platId;
    /**
     * 三方游戏ID
     */
    @TableField("game_id")
   // @ESMapping(datatype = DataType.integer_type)
    private Integer gameId;
    /**
     * 状态:0-处理中 1-成功 2-失败
     */
    @TableField("status")
   // @ESMapping(datatype = DataType.integer_type)
    private Integer status;

    @TableField("created_at")
  //  @ESMapping(datatype = DataType.integer_type)
    private Integer createdAt;

    @TableField("updated_at")
  //  @ESMapping(datatype = DataType.integer_type)
    private Integer updatedAt;

    @TableField(exist = false)
   // @ESMapping(datatype = DataType.text_type)
    private String externalTxId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CoinLog{" +
                "id=" + id +
                ", uid=" + uid +
                ", username=" + username +
                ", category=" + category +
                ", referId=" + referId +
                ", coin=" + coin +
                ", coinBefore=" + coinBefore +
                ", coinAfter=" + coinAfter +
                ", gameId=" + gameId +
                ", platId=" + platId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "}";
    }
}
