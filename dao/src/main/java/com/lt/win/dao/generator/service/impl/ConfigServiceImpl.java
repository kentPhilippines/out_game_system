package com.lt.win.dao.generator.service.impl;

import com.lt.win.dao.generator.po.Config;
import com.lt.win.dao.generator.mapper.ConfigMapper;
import com.lt.win.dao.generator.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
