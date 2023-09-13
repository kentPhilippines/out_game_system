package com.lt.win.backend.base;

import com.lt.win.backend.configuration.tenant.TenantContextHolder;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.enums.DeviceEnum;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
    private final AdminCache adminCache;
    private final UserCache userCache;

    /**
     * 无需Check Token有效性时 获取头部自定义参数信息
     *
     * @return token、lang、device
     */
    public HeaderInfo getHeaderLocalData(Boolean isValid) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader("User-Agent");
        log.info("User-Agent=====" + header);
        // 判定是否设置语言
        String lang = request.getHeader(ConstData.LANG);
        if (lang == null || !EnumUtils.isValidEnumIgnoreCase(LangEnum.class, lang)) {
            throw new BusinessException(CodeInfo.HEADER_LANG_ERROR);
        }

        // 判定是否设置访问设备
        String device = request.getHeader(ConstData.DEVICE);
        if (device == null || !EnumUtils.isValidEnumIgnoreCase(DeviceEnum.class, device)) {
            throw new BusinessException(CodeInfo.HEADER_DEVICE_ERROR);
        }

        // 后台不强制校验Token 直接返回
        if (Boolean.FALSE.equals(isValid)) {
            return HeaderInfo.builder().lang(lang).device(device).build();
        }

        try {
            String apiToken = request.getHeader(ConstData.TOKEN);
            if (StringUtils.isNotBlank(apiToken) && apiToken.startsWith(ConstData.TOKEN_START_WITH)) {
                // 获取正式apiToken
                apiToken = apiToken.substring(ConstData.TOKEN_START_WITH.length());
            }
            // Token 验证有效性
            HeaderInfo headerInfo = adminCache.validUserToken(apiToken);
            headerInfo.setDevice(device);
            headerInfo.setLang(lang);
            var agentId = adminCache.getAdminInfoById(headerInfo.id).getAgentId();
            TenantContextHolder.setTenantId(null);
            if (agentId != 0) {
                //获取代理的下级uid
                var uidList = userCache.subordinate(agentId).getTeam();
                if (CollectionUtils.isNotEmpty(uidList)) {
                    TenantContextHolder.setTenantId(StringUtils.join(uidList, ","));
                } else {
                    TenantContextHolder.setTenantId("0");
                }
            }
            return headerInfo;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(CodeInfo.STATUS_CODE_401);
        }
    }
}
