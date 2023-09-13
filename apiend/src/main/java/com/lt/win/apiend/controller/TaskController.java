package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.apiend.task.VipDemotionTask;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Auther: wells
 * @Date: 2022/10/15 19:59
 * @Description:
 */
@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
    private final VipDemotionTask vipDemotionTask;

    @UnCheckToken
    @GetMapping(value = "/demotion")
    public Result<Boolean> demotion() {
        return Result.ok(vipDemotionTask.demotion());
    }

}
