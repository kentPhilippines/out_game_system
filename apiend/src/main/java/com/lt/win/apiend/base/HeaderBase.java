package com.lt.win.apiend.base;

import com.lt.win.dao.generator.service.AdminService;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.enums.DeviceEnum;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.lt.win.service.io.dto.BaseParams.HeaderInfo;

/**
 * @author: David
 * @date: 06/06/2020
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class HeaderBase {
    private final UserCache userCache;
    private final AdminService adminServiceImpl;

    /**
     * 无需Check Token有效性时 获取头部自定义参数信息
     *
     * @return token、lang、device
     */

    public HeaderInfo getHeaderLocalData(Boolean isValid) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        String lang = request.getHeader(ConstData.LANG);
        String device = request.getHeader(ConstData.DEVICE);
        String apiToken = request.getHeader(ConstData.TOKEN);
        // 不强制校验Token、语言、设备信息直接返回 
        //TODO 2022-09-30 删除  && apiToken == null
        if (Boolean.FALSE.equals(isValid) && (apiToken == null || !apiToken.startsWith(ConstData.TOKEN_START_WITH))) {
            return HeaderInfo.builder().lang(lang).device(device).id(0).build();
        }
        // 判定是否设置语言
        if (lang == null || !EnumUtils.isValidEnumIgnoreCase(LangEnum.class, lang)) {
            throw new BusinessException(CodeInfo.HEADER_LANG_ERROR);
        }
        // 判定是否设置访问设备
        if (device == null || !EnumUtils.isValidEnumIgnoreCase(DeviceEnum.class, device)) {
            throw new BusinessException(CodeInfo.HEADER_DEVICE_ERROR);
        }

        if (StringUtils.isNotBlank(apiToken) && apiToken.startsWith(ConstData.TOKEN_START_WITH)) {
            // 获取正式apiToken
            apiToken = apiToken.substring(ConstData.TOKEN_START_WITH.length());
        } else if (Boolean.FALSE.equals(isValid)) {
            return HeaderInfo.builder().lang(lang).device(device).id(0).build();
        } else {
            log.info("报错：？");
            throw new BusinessException(CodeInfo.STATUS_CODE_401);
        }

        try {
            // Token 验证有效性
            HeaderInfo headerInfo = userCache.validUserToken(apiToken);
            headerInfo.setDevice(device);
            headerInfo.setLang(lang);
            headerInfo.setToken(request.getHeader(ConstData.TOKEN));
            return headerInfo;
        } catch (Exception e) {
            log.error(e.toString());
            // 不强制验证  解析失败直接返回
            if (Boolean.FALSE.equals(isValid)) {
                return HeaderInfo.builder().lang(lang).device(device).id(0).build();
            }
            throw new BusinessException(CodeInfo.STATUS_CODE_401);
        }
    }

}
