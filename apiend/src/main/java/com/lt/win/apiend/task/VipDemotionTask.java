package com.lt.win.apiend.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.po.UserLevel;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.UserLevelService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.win.service.common.Constant.USER_ROLE_CS;

/**
 * @Auther: wells
 * @Date: 2022/10/15 18:06
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VipDemotionTask {
    private final UserService userServiceImpl;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final UserLevelService userLevelServiceImpl;

    @Scheduled(cron = "0 0 3 1 * ?")
    public void execute() {
        demotion();
    }


    public boolean demotion() {
        List<User> userList = userServiceImpl.lambdaQuery()
                .ne(User::getRole, USER_ROLE_CS)
                .gt(User::getLevelId, 1)
                .list();
        List<Integer> uidList = userList.stream().map(User::getId).collect(Collectors.toList());
        //开始时间与结束时间
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.add(Calendar.MONTH, -1);
        calendarStart.set(Calendar.DAY_OF_MONTH, 1);
        String startTimeStr = DateUtils.yyyyMMdd(calendarStart.getTime()) + " 00:000:00";
        int startTime = (int) (Objects.requireNonNull(DateUtils.yyyyMMddHHmmss(startTimeStr)).getTime() / 1000);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.DAY_OF_MONTH, 0);
        String endTimeStr = DateUtils.yyyyMMdd(calendarEnd.getTime()) + " 23:59:59";
        int endTime = (int) (Objects.requireNonNull(DateUtils.yyyyMMddHHmmss(endTimeStr)).getTime() / 1000);
        QueryWrapper<CoinDepositRecord> wrapper = new QueryWrapper<CoinDepositRecord>()
                .select("ifNull(sum(real_amount),0) as realAmountSum,uid")
                .in("uid", uidList)
                .eq("status", 1)
                .ge("created_at", startTime)
                .le("created_at", endTime)
                .groupBy("uid");
        List<Map<String, Object>> mapList = coinDepositRecordServiceImpl.listMaps(wrapper);
        Map<Integer, BigDecimal> userDepositMap = new HashMap<>();
        mapList.forEach(map ->
                userDepositMap.put(Integer.valueOf(map.get("uid").toString()),
                        new BigDecimal(map.get("realAmountSum").toString()))
        );
        //所有等级对应的降级充值额度
        List<UserLevel> levelList = userLevelServiceImpl.list();
        Map<Integer, Integer> levelMap = levelList.stream()
                .collect(Collectors.toMap(UserLevel::getId, UserLevel::getScoreRelegation));
        Map<Integer, Integer> scoreUpgradeMinMap = levelList.stream()
                .collect(Collectors.toMap(UserLevel::getId, UserLevel::getScoreUpgradeMin));

        //需降级的用户
        List<User> relegationList = new ArrayList<>();
        Integer now = DateUtils.getCurrentTime();
        userList.forEach(user -> {
            Integer scoreRelegation = levelMap.getOrDefault(user.getLevelId(), 0);
            Integer depositCoinSum = userDepositMap.getOrDefault(user.getId(), BigDecimal.ZERO).intValue();
            if (scoreRelegation > depositCoinSum) {
                //进行降级
                int levelId = user.getLevelId() - 1;
                Integer scoreUpgradeMin = scoreUpgradeMinMap.getOrDefault(levelId, 0);
                user.setLevelId(levelId);
                user.setScore(scoreUpgradeMin);
                user.setUpdatedAt(now);
                relegationList.add(user);
            }
        });
        userServiceImpl.updateBatchById(relegationList);
        return true;
    }
}
