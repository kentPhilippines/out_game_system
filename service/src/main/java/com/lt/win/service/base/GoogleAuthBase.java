package com.lt.win.service.base;

import com.lt.win.dao.generator.po.UserGoogleCode;
import com.lt.win.dao.generator.service.UserGoogleCodeService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.GoogleAuthenticator;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Google 验证码检测类
 * date : 2022-05-31
 *
 * @author david
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoogleAuthBase {
    private final UserGoogleCodeService userGoogleCodeServiceImpl;

    /**
     * 判定用户的Google验证码使用状态*
     *
     * @param code code
     * @param uid  uid
     */
    public void checkGoogleCode(Integer uid, String code) {
        var googleCode = userGoogleCodeServiceImpl.lambdaQuery()
                .eq(UserGoogleCode::getUid, uid)
                .one();
        if (Optional.ofNullable(googleCode).isEmpty() || googleCode.getStatus() == 0) {
            return;
        }
        if (Optional.ofNullable(code).isEmpty()) {
            throw new BusinessException(CodeInfo.GOOGLE_CODE_INVALID);
        }

        var isCheck = !GoogleAuthenticator.checkVerificationCode(googleCode.getGoogleCode(), code);
        //检测验证码
        if (isCheck) {
            throw BusinessException.buildException(CodeInfo.GOOGLE_CODE_INVALID);
        }
    }

    /**
     * 认证Google验证码Code
     *
     * @param secret secret
     * @param code   code
     */
    public void verifyGoogleAuthCode(String secret, String code) {
        var isCheck = !GoogleAuthenticator.checkVerificationCode(secret, code);
        //检测验证码
        if (isCheck) {
            throw BusinessException.buildException(CodeInfo.GOOGLE_CODE_INVALID);
        }
    }

    /**
     * 获取用户绑定Google验证码状态
     *
     * @param uid uid
     * @return res -1:未绑定 1-启用 0-禁用
     */
    public Integer getGoogleAuthStatus(Integer uid) {
        var googleCode = userGoogleCodeServiceImpl.lambdaQuery()
                .eq(UserGoogleCode::getUid, uid)
                .one();
        //检测验证码
        if (Optional.ofNullable(googleCode).isEmpty()) {
            return -1;
        }

        return googleCode.getStatus();
    }

}
