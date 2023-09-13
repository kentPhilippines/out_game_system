package com.lt.win.apiend.io.dto.centeragent;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代理中心-我的报表-奖金与佣金详情ReqBody
 * /p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardsCommissionDetailsReqBody extends StartEndTime {

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "大类型:1-邀请奖励 2-佣金奖励", example = "1")
    private Integer category;

    @ApiModelProperty(name = "subCategory", value = "[当大类型为1 -> 子类型:0-被邀请奖金1-邀请奖金 2-充值返利];[当大类型为2 ->子类型:0-流水佣金 1-活跃会员佣金 2-满额人头彩金]", example = "1")
    private Integer subCategory;
}
