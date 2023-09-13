package com.lt.win.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lt.win.service.server.CoinLogService;
import com.lt.win.service.mapper.CoinLogMapper;
import com.lt.win.service.io.dto.CoinLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账变明细 服务实现类
 * </p>
 *
 * @author David
 * @since 2022-08-14
 */
@Service
public class CoinLogServiceImpl extends ServiceImpl<CoinLogMapper, CoinLog> implements CoinLogService {

}
