package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.lt.win.backend.service.IUserBankBusinessService;
import com.lt.win.dao.generator.po.WithdrawalAddress;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.dao.generator.service.WithdrawalAddressService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.UserCacheBo;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
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
    public List<UserCacheBo.WithdrawalAddressResDto> withdrawalAddressList(ReqPage<UserCacheBo.WithdrawalAddressReqDto> dto) {
        var res = withdrawalAddressServiceImpl.lambdaQuery()
                // .orderByDesc(dto::getSortKey,)
                //    .eq( WithdrawalAddress::getCategoryCurrency,dto.getData().getCategory())
                .eq(WithdrawalAddress::getUid, dto.getData().getUid())
                /*
                .eq(WithdrawalAddress::getUid, dto.getUid())
                .eq(dto.getCategory() == 1, WithdrawalAddress::getCategoryCurrency, "PIX")
                .in(dto.getCategory() == 0, WithdrawalAddress::getCategoryCurrency, List.of("ERC-20", "TRC-20"))*/
                .orderByDesc(WithdrawalAddress::getId)
                .list();
        return BeanConvertUtils.copyListProperties(
                res,
                UserCacheBo.WithdrawalAddressResDto::new,
                (ori, dest) -> {
                    var temp = JSON.parseObject(ori.getAddress()).toJavaObject(UserCacheBo.WithdrawalAddressResDto.class);
                    dest.setAccountNo(temp.getAccountNo());
                    dest.setAccountType(temp.getAccountType());
                    dest.setAddress(temp.getAddress());
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
        var address = dto.getAccountNumber();
        if ("3".equalsIgnoreCase(dto.getCategoryTransfer())) {
            var json = new JSONObject();
            json.put("accountNo", dto.getAccountNo());
            json.put("address", address);
            address = JSON.toJSONString(json);
            withdrawalAddress.setCategoryCurrency(1);
            withdrawalAddress.setCategoryTransfer(Integer.valueOf(dto.getCategoryTransfer()));
        } else if ("1".equals(dto.getCategoryTransfer())) {
            withdrawalAddress.setCategoryCurrency(0);
            withdrawalAddress.setCategoryTransfer(Integer.valueOf(dto.getCategoryTransfer()));
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
