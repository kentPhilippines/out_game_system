package com.lt.win.apiend.service;


import com.lt.win.apiend.io.bo.BlogParams;
import com.lt.win.dao.generator.po.Blog;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;


/**
 * @author: piking
 * @date: 2020/8/1
 */
public interface IBlogService {

    /**
     * 博客分页查找数据
     *
     * @param dto dto
     * @return res
     */
    ResPage<Blog> list(ReqPage<BlogParams.ListReqDto> dto);


    /**
     * 博客分页查找数据
     *
     * @param dto dto
     * @return res
     */
    List<Blog> recommend(BlogParams.BlogRecommendsReqDto dto);

    /**
     * 博客分页查找数据
     *
     * @param dto dto
     * @return res
     */
    ResPage<Blog> search(ReqPage<BlogParams.BlogSearchReqDto> dto);
}
