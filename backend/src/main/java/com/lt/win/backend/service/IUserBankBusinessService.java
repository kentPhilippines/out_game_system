package com.lt.win.backend.service;

import com.lt.win.service.io.bo.UserCacheBo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
public interface IUserBankBusinessService {
    /**
     * 提款地址列表
     *
     * @param dto dto
     * @return res
     */
    List<UserCacheBo.WithdrawalAddressResDto> withdrawalAddressList(UserCacheBo.WithdrawalAddressReqDto dto);

    /**
     * 新增提款地址
     *
     * @param dto dto
     */
    void addWithdrawalAddress(@Valid @RequestBody UserCacheBo.AddWithdrawalAddressReqDto dto);

    /**
     * 修改提款地址
     *
     * @param dto dto
     */
    void updateWithdrawalAddress(@Valid @RequestBody UserCacheBo.UpdateWithdrawalAddressReq dto);

    /**
     * 删除提款地址
     *
     * @param dto dto
     */
    void deleteWithdrawalAddress(@Valid @RequestBody UserCacheBo.DeleteWithdrawalAddressReqDto dto);
}
