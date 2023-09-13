package com.lt.win.backend.io.bo.user;

import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 交易记录-列表查询 请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/19
 */
@Data
public class TransactionRecordListResBody {
    @ApiModelProperty(name = "total", value = "统计总金额")
    private TransactionRecordTotal total;
    @ApiModelProperty(name = "list", value = "列表数据")
    private ResPage<TransactionRecordList> list;

}
