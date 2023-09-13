package com.lt.win.backend.io.po;

import com.lt.win.dao.generator.po.GameList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 平台管理-列表
 * </p>
 *
 * @author andy
 * @since 2020/6/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListPlatPO extends GameList {

    @ApiModelProperty(name = "code")
    private String code;

    @ApiModelProperty(name = "nameZh")
    private String nameZh;

    @ApiModelProperty(name = "rate")
    private String rate;

    @ApiModelProperty(name = "config")
    private String config;
}
