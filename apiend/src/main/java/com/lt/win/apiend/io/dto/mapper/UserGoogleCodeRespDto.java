package com.lt.win.apiend.io.dto.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Google验证码返回实体
 *
 * @author david
 */
@ApiModel(value = "谷歌口令秘钥和二维码返回")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGoogleCodeRespDto {
    @ApiModelProperty(value = "秘钥")
    private String secret;
    @ApiModelProperty(value = "二维码")
    private String qrCode;
}
