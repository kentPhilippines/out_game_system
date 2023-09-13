package com.lt.win.utils.components.pagination;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;


/**
 * <p>
 * 分页-响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReqPage<T> extends BasePage {
    @ApiModelProperty(name = "data", value = "请求body")
    private T data;
    @ApiModelProperty(name = "sortKey", value = "ASC或DESC", example = "DESC")
    private String sortKey;
    @ApiModelProperty(name = "sortField", value = "排序字段")
    private String[] sortField;

    @ApiModelProperty(name = "page", hidden = true)
    @Override
    public <K> Page<K> getPage() {
        Page<K> page = super.getPage();
        // 默认使用ID倒序排序
        if (ArrayUtils.isEmpty(sortField)) {
            sortField = new String[]{"id"};
        }
        if (StringUtils.isNotBlank(sortKey)) {
            if (ASC_SORT.equals(sortKey.toUpperCase(Locale.US))) {
                page.setOrders(OrderItem.ascs(sortField));
            }
            if (DESC_SORT.equals(sortKey.toUpperCase(Locale.US))) {
                page.setOrders(OrderItem.descs(sortField));
            }
        }
        return page;
    }
}

