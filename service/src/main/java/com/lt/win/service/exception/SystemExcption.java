package com.lt.win.service.exception;

import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.utils.components.response.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统异常转换类
 *
 * </p>
 *
 * @author andy
 * @since 2020-02-11
 */
@Controller
@ApiIgnore
public class SystemExcption implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 400) {
            return "/400";
        } else if (statusCode == 403) {
            return "/403";
        } else if (statusCode == 404) {
            return "/404";
        } else if (statusCode == 405) {
            return "/405";
        } else if (statusCode == 500) {
            return "/500";
        } else {
            return "/500";
        }
    }

    @ResponseBody
    @RequestMapping("/400")
    public Result error400() {
        return Result.error(CodeInfo.STATUS_CODE_400);
    }

    @ResponseBody
    @RequestMapping("/403")
    public Result error403() {
        return Result.error(CodeInfo.STATUS_CODE_403);
    }

    @ResponseBody
    @RequestMapping("/404")
    public Result error404() {
        return Result.error(CodeInfo.STATUS_CODE_404);
    }

    @ResponseBody
    @RequestMapping("/405")
    public Result error405() {
        return Result.error(CodeInfo.STATUS_CODE_405);
    }

    @ResponseBody
    @RequestMapping("/500")
    public Result error500() {
        return Result.error(CodeInfo.STATUS_CODE_500);
    }
}