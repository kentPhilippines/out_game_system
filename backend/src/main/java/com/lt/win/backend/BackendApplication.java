package com.lt.win.backend;

import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.zxp.esclientrhl.annotation.EnableESTools;

/**
 * @author david
 */
@Slf4j
@MapperScan(value = {"com.lt.win.**"})
@SpringBootApplication(scanBasePackages = {"com.lt.win.**"})
@EnableFileStorage
//@EnableESTools(
//        basePackages = {"com.lt.win"},
//        entityPath = {"com.lt.win"},
//        printregmsg = true)
@EnableKnife4j
@EnableCaching
public class BackendApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BackendApplication.class, args);
        } catch (Exception e) {
            log.error("===> {} main run 异常:", BackendApplication.class.getSimpleName(), e);
        }
    }

}
