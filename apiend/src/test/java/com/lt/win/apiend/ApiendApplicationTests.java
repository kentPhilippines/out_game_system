package com.lt.win.apiend;

import com.lt.win.dao.generator.service.NoticeService;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.utils.DateNewUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

@SpringBootTest
@Slf4j
class ApiendApplicationTests {
    protected static final Pattern PATTERN = Pattern.compile("[0-9]{1,4}Hand$");
    public static String prefix = "/icon/habanero/rectangle/";
    @Resource
    NoticeService noticeServiceImpl;

    //    @Test
    void testNotice() {
        System.out.println(noticeServiceImpl.list());
    }

    @Test
    void regular() {
        /**
         * 昨天的开始时间、今天的开始时间
         */
        ZonedDateTime now = ZonedDateTime.now();
        int startTime = (int) now.withHour(0).withMinute(0).withSecond(0).withNano(0).toEpochSecond();
        int endTime = (int) now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0).toEpochSecond();
        log.info(startTime + "");
        log.info(endTime + "");

        Integer time = 1597821737;
        log.info(DateNewUtils.utc8Int(time) + "");
        log.info(DateNewUtils.utc8Zoned(time) + "");

        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
        System.out.println(dateTimeFormatter.format(zdt));
        ZonedDateTime zdt11 = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern(DateNewUtils.Format.yyyy_MM_dd_T_HH_mm_ss_XXX.getValue(), Locale.US);
        System.out.println(dateTimeFormatter1.format(zdt11));

        System.out.println("------log1-----" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zdt));
        System.out.println("------log2-----" + DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_DATE_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_OFFSET_DATE.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_DATE_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_DATE.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_LOCAL_DATE.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_INSTANT.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_LOCAL_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_OFFSET_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_ORDINAL_DATE.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_TIME.format(zdt));
        System.out.println("------log3-----" + DateTimeFormatter.ISO_WEEK_DATE.format(zdt));
    }

    @Test
    void enumTest() {
        String timeaa = "en";
        boolean validEnum = EnumUtils.isValidEnumIgnoreCase(LangEnum.class, "en");
        if (validEnum) {
            log.info("1111111111111111");
        } else {
            log.info("222222222222222");
        }

        timeaa = "en111";
        boolean validEnumaa = EnumUtils.isValidEnum(LangEnum.class, timeaa);
        if (validEnumaa) {
            log.info("333333333");
        } else {
            log.info("444444444");
        }
    }

    @Test
    void LocaleTest() {
        String img = "adffdsafadfad.png";
        Locale en1 = new Locale("en");
        int i = img.lastIndexOf('.');
        log.info(img.substring(0, i) + "_" + StringUtils.upperCase(en1.toString()) + img.substring(i));
        log.info("-----------------------------------");

        Locale en = new Locale("en");
        log.info(en.toString());

        Locale enus = new Locale("en", "US");
        log.info(enus.toString());
        Locale enuss = new Locale("en", "US", "-");
        log.info(enuss.toString());

    }

    private static String readResponse(HttpURLConnection urlConnection) {
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String responeLine;

            StringBuilder response = new StringBuilder();

            while ((responeLine = bufferedReader.readLine()) != null) {
                response.append(responeLine);
            }

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally  // closing stream
        {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Test
    void SimulateBrowserPost() {
        try {
            String urlEncodedContent = "session_id=402890D614F141B6B3AF0074C306FA7E&login_id=suki001&lang=h-CN";

            URL url = new URL("https://css.cfb2.net/api/auth_login.aspx");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");

            HttpURLConnection.setFollowRedirects(true);
            urlConnection.setRequestMethod("POST");

            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());

            // throws IOException
            dataOutputStream.writeBytes(urlEncodedContent);
            dataOutputStream.flush();
            dataOutputStream.close();

            String response = readResponse(urlConnection);

            System.out.println("Response : \n" + response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("11111111111");
    }
}