package com.lt.win.service.io.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: David
 * @Date: 03/06/2020
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLocalDataDto {
    @ApiModelProperty(name = "id", value = "UID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "username", value = "test999", example = "1")
    private String username;
}
