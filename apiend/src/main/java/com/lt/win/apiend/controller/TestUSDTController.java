package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wells
 * @Date: 2022/11/17 19:40
 * @Description:
 */
@Slf4j
//@RestController
@RequestMapping("/v1/test")
@Api(tags = "代理中心")
@ApiSort(7)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestUSDTController {


}
