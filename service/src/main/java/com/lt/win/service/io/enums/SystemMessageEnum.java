package com.lt.win.service.io.enums;

import lombok.Getter;

/**
 * @Auther: wells
 * @Date: 2022/11/2 12:15
 * @Description:
 */
@Getter
public enum SystemMessageEnum {
    DEPOSIT_SUCCESS("Deposit Success", "Dear User, Congratulation! Your deposit was successful %s Now account balance of %s !"),
    DEPOSIT_FAIL("Deposit Fail", "Dear User, We’re sorry, Your deposit was not successful %s Please try again later!"),
    WITHDRAWAL_SUCCESS("Withdrawal Success", "Dear User,Congratulation! Your withdrawal was successful %s Now account balance of %s!"),
    WITHDRAWAL_FAIL("Withdrawal Fail", "Dear User,We’re sorry, you fail to meet the withdrawal requirements. Your withdrawal was not successful %s Please try again later!");

    String title;
    String content;

    SystemMessageEnum(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
