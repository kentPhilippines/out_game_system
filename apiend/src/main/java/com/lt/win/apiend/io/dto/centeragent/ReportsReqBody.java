package com.lt.win.apiend.io.dto.centeragent;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 代理中心-我的报表 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportsReqBody extends StartEndTime {
    @ApiModelProperty(name = "tableName", value = "表名", hidden = true)
    private String tableName;

    @ApiModelProperty(name = "columnName", value = "列名", hidden = true)
    private String columnName;

    @ApiModelProperty(name = "uid", value = "uid", hidden = true)
    private Integer uid;

    @ApiModelProperty(name = "depStatusList", value = "充值标识:1-首充 2-二充 9-其他", hidden = true)
    private List<Integer> depStatusList;

    @ApiModelProperty(name = "statusList", value = "充值状态:0-申请中 1-手动到账 2-自动到账 3-充值失败 8-充值锁定 9-管理员充值;提现状态：0-申请中 1-成功 2-失败", hidden = true)
    private List<Integer> statusList;

}
