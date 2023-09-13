package com.lt.win.utils.components.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 分页-响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class ResPage<T> {
    @ApiModelProperty(name = "current", value = "当前页", example = "1")
    private long current = 1;
    @ApiModelProperty(name = "size", value = "每页显示条数", example = "10")
    private long size = 10;
    @ApiModelProperty(name = "list", value = "数据")
    private List<T> list = Collections.emptyList();
    @ApiModelProperty(name = "total", value = "总条数")
    private long total = 0;
    @ApiModelProperty(name = "pages", value = "总页数")
    private long pages = 0;

    @NotNull
    public static <K> ResPage<K> get(Page<K> page) {
        ResPage<K> p = new ResPage<>();
        if (null != page) {
            p.setPages(page.getPages());
            p.setList(page.getRecords());
            p.setTotal(page.getTotal());
            p.setCurrent(page.getCurrent());
            p.setSize(page.getSize());
        }
        return p;
    }
}
