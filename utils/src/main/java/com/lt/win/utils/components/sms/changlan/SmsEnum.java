package com.lt.win.utils.components.sms.changlan;

import lombok.*;

/**
 * @author: David
 * @date: 16/06/2020
 */
public interface SmsEnum {

    @Getter
    enum LIMIT {
        /**
         * 注册账号模版
         */
        SMS_MAX_NUM_PER_DAY(10L),
        SMS_MAX_NUM_PER_HOUR(5L),
        ;

        Long value;

        LIMIT(Long value) {
            this.value = value;
        }
    }


    @Getter
    enum SIGNATURE {
        /**
         * 注册账号模版
         */
        ZH("【BestWinnerGaming】"),
        EN("【XinBo Sports】"),
        ;

        String value;

        SIGNATURE(String value) {
            this.value = value;
        }
    }

    @Getter
    enum MESSAGES {
        /**
         * 注册账号模版
         */
        ZH("验证码: {$Code}. 5分钟内有效, 请勿向任何人泄露您的验证码。{$Signature}"),
        EN("Verify Code: {$Code}. Valid for 5 minutes. Do not share this code with others. {$Signature}"),
        ;

        String value;

        MESSAGES(String value) {
            this.value = value;
        }
    }

    @Getter
    enum STATUS {
        /**
         * 状态码
         */
        SUCCESS("0"),
        ;

        String value;

        STATUS(String value) {
            this.value = value;
        }
    }


}
