package com.lt.win.dao.generator.service.impl;

import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.mapper.UserMapper;
import com.lt.win.dao.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-11-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
