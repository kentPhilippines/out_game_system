package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.BlogParams;
import com.lt.win.apiend.service.impl.IBlogServiceImpl;
import com.lt.win.dao.generator.po.Blog;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * 博客相关接口
 * <p>
 * date: 08/01/2022
 *
 * @author david
 */
@Slf4j
//@RestController
@Api(tags = "首页 - 博客")
@ApiSort(2)
@RequestMapping("/v1/blog")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BlogController {

    private final IBlogServiceImpl iBlogServiceImpl;

    @UnCheckToken
    @PostMapping(value = "/list")
    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "博客分页", notes = "博客")
    public Result<ResPage<Blog>> list(@Valid @RequestBody ReqPage<BlogParams.ListReqDto> dto) {
        return Result.ok(iBlogServiceImpl.list(dto));
    }

    @UnCheckToken
    @PostMapping(value = "/recommend")
    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "博客推荐", notes = "博客推荐")
    public Result<List<Blog>> recommend(@Valid @RequestBody BlogParams.BlogRecommendsReqDto dto) {
        return Result.ok(iBlogServiceImpl.recommend(dto));
    }

    @UnCheckToken
    @PostMapping(value = "/search")
    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "博客推荐", notes = "博客推荐")
    public Result<ResPage<Blog>> search(@Valid @RequestBody ReqPage<BlogParams.BlogSearchReqDto> dto) {
        return Result.ok(iBlogServiceImpl.search(dto));
    }
}
