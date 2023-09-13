package com.lt.win.dao.generator.service.impl;

import com.lt.win.dao.generator.po.Admin;
import com.lt.win.dao.generator.mapper.AdminMapper;
import com.lt.win.dao.generator.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台管理员账号 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
