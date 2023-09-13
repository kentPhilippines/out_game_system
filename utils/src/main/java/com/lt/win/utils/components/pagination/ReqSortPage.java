package com.lt.win.utils.components.pagination;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;


/**
 * <p>
 * 带排序的
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReqSortPage<T> extends BasePage {
    @ApiModelProperty(name = "sortKey", value = "ASC或DESC", example = "DESC")
    private String sortKey;
    @ApiModelProperty(name = "sortField", value = "排序字段")
    private String[] sortField;
    @ApiModelProperty(name = "data", value = "请求body")
    private T data;

    @Override
    public <K> Page<K> getPage() {
        Page<K> page = super.getPage();
        if (StringUtils.isNotBlank(sortKey)) {
            if (ASC_SORT.equalsIgnoreCase(sortKey.toUpperCase())) {
                page.setOrders(OrderItem.ascs(sortField));
            }
            if (DESC_SORT.equalsIgnoreCase(sortKey.toUpperCase())) {
                page.setOrders(OrderItem.descs(sortField));
            }
        }
        return page;
    }
}

