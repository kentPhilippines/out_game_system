package com.lt.win.apiend.service;

import com.lt.win.service.io.bo.VipBo;

import java.util.List;

/**
 * <p>
 * 代理中心业务处理接口
 * </p>
 *
 * @author David
 * @since 2022/4/22
 */
public interface IVipService {
    /**
     * Vip-membership benefits
     *
     * @return res
     */
    VipBo.MemberShipResDto memberShipDetails();

    /**
     * Vip-membership level benefits
     *
     * @return res
     */
    List<VipBo.MemberShipLevelResDto> memberShipLevelDetails();
}
