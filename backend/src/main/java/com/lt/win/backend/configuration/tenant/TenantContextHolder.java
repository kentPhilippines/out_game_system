package com.lt.win.backend.configuration.tenant;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * 多租户Holder
 *
 * @author pangu
 * @date 2020-9-8
 */
@UtilityClass
public class TenantContextHolder {


    /**
     * 支持父子线程之间的数据传递
     */
    private final ThreadLocal<String> THREAD_LOCAL_TENANT = new TransmittableThreadLocal<>();
    private final ThreadLocal<String> THREAD_FIELD = new ThreadLocal<>();

    /**
     * TTL 设置租户ID<br/>
     * <b>谨慎使用此方法,避免嵌套调用。尽量使用 {@code TenantBroker} </b>
     *
     * @param tenantId 租户ID
     */
    public synchronized void setTenantId(String tenantId) {
        THREAD_LOCAL_TENANT.set(tenantId);
    }

    /**
     * 获取TTL中的租户ID
     *
     * @return String
     */
    public synchronized String getTenantId() {
        return THREAD_LOCAL_TENANT.get();
    }

    /**
     * 清除tenantId
     */
    public synchronized void clear() {
        THREAD_LOCAL_TENANT.remove();
    }

    public void setField(String tenantId) {
        THREAD_FIELD.set(tenantId);
    }

    /**
     * 获取TTL中的租户ID
     *
     * @return String
     */
    public String getField() {
        return THREAD_FIELD.get();
    }

    /**
     * 清除tenantId
     */
    public void clearFiled() {
        THREAD_FIELD.remove();
    }

}


