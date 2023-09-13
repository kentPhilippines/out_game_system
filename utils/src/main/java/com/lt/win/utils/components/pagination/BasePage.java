package com.lt.win.utils.components.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 分页
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class BasePage {
    public static final String DESC_SORT = "DESC";
    public static final String ASC_SORT = "ASC";

    @ApiModelProperty(name = "current", value = "当前页", example = "1", required = true)
    @NotNull(message = "current不能为空")
    private long current = 1;
    @ApiModelProperty(name = "size", value = "每页显示条数", example = "10", required = true)
    @NotNull(message = "size不能为空")
    private long size = 20;

    @ApiModelProperty(name = "getPage", hidden = true)
    public <T> Page<T> getPage() {
        return new Page<>(current, size);
    }

    @ApiModelProperty(name = "setPage", hidden = true)
    public void setPage(long current, long size) {
        this.current = current;
        this.size = size;
    }
}