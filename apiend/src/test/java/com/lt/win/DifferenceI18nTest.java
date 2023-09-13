package com.lt.win;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.apiend.ApiendApplication;
import com.lt.win.apiend.base.DictionaryBase;
import com.lt.win.dao.generator.po.Promotions;
import com.lt.win.dao.generator.service.AuthRuleService;
import com.lt.win.dao.generator.service.PromotionsService;
import com.lt.win.service.base.AgentBase;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.utils.I18nUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @Author : Wells
 * @Date : 2020/10/1 7:30 下午
 * @Description : XX
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiendApplication.class)
@Slf4j
class DifferenceI18nTest {
    @Resource
    private DictionaryBase dictionaryBase;
    @Resource
    private AuthRuleService authRuleServiceImpl;
    @Resource
    private PromotionsService promotionsServiceImpl;
    @Resource
    private AgentBase agentBase;

    @SneakyThrows
    public static void writeFileContext(List<String> strings, String path) {
        File file = new File(path);
        //如果没有文件就创建
        if (!file.isFile()) {
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for (String l : strings) {
            writer.write(l + "\r\n");
        }
        writer.close();
    }

    /**
     * 1.获取字段表的tile与codeInfo的msg
     * 2.对比国际化文件
     * 3.列出差异字段
     */

    @Test
    void diffTest() {
        var map = dictionaryBase.getDictionary(null);
        var set = new HashSet<String>();
        map.values().forEach(x -> x.forEach(resDto -> {
            // set.add(resDto.getTitle());
        }));
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        var codeArr = CodeInfo.values();
        for (CodeInfo codeInfo : codeArr) {
            // set.add(codeInfo.getMsg());
            System.out.println(codeInfo.getMsg());
        }
//        var ruleSet = authRuleServiceImpl.list().stream().map(AuthRule::getTitle).collect(Collectors.toSet());
//        set.addAll(ruleSet);
//        set.forEach(e -> {
//            // e = e.replace(" ", "").trim();
//            var usMsg = I18nUtils.getLocaleMessage(e);
//            if (usMsg.equals(e) && p.matcher(usMsg).find()) {
//                System.out.println(e);
//            }
//        });
    }

    @Test
    void addCodeZh() {
        var set = promotionsServiceImpl.list();
        set.forEach(promotions -> {
            var json = JSONObject.parseObject(promotions.getCodeZh());
            var codeZh = json.getString("zh");
            codeZh = codeZh.replace(" ", "");
            json.put("th", I18nUtils.getLocaleMessage(codeZh));
            var rePromotions = new Promotions();
            rePromotions.setId(promotions.getId());
            rePromotions.setCodeZh(json.toJSONString());
            //  promotionsServiceImpl.updateById(rePromotions);

            //  var str = "update win_promotions set code_zh='" + json.toJSONString() + "' where id=" + promotions.getId() + ";";
            // System.out.println(str);
        });

    }

    @Test
    void testPro() {
        new I18nUtils().resolveCode(Locale.US, "Bank of China");
    }

    @Test
    void zhTransfer() {
        var list = new I18nUtils().resolveCode(Locale.SIMPLIFIED_CHINESE);
        writeFileContext(list, File.separator + "Users" + File.separator + "mac" + File.separator + "Desktop" + File.separator + "i18n.txt");
    }

    @Test
    void timeOffset() {
        var weekdays = 1;

        // 快乐周五
        var startTime = ConstData.ZERO;
        var endTime = ConstData.ZERO;
        if (true) {
            var time = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
            var indexOfWeek = time.getDayOfWeek().getValue();
            if (indexOfWeek >= weekdays) {
                // 开始时间
                startTime = (int) time.minusDays(indexOfWeek - weekdays).toInstant().getEpochSecond();
                endTime = startTime + 3600 * 24 * 7 - 1;
            } else {
                endTime = (int) time.plusDays(weekdays - indexOfWeek + 1).toInstant().getEpochSecond() - 1;
                startTime = endTime - 3600 * 24 * 7 + 1;
            }
        }

        log.info(startTime + "");
        log.info(endTime + "");
    }
}
