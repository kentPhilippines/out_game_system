package com.lt.win.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Supplier;

/**
 * java Bean转换工具
 *
 * @author william
 * @version 1.0
 * @date 2020/2/14 0014 下午 4:12
 */
public class BeanConvertUtils {


    /**
     * 实体类映射
     * 使用beanCopy
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> clazz) {
        Assert.notNull(source, "source must not be null");
        Mapper mapper = new DozerBeanMapper();
        return mapper.map(source, clazz);
    }

    /**
     * 属性复制，操作字节码
     * 示例: BeanConvertUtils.beanCopy(source, userVO）
     *
     * @param source
     * @param destination
     */
    public static <T> T beanCopy(Object source, T destination) {
        Assert.notNull(destination, "destination must not be null");
        if (source == null) {
            return destination;
        }
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), destination.getClass(), false);
        beanCopier.copy(source, destination, null);
        return destination;
    }


    /**
     * 属性复制，操作字节码
     * 示例: BeanConvertUtils.beanCopy(source, UserVO::new)
     *
     * @param source      数据源类
     * @param destination 目标类::new(eg: UserVO::new)
     * @return
     */
    public static <T> T beanCopy(Object source, Supplier<T> destination) {
        T t = destination.get();
        if (source == null) {
            return t;
        }
        Assert.notNull(t, "destination must not be null");
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), t.getClass(), false);
        beanCopier.copy(source, t, null);
        return t;
    }


    /**
     * 分页集合数据的属性拷贝,操作字节码
     * 示例: BeanConvertUtils.copyPageProperties(pageSource, UserVO::new）
     *
     * @param pageSource: 数据源类
     * @param target:     目标类::new(eg: UserVO::new)
     * @return
     */
    public static <S, T> Page<T> copyPageProperties(Page<S> pageSource, Supplier<T> target) {
        return copyPageProperties(pageSource, target, null);
    }

    /**
     * 分页集合数据的属性拷贝,操作字节码
     * 比如coin_bet主键id为long类型8字节,64位,前端js获取数值类型数据只能精确获取53位二进制数据,直接返回long会丢失精度,
     * 因此需要将long转成String类型返回.
     * 示例:
     * Page<MyBetRecordsResponseDTO> pageRecords =
     * BeanConvertUtils.copyPageProperties(
     * listCoinBet,
     * MyBetRecordsResponseDTO::new,
     * (source, target) -> {
     * target.setId(String.valueOf(source.getId()));
     * });
     *
     * @param pageSource: 数据源类
     * @param target:     目标类::new(eg: UserVO::new)
     * @param callBack    特殊字段处理器
     * @return
     */
    public static <S, T> Page<T> copyPageProperties(Page<S> pageSource, Supplier<T> target, BeanCopyUtilsCallBack<S, T> callBack) {
        Assert.notNull(target, "target must not be null");
        List<S> records = pageSource.getRecords();
        List<T> list = copyListProperties(records, target, callBack);
        Page<T> pageTarget = new Page<>();
        pageTarget.setTotal(pageSource.getTotal());
        pageTarget.setCurrent(pageSource.getCurrent());
        pageTarget.setSize(pageSource.getSize());
        pageTarget.setRecords(list);
        return pageTarget;
    }


    /**
     * 集合数据的属性拷贝,操作字节码
     * 示例: BeanConvertUtils.copyListProperties(listSource, UserVO::new）
     *
     * @param sources: 数据源类
     * @param target:  目标类::new(eg: UserVO::new)
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     *
     * @param sources:  数据源类
     * @param target:   目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanCopyUtilsCallBack<S, T> callBack) {
        Assert.notNull(target, "target must not be null");
        List<T> list = Lists.newArrayList();
        if (sources == null || sources.isEmpty()) {
            return list;
        }
        Class<?> sourceClazz = sources.get(0).getClass();
        Class<?> targetClazz = target.get().getClass();
        /*使用反射*/
        BeanCopier beanCopier = BeanCopier.create(sourceClazz, targetClazz, false);
        for (S source : sources) {
            T t = target.get();
            /*操作字节码*/
            beanCopier.copy(source, t, null);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }


    /**
     * 属性赋值
     *
     * @param source 数据源类
     * @param target 目标类::new(eg: UserVO::new)
     * @param <T>    泛型
     * @return
     */
    public static <T> T copyProperties(Object source, Supplier<T> target) {
        T t = target.get();
        if (source == null) {
            return t;
        }
        Assert.notNull(t, "destination must not be null");
        BeanUtils.copyProperties(source, t);
        return t;


    }


    /**
     * 属性赋值
     *
     * @param source 数据源类
     * @param target 目标类::new(eg: UserVO::new)
     * @param <T>    泛型
     * @return
     */
    public static <S, T> T copyProperties(S source, Supplier<T> target, BeanCopyUtilsCallBack<S, T> callBack) {
        T t = target.get();
        if (source == null) {
            return t;
        }
        Assert.notNull(t, "destination must not be null");
        t = beanCopy(source, target);
        if (callBack != null) {
            // 回调
            callBack.callBack(source, t);
        }
        return t;
    }
}
