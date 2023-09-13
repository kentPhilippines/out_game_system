package com.lt.win.apiend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.bo.BlogParams;
import com.lt.win.apiend.service.IBlogService;
import com.lt.win.dao.generator.po.Blog;
import com.lt.win.dao.generator.service.BlogService;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: David
 * @date: 08/01/2022
 * @description:
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IBlogServiceImpl implements IBlogService {
    private final BlogService blogServiceImpl;


    /**
     * 博客分页查找数据
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<Blog> list(ReqPage<BlogParams.ListReqDto> dto) {
        var headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var data = dto.getData();
        Page<Blog> page = blogServiceImpl.page(
                dto.getPage(),
                new LambdaQueryWrapper<Blog>()
                        .eq(Optional.ofNullable(data.getCategory()).isPresent(), Blog::getCategory, data.getCategory())
                        .eq(Blog::getLang, headerInfo.getLang())
                        .orderByDesc(Blog::getSort)
                        .orderByDesc(Blog::getCreatedAt)
        );

        return ResPage.get(page);
    }

    /**
     * 博客分页查找数据
     *
     * @param dto dto
     * @return res
     */
    @Override
    public List<Blog> recommend(BlogParams.BlogRecommendsReqDto dto) {
        var headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        return blogServiceImpl.lambdaQuery()
                .eq(Blog::getLang, headerInfo.getLang())
                .orderByDesc(Blog::getRecommend)
                .orderByDesc(Blog::getCreatedAt)
                .last("limit " + dto.getNums())
                .list();
    }

    /**
     * 博客分页查找数据
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<Blog> search(ReqPage<BlogParams.BlogSearchReqDto> dto) {
        var headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var data = dto.getData();
        return ResPage.get(
                blogServiceImpl.page(
                        dto.getPage(),
                        new LambdaQueryWrapper<Blog>()
                                .eq(Blog::getLang, headerInfo.getLang())
                                .and(
                                        o -> o.like(Optional.ofNullable(data.getTitle()).isPresent(), Blog::getTitle, data.getTitle())
                                                .or()
                                                .like(Optional.ofNullable(data.getTitle()).isPresent(), Blog::getTitleSub, data.getTitle())
                                )
                                .orderByDesc(Blog::getRecommend)
                                .orderByDesc(Blog::getCreatedAt)
                )
        );
    }
}
