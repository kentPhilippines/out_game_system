package com.lt.win.apiend;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: wells
 * @date: 2020/7/13
 * @description:
 */
@Component
public class AsyncObject {
    @Async
    public void asyncMethod() {
        while (true) {
            System.out.println("asyncMethod....");
        }
    }
}
