package com.lt.win.service.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 字典
 * @author: andy
 * @date: 2020/8/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryBo {
    @ApiModelProperty(name = "category", value = "字典类别", example = "dic_user_role")
    private String category;

    @ApiModelProperty(name = "code", value = "字典码")
    private String code;

    @ApiModelProperty(name = "title", value = "字典名称")
    private String title;

    @ApiModelProperty(name = "isShowList", value = "类型: 0-全部 1-前端 2-后台")
    private List<Integer> isShowList;

    @ApiModelProperty(name = "redis字典大类dictHashKey", value = "[apiKey -> DICTIONARY_API_HASH] [backendKey -> DICTIONARY_BACKEND_HASH]")
    private String dictHashKey;
}
