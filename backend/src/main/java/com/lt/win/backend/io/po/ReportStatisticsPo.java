package com.lt.win.backend.io.po;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 报表中心-各平台盈利排行版PO
 * @author: andy
 * @date: 2020/8/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportStatisticsPo extends StartEndTime {
    @ApiModelProperty(name = "gameListId", value = "win_game_list表ID")
    private Integer gameListId;
    @ApiModelProperty(name = "gameListName", value = "win_game_list表Name")
    private String gameListName;
    @ApiModelProperty(name = "tableName", value = "表名", hidden = true)
    private String tableName;
    @ApiModelProperty(name = "columnName", value = "列名", hidden = true)
    private String columnName;
    @ApiModelProperty(name = "columnName", value = "列名", hidden = true)
    private String columnName1;
    @ApiModelProperty(name = "columnName", value = "列名", hidden = true)
    private String columnName2;


    @ApiModelProperty(name = "username", value = "用户名", example = "2")
    private String username;
    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;
    @ApiModelProperty(name = "profit", value = "盈亏金额", example = "10000.00")
    private BigDecimal profit;
    @ApiModelProperty(name = "coin", value = "投注金额", example = "10000.00")
    private BigDecimal coin;

    @ApiModelProperty(name = "dateName", value = "日期名称yyyy-MM-dd", example = "2020-11-05")
    private String dateName;

    @ApiModelProperty(name = "groupId", value = "组名类型:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8-动物", hidden = true)
    private Integer groupId;
    @ApiModelProperty(name = "model", hidden = true)
    private String model;

    @ApiModelProperty(name = "count", value = "投注人数或投注笔数", hidden = true)
    private Integer count;

    @ApiModelProperty(name = "uidList", value = "uidList", hidden = true)
    private List<Integer> uidList;
    @ApiModelProperty(name = "size", value = "size", hidden = true)
    private int size;

    @ApiModelProperty(name = "testUidList", hidden = true)
    private List<Integer> testUidList;


    /**
     * 交易记录-投注总额-列表额外字段
     */
    @ApiModelProperty(name = "status", value = "状态", example = "10000.00")
    private Integer status;
    @ApiModelProperty(name = "createdAt", value = "创建时间", example = "10000.00")
    private Integer createdAt;
    @ApiModelProperty(name = "updatedAt", value = "更新时间", example = "10000.00")
    private Integer updatedAt;
    @ApiModelProperty(name = "actionNo", value = "局号/期号", example = "20201009348")
    private String actionNo;
    @ApiModelProperty(name = "gameId", value = "游戏Id", example = "100")
    private String gameId;
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private String id;
}
