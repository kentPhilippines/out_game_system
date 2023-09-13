package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.io.enums.MessageDeviceEnum;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Auther: wells
 * @Date: 2022/9/17 18:02
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class UserOnlineCache {
    private final JedisUtil jedisUtil;

    @PostConstruct
    public void clearOnLineUserList() {
        jedisUtil.del(KeyConstant.UID_ONLINE_HASH);
    }

    public List<Integer> getOnLineUerList() {
        return jedisUtil.hkeys(KeyConstant.UID_ONLINE_HASH).stream()
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    public void setOnlineUser(Integer uid,String json) {
        jedisUtil.hset(KeyConstant.UID_ONLINE_HASH, uid.toString(), json);
    }

    public void deleteOnlineUser(Integer uid) {
        jedisUtil.hdel(KeyConstant.UID_ONLINE_HASH, uid.toString());
    }


}
