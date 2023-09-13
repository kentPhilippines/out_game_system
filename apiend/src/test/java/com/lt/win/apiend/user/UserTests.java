package com.lt.win.apiend.user;

import com.lt.win.service.base.UserServiceBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: David
 * @date: 02/05/2020
 * @description:
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class UserTests {
    @Autowired
    UserServiceBase userServiceBase;



    @Test
    public void getSlotFavorite() {
        userServiceBase.slotFavoriteByUid(1, 204);
    }

    @Test
    public void replace() {
        String str = "sss  aaa  ccc";
        str = str.replace(" ", "");
        System.out.println("str=" + str);
    }



}
