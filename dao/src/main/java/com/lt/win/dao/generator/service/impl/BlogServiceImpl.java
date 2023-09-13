package com.lt.win.dao.generator.service.impl;

import com.lt.win.dao.generator.po.Blog;
import com.lt.win.dao.generator.mapper.BlogMapper;
import com.lt.win.dao.generator.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
