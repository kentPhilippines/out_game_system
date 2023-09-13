package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author admin
 */
@Component
public class JedisUtil {
    @Resource
    private JedisPool jedisPool;

    /**
     * 刷新到期时间
     *
     * @param key   key
     * @param value value
     */
    public void expire(String key, Integer value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.expire(key, value);
        }
    }

    /**
     * 设置缓存 默认超时时间1小时
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, String value) {
        try (Jedis ignored = this.jedisPool.getResource()) {
            setex(key, 3600, value);
        }
    }

    /**
     * 设置缓存 默认超时时间1小时
     *
     * @param key     key
     * @param seconds 有效时间
     * @param value   值
     */
    public void setex(String key, int seconds, String value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.setex(key, seconds, value);
        }
    }

    /**
     * 根据KEY获取 Value值
     *
     * @param key key
     * @return 值
     */
    public String get(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * 获取对应数据结构
     *
     * @param key   key
     * @param clazz 类型
     * @return 对象
     */
    public <T> T get(String key, Class<T> clazz) {
        return JSON.parseObject(get(key), clazz);
    }

    /**
     * 判断某个Key是否存在
     *
     * @param key key
     * @return true-存在 false-不存在
     */
    public boolean exists(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    /**
     * 查看Key的有效生命周期
     *
     * @param key key
     * @return 时间
     */
    public Long ttl(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.ttl(key);
        }
    }

    /**
     * 删除Key对应的缓存
     *
     * @param key key
     */
    public void del(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    /**
     * redis  lpush命令,插入数据到list的表头
     *
     * @param key    key
     * @param values 值列表
     */
    public void lpush(String key, String... values) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.lpush(key, values);
        }
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素
     *
     * @param key   key
     * @param start 开始索引
     * @param end   结束索引
     */
    public List<String> lrange(String key, int start, int end) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.lrange(key, start, end);
        }
    }


    /**
     * 移除并返回列表key的尾元素
     *
     * @param key key
     */
    public String rpop(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.rpop(key);
        }
    }

    /**
     * 返回递增后的值
     *
     * @author David
     */
    public BigDecimal incr(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            Long value = jedis.incr(key);
            return new BigDecimal(value);
        }
    }

    /**
     * 返回递增后的值
     *
     * @author David
     */
    public BigDecimal incr(String key, double value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return BigDecimal.valueOf(jedis.incrByFloat(key, value));
        }
    }

    // ******************************** SET START **********************************

    /**
     * 向Redis集合中插入数据
     */
    public void sadd(String key, String value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.sadd(key, value);
        }
    }

    /**
     * 获取集合的成员数
     */
    public Long scard(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.scard(key);
        }
    }

    /**
     * 判断 member 元素是否是集合 key 的成员
     */
    public Boolean sismember(String key, String value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.sismember(key, value);
        }
    }

    /**
     * 移除集合中的一个或多个成员元素, 不存在的成员元素会被忽略
     */
    public void srem(String key, String value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.srem(key, value);
        }
    }
    // ******************************** SET END **********************************


    // ******************************** HASH START **********************************

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     * 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0
     *
     * @param key   key
     * @param filed 表名
     * @param value 值
     */
    public void hset(String key, String filed, String value) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.hset(key, filed, value);
        }
    }

    /**
     * 存map
     *
     * @param key
     * @param map
     */
    public void hmSet(String key, Map<String, String> map) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.hmset(key, map);
        }
    }

    /**
     * 获取存储在哈希表中指定字段的值
     */
    public String hget(String key, String filed) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hget(key, filed);
        }
    }

    /**
     * 删除一个或多个哈希表字段
     */
    public Long hdel(String key, String filed) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hdel(key, filed);
        }
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     */
    public Boolean hexists(String key, String filed) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hexists(key, filed);
        }
    }

    /**
     * 获取所有哈希表中的字段
     */
    public Set<String> hkeys(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hkeys(key);
        }
    }

    /**
     * 获取哈希表中所有值
     */
    public List<String> hvals(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hvals(key);
        }
    }

    /**
     * 哈希表中的字段值加上指定增量值
     */
    public Long hincrBy(String key, String filed, long num) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.hincrBy(key, filed, num);
        }
    }


    // ******************************** HASH END **********************************

    /**
     * 用于返回列表的长度
     */
    public Long llen(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.llen(key);
        }
    }

    /**
     * 返回该下标的数值
     */
    public Long lindex(String key, int index) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return Long.valueOf(jedis.lindex(key, index));
        }
    }

    /**
     * 设置键的过期时间
     */
    public void expire(String key, int seconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.expire(key, seconds);
        }
    }

    /**
     * NX:只在键不存在时，才对键进行设置操作
     *
     * @param key
     * @param seconds
     * @return
     */
    public String setNX(String key, int seconds) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.set(key, key, "NX", "EX", seconds);
        }
    }

    /**
     * 清空缓存
     *
     * @author David
     */
    public boolean flushDb() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String result = jedis.flushDB();
            return StringUtils.equals("OK", result);
        }
    }

    public Map<String, String> hgetall(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            Map<String, String> map = jedis.hgetAll(key);
            return map;

        }
    }

    /**
     * 生产者
     *
     * @param key     通道名称
     * @param message 发送的消息
     */
    public void publish(String key, String message) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            //返回订阅者数量
            jedis.publish(key, message);
            System.out.println("生产者...message===" + message);
        }
    }

    /**
     * 消费者
     *
     * @param key 通道名称
     */
    public void subscribe(JedisPubSub jedisPubSub, String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            //第一个参数是处理接收消息，第二个参数是订阅的消息频道
            jedis.subscribe(jedisPubSub, key);
            System.out.println("消费者.." + key);
        }
    }


    /**
     * list右边加入
     *
     * @param key key
     */
    public void rightPush(String key, String message) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.rpush(key, message);
        }
    }


    /**
     * list左边加入
     *
     * @param key key
     */
    public void leftPush(String key, String message) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.lpush(key, message);
        }
    }

    /**
     * list右边阻塞读取
     *
     * @param key key
     */
    public List<String> rightBPop(String key) {
        List<String> list;
        try (Jedis jedis = this.jedisPool.getResource()) {
            list = jedis.brpop(0, key);
        } catch (Exception e) {
            list = rightBPop(key);
        }
        return list;
    }

    /**
     * list右边阻塞读取
     *
     * @param key key
     */
    public List<String> leftBPop(String key) {
        List<String> list;
        try (Jedis jedis = this.jedisPool.getResource()) {
            list = jedis.blpop(0, key);
        } catch (Exception e) {
            list = leftBPop(key);
        }
        return list;
    }

    /**
     * list右边读取
     *
     * @param key key
     */
    public String rightPop(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.rpop(key);
        }
    }

    /**
     * list右边读取
     *
     * @param key key
     */
    public String leftPop(String key) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.lpop(key);
        }
    }


    /**
     * 设置键的过期时间
     */
    public void expireByTimestamp(String key, Long timestamp) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.expireAt(key, timestamp);
        }
    }
}
