package com.lt.win.backend.io.bo.user;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * <p>
 * 交易记录-列表查询 请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/19
 */
@Data
public class TransactionRecordListReqBody extends StartEndTime {
    @ApiModelProperty(name = "referId", value = "关联ID")
    private Long referId;

    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;

    @ApiModelProperty(name = "category", value = "交易类型:1-上分(转入) 2-下分(转出) 3-投注 4-返水 5-奖励 6-佣金 7-结算", example = "3")
    private Integer category;

    @ApiModelProperty(name = "tableName", value = "表名", hidden = true)
    private String tableName;

    @ApiModelProperty(name = "columnName", value = "列名", hidden = true)
    private String columnName;

    @ApiModelProperty(name = "columnName1", value = "列名", hidden = true)
    private String columnName1;

    @ApiModelProperty(name = "categoryList", hidden = true)
    private List<Integer> categoryList;
}
