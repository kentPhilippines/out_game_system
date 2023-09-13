package com.lt.win.service.configuration;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lt.win.utils.IdUtils;
import org.springframework.stereotype.Component;

/**
 * @author william
 * @version 1.0
 * @date 2020/3/5 0005 下午 1:26
 */
@Component
public class MybatisPlusIdentifierGeneratorConfig implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return IdUtils.getSnowFlakeId();
    }
}
