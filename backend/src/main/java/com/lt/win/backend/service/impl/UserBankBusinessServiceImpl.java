package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.backend.service.IUserBankBusinessService;
import com.lt.win.dao.generator.po.WithdrawalAddress;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.dao.generator.service.WithdrawalAddressService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.UserCacheBo;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.lt.win.service.common.Constant.PIX;
import static com.lt.win.service.common.Constant.TRC_20;

/**
 * 用户提款银行地址
 *
 * @author David
 * @since 2022/8/25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserBankBusinessServiceImpl implements IUserBankBusinessService {
    private final WithdrawalAddressService withdrawalAddressServiceImpl;
    private final UserService userServiceImpl;

    /**
     * 提款地址列表
     *
     * @param dto dto
     * @return res
     */
    @Override
    public List<UserCacheBo.WithdrawalAddressResDto> withdrawalAddressList(UserCacheBo.WithdrawalAddressReqDto dto) {
        var res = withdrawalAddressServiceImpl.lambdaQuery()
                .eq(WithdrawalAddress::getUid, dto.getUid())
                .eq(dto.getCategory() == 2, WithdrawalAddress::getCategoryCurrency, "PIX")
                .in(dto.getCategory() == 1, WithdrawalAddress::getCategoryCurrency, List.of("ERC-20", "TRC-20"))
                .orderByDesc(WithdrawalAddress::getId)
                .list();

        return BeanConvertUtils.copyListProperties(
                res,
                UserCacheBo.WithdrawalAddressResDto::new,
                (ori, dest) -> {
                    if (ori.getCategoryCurrency().equals(ori.getCategoryCurrency())) {
                        var temp = JSON.parseObject(ori.getAddress()).toJavaObject(UserCacheBo.WithdrawalAddressResDto.class);
                        dest.setAccountNo(temp.getAccountNo());
                        dest.setAccountType(temp.getAccountType());
                        dest.setAddress("");
                    }
                }
        );
    }

    /**
     * 新增提款地址
     *
     * @param dto dto
     */
    @Override
    public void addWithdrawalAddress(UserCacheBo.AddWithdrawalAddressReqDto dto) {
        var user = userServiceImpl.getById(dto.getUid());
        if (Optional.ofNullable(user).isEmpty()) {
            throw new BusinessException(CodeInfo.USER_NOT_EXISTS);
        }
        WithdrawalAddress withdrawalAddress = new WithdrawalAddress();
        var address = dto.getAddress();
        if (PIX.equalsIgnoreCase(dto.getCode())) {
            var json = new JSONObject();
            json.put("accountType", dto.getAccountType());
            json.put("accountNo", dto.getAccountNo());
            address = JSON.toJSONString(json);
            withdrawalAddress.setCategoryCurrency(1);
            withdrawalAddress.setCategoryTransfer(4);
        } else if (TRC_20.equals(dto.getCode())) {
            withdrawalAddress.setCategoryCurrency(0);
            withdrawalAddress.setCategoryTransfer(1);
        }
        withdrawalAddress.setUid(dto.getUid());
        withdrawalAddress.setUsername(user.getUsername());
        withdrawalAddress.setAddress(address);
        var now = DateUtils.getCurrentTime();
        withdrawalAddress.setCreatedAt(now);
        withdrawalAddress.setUpdatedAt(now);
        withdrawalAddressServiceImpl.save(withdrawalAddress);
    }

    /**
     * 修改提款地址
     *
     * @param dto dto
     */
    @Override
    public void updateWithdrawalAddress(UserCacheBo.UpdateWithdrawalAddressReq dto) {
        var withdrawalAddress = BeanConvertUtils.beanCopy(dto, WithdrawalAddress::new);
        if (Optional.ofNullable(dto.getAddress()).isEmpty() && Objects.equals(dto.getCode(), "PIX")) {
            var json = new JSONObject();
            json.put("accountType", dto.getAccountType());
            json.put("accountNo", dto.getAccountNo());
            withdrawalAddress.setAddress(JSON.toJSONString(json));
        }
        withdrawalAddress.setUpdatedAt(DateUtils.getCurrentTime());
        withdrawalAddressServiceImpl.updateById(withdrawalAddress);
    }

    /**
     * 删除提款地址
     *
     * @param dto dto
     */
    @Override
    public void deleteWithdrawalAddress(UserCacheBo.DeleteWithdrawalAddressReqDto dto) {
        withdrawalAddressServiceImpl.removeById(dto.getId());
    }
}
