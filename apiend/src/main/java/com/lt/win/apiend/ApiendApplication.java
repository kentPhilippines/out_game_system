package com.lt.win.apiend;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.zxp.esclientrhl.annotation.EnableESTools;

/**
 * @author admin
 */
@Slf4j
@MapperScan(value = {"com.lt.win.**"})
@SpringBootApplication(scanBasePackages = {"com.lt.win.**"})
//@EnableESTools(
//        basePackages = {"com.lt.win"},
//        entityPath = {"com.lt.win"},
//        printregmsg = true)
@EnableScheduling
@EnableAsync
@EnableCaching
public class ApiendApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ApiendApplication.class, args);
            idInit();
        } catch (Exception e) {
            log.error("===> {} main run 异常:", ApiendApplication.class.getSimpleName(), e);
        }
    }

    public static void idInit() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        // options.WorkerIdBitLength = 10; // 默认值6，限定 WorkerId 最大值为2^6-1，即默认最多支持64个节点。
        // options.SeqBitLength = 6; // 默认值6，限制每毫秒生成的ID个数。若生成速度超过5万个/秒，建议加大 SeqBitLength 到 10。
        // options.BaseTime = Your_Base_Time; // 如果要兼容老系统的雪花算法，此处应设置为老系统的BaseTime。
        // ...... 其它参数参考 IdGeneratorOptions 定义。

        // 保存参数（务必调用，否则参数设置不生效）：
        YitIdHelper.setIdGenerator(options);
        // 以上过程只需全局一次，且应在生成ID之前完成。
    }
}
