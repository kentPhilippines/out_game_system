package com.lt.win.dao.generator.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 代理佣金分成比例表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_agent_commission_rate")
public class AgentCommissionRate extends Model<AgentCommissionRate> {

    private static final long serialVersionUID=1L;

    /**
     * 代理层级
     */
    @TableId("agent_level")
    private Integer agentLevel;

    /**
     * 佣金比例
     */
    @TableField("agent_level_rate")
    private BigDecimal agentLevelRate;

    @TableField("created_at")
    private Integer createdAt;

    @TableField("updated_at")
    private Integer updatedAt;


    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public BigDecimal getAgentLevelRate() {
        return agentLevelRate;
    }

    public void setAgentLevelRate(BigDecimal agentLevelRate) {
        this.agentLevelRate = agentLevelRate;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.agentLevel;
    }

    @Override
    public String toString() {
        return "AgentCommissionRate{" +
        "agentLevel=" + agentLevel +
        ", agentLevelRate=" + agentLevelRate +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
