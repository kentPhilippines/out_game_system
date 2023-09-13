package com.lt.win.apiend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.apiend.aop.annotation.FieldAnnotation;
import com.lt.win.apiend.io.EncryptDto;
import com.lt.win.dao.generator.po.DictItem;
import com.lt.win.dao.generator.po.Promotions;
import com.lt.win.dao.generator.service.DictItemService;
import com.lt.win.dao.generator.service.PromotionsService;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.I18nUtils;
import com.lt.win.utils.SpringUtils;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wells
 * @date: 2020/7/7
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiendApplication.class)
public class InternationalTest {
    @Autowired
    private DictItemService dictItemServiceImpl;

    @Test
    void translate() {
        //  var msg = I18nUtils.getLocaleMessage("HongKong  odds");
    }

    @Test
    void testJson() {
        String str = "{\n" +
                "\t\"code\": 0,\n" +
                "\t\"data\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"codeZh\": \"首充优惠\",\n" +
                "\t\t\t\"id\": 1,\n" +
                "\t\t\t\"promotionsResDtoList\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"category\": 1,\n" +
                "\t\t\t\t\t\"endedAt\": 1585991266,\n" +
                "\t\t\t\t\t\"id\": 1,\n" +
                "\t\t\t\t\t\"startedAt\": 1585991266\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"msg\": \"\"\n" +
                "}";
        var jsonObject = JSONObject.parseObject(str);
        var json = analysisJson(jsonObject);
        System.out.println("json=" + json);
    }

    public Object analysisJson(Object reqObject) {
        if (reqObject instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) reqObject;
            for (int i = 0; i < jsonArray.size(); i++) {
                analysisJson(jsonArray.get(i));
            }
        }
        if (reqObject instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) reqObject;
            Iterator<String> it = jsonObject.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Object object = jsonObject.get(key);
                //如果得到的是数组
                if (object instanceof JSONArray) {
                    JSONArray objArray = (JSONArray) object;
                    analysisJson(objArray);
                }
                //如果key中是一个json对象
                else if (object instanceof JSONObject) {
                    analysisJson((JSONObject) object);
                }
                //如果key中是其他
                else {
                    jsonObject.put(key, I18nUtils.getLocaleMessage(object.toString()));
                }
            }
        }
        return reqObject;
    }

    @Test
    void encrypt() {
        ApplicationContext context = SpringUtils.getApplicationContext();
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
        Class<EncryptDto> encryptDtoClass = EncryptDto.class;
        boolean b = encryptDtoClass.isAnnotationPresent(FieldAnnotation.class);
        if (b) {
            FieldAnnotation annotation = encryptDtoClass.getAnnotation(FieldAnnotation.class);
            //注解参数
            boolean createFlag = annotation.createFlag();
            if (createFlag) {
                // get the BeanDefinitionBuilder
                BeanDefinitionBuilder beanDefinitionBuilder =
                        BeanDefinitionBuilder.genericBeanDefinition("EncryptDto");
                // get the BeanDefinition
                BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
                // register the bean
                beanDefinitionRegistry.registerBeanDefinition("encryptDto", beanDefinition);
                EncryptDto dto = (EncryptDto) SpringUtils.getBean("encryptDto");
                String name = dto.getName();
                System.out.println("name=" + name);
            }
        }
    }

    @Autowired
    private PromotionsService promotionsServiceImpl;

    @Test
    void testActivity() {
        var list = promotionsServiceImpl.list();
        try {
            var langList = List.of("en", "th", "zh", "vi");
            for (Promotions x : list) {
                if (x.getId() != 6 && x.getId() != 9 && x.getId() != 12 && x.getId() != 13) {
                    JSONObject jsonObject = new JSONObject();
                    for (String lang : langList) {
                        // var description = activityDescription.getDescription(x.getId(), lang);
                        var path = "/Users/mac/Downloads/i18n/" + lang + "/promotions_" + x.getId() + ".txt";
                        File file = new File(path);
                        BufferedReader proBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line = "";
                        var stringBuilder = new StringBuilder();
                        while ((line = proBuffer.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        var reStr = stringBuilder.toString();
                        if (Strings.isNotEmpty(reStr)) {
                            jsonObject.put(lang, reStr);
                        }
                    }
                    Promotions promotions = new Promotions();
                    promotions.setId(x.getId());
                    promotions.setDescript(jsonObject.toJSONString());
                    promotionsServiceImpl.updateById(promotions);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        //activityDescription.getStaticDescription(1);
        //activityDescription.getFileContent(1);
        // activityDescription.changeValueByPropertyName(
        //  "/i18n/messages_en_US.properties","体育赛事","Sports11");
    }

    @Test
    void testInstant() {
        var time1 = Instant.now().getEpochSecond();
        var time2 = DateNewUtils.now();
        var diff = time1 - time2;
        System.out.println("diff=" + diff);
    }


    @SneakyThrows
    @Test
    public void readText() {
        List<DictItem> dictItemList = dictItemServiceImpl.list();
//        for (DictItem dictItem : dictItemList) {
//            System.out.println(dictItem.getRemark()+"="+dictItem.getTitle());
//        }
        Map<String, String> map = new HashMap<>();
        FileInputStream fileInputStream = new FileInputStream("/Users/wells/Documents/dict");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            String[] arr = str.split("=");
            if (arr.length == 1) {
                //     System.out.println(str);
                continue;
            }
            String title = arr[0].trim();
            String esTitle = arr[1];
            // esTitle= esTitle.replace(" ","");
            //System.out.println(esTitle+"="+title);
            map.put(title,esTitle);
//            for (DictItem dictItem : dictItemList) {
//                if (dictItem.getTitle().trim().equals(title)) {
//                    System.out.println("esTitle =" + esTitle);
//                    dictItem.setTitle(esTitle);
//                    dictItemServiceImpl.updateById(dictItem);
//                }
//            }
            //   System.out.println("str =" + str);
        }
       // map.keySet().forEach(out::println);
        map.values().forEach(System.out::println);
        fileInputStream.close();
        bufferedReader.close();
    }


}
