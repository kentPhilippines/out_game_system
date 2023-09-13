package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.aop.annotation.UnCheckLog;
import com.lt.win.backend.base.DictionaryBase;
import com.lt.win.service.io.dto.Dictionary;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典接口
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
@Slf4j
@RestController
@Api(tags = "字典接口")
@RequestMapping("/v1/dictionary")
public class DictionaryController {
    @Resource
    private DictionaryBase dictionaryBase;

    @UnCheckLog
    @ApiOperationSupport(author = "Andy")
    @PostMapping(value = "/getDictionary")
    @ApiOperation(value = "获取字典", notes = "category不传查询所有，否则按category获取")
    public Result<Map<String, List<Dictionary.ResDto>>> getDictionary(@RequestBody Dictionary.ReqDto reqDto) {
        return Result.ok(dictionaryBase.getDictionary(null != reqDto ? reqDto.getCategory() : null));
    }

}
