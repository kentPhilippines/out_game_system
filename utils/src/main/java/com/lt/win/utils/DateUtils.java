package com.lt.win.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: David
 * @date: 04/03/2020
 * @description:
 */
@Slf4j
public class DateUtils {
    public final static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYYMMDDHHMM24 = "yyyyMMddHHmm";
    public final static String YYYYMMDD = "yyyy-MM-dd";
    public final static String HHMMSS = "HH:mm:ss";
    public final static String MMDD = "MM月dd日";
    public final static SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);

    /**
     * string to int
     *
     * @return
     */
    public static long dateToTimestamp(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMM24);
            return sdf.parse("202008070000").getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * int to datetime
     *
     * @param param
     * @return
     */
    public static String UTC(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.format(nowTimeLong);
    }

    /**
     * int
     * t0 /20200722/
     *
     * @return
     */
    public static String yyyyMMdd2(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat("yyyyMMdd");
        return ymdhmsFormat.format(nowTimeLong);
    }

    public static String utcYyyyMMddHHmmssSSSS(int param) {
        return getDateTimeWithTimeZone((long) param * 1000, TimeZone.getTimeZone("UTC"), "yyyyMMddHHmmssSSSS");
    }


    /**
     * int
     * t0 YYYYMMDDHH24MI
     *
     * @return
     */
    public static String YYYYMMDDHH24MI(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDDHHMM24);
        return ymdhmsFormat.format(nowTimeLong);
    }


    /**
     * long to string
     *
     * @return
     */
    public static String longToString(long date) {
        try {
            return sdf.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取当前时间:int类型
     *
     * @return int
     */
    public static Integer getCurrentTime() {
        long l = System.currentTimeMillis() / 1000;
        Integer currentTime = Integer.parseInt(String.valueOf(l));
        return currentTime;
    }

    /**
     * 获取本年年份
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * int to yyyy-MM-dd HH:mm:ss
     *
     * @param param
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String yyyyMMddHHmmss(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
        String nowTimeStr = ymdhmsFormat.format(nowTimeLong);
        //Date nowTimeDate = ymdhmsFormat.parse(nowTimeStr);
        return nowTimeStr;
    }

    /**
     * yyyy-MM-dd HH:mm:ss to date
     *
     * @param param
     * @return
     * @throws ParseException
     */
    public static Date yyyyMMddHHmmss(String param) {
        try {
            DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
            Date nowTimeDate = ymdhmsFormat.parse(param);
            return nowTimeDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * yyyy-MM-dd to date
     *
     * @param param
     * @return
     * @throws ParseException
     */
    public static Date yyyyMMdd(String param) {
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDD);
        Date nowTimeDate = null;
        try {
            nowTimeDate = ymdhmsFormat.parse(param);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nowTimeDate;
    }

    /**
     * 获取yyyymmdd日期格式
     *
     * @return
     */
    public static String getYyyyMMddStr() {
        return yyyyMMdd(new Date());
    }

    /**
     * date to yyyy-MM-dd
     *
     * @param param
     * @return
     * @throws ParseException
     */
    public static String yyyyMMdd(Date param) {
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDD);
        String nowTimeDate = ymdhmsFormat.format(param);
        return nowTimeDate;
    }

    public static String yyyyMMddHHmmss(long param) {
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
        String nowTimeStr = ymdhmsFormat.format(param);
        //Date nowTimeDate = ymdhmsFormat.parse(nowTimeStr);
        return nowTimeStr;
    }

    /**
     * int to yyyy-MM-dd
     *
     * @param param
     * @return yyyy-MM-dd
     */
    public static String yyyyMMdd(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDD);
        String nowTimeStr = ymdhmsFormat.format(nowTimeLong);
        //Date nowTimeDate = ymdhmsFormat.parse(nowTimeStr);
        return nowTimeStr;
    }

    public static String hhmmss(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat(HHMMSS);
        String nowTimeStr = ymdhmsFormat.format(nowTimeLong);
        //Date nowTimeDate = ymdhmsFormat.parse(nowTimeStr);
        return nowTimeStr;
    }

    /**
     * 根据当前时间获取-n天
     *
     * @param n 天
     * @return 格式后的时间
     */
    public static String getWeekTimeBeforeByCurrentTime(int n) {
        Date firstDay = getBeforeOrAfterDate(new Date(), -n);
        return sdf.format(firstDay);
    }

    /**
     * int to
     *
     * @param param
     * @return
     */

    public static String yyyyMMddHH(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTimeStr = ymdhmsFormat.format(nowTimeLong);
        return nowTimeStr;
    }

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getWeekTimeBeforeByCurrentTime() {
        return getWeekTimeBeforeByCurrentTime(0);
    }

    /**
     * 获取10天前的时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getWeekTimeBeforeByCurrentTime_10() {
        return getWeekTimeBeforeByCurrentTime(10);
    }

    /**
     * 根据当前时间，添加或减去指定的时间量。例如，要从当前日历时间减去 5 天，可以通过调用以下方法做到这一点：
     * add(Calendar.DAY_OF_MONTH, -5)。
     *
     * @param date 指定时间
     * @param num  为时间添加或减去的时间天数
     * @return
     */
    public static Date getBeforeOrAfterDate(Date date, int num) {
        Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
        calendar.add(Calendar.DATE, num);
        Date d = calendar.getTime();//把日历转换为Date
        return d;
    }

    /**
     * 字符串转时间毫秒
     *
     * @param dateStr
     * @return
     */
    public static long strTranDate(String dateStr) {
        DateFormat ymdhmsFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
        try {
            Date date = ymdhmsFormat.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间
     *
     * @return 20220824131313999
     */
    public static String yyyyMMddHHmmssSSS() {
        return ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    /**
     * 获取当前时间
     *
     * @return 20220824131313999
     */
    public static String yyyyMMddHHmmssffff() {
        return ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS"));
    }

    /**
     * 字符串转int
     *
     * @param dateStr
     * @return
     */
    public static int strTranInt(String dateStr) {
        DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = ymdhmsFormat.parse(dateStr);
            return (int) (date.getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 相差几天
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @return 天数
     */
    public static int differenceDays(Integer starTime, Integer endTime) {
        int days = (endTime * 1000 - starTime * 1000) / (1000 * 3600 * 24);
        return days;
    }


    /**
     * 当前时间-n天
     *
     * @param day
     * @return 10位int
     */
    public static int subtractDate(int day) {
        String weekTimeBeforeByCurrentTime = DateUtils.getWeekTimeBeforeByCurrentTime(day);
        long temp = DateUtils.strTranDate(weekTimeBeforeByCurrentTime) / 1000;
        return Integer.parseInt(String.valueOf(temp));
    }

    /**
     * 获取今天凌晨时间
     *
     * @return 10位int
     */
    public static int todayZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long l = calendar.getTimeInMillis() / 1000;
        Integer zeroTime = Integer.parseInt(String.valueOf(l));
        return zeroTime;
    }

    /**
     * int to yyyy-MM-dd
     *
     * @param param
     * @return yyyy-MM-dd
     */
    public static String MMDD(int param) {
        long nowTimeLong = new Long(param).longValue() * 1000;
        DateFormat ymdhmsFormat = new SimpleDateFormat(MMDD);
        String nowTimeStr = ymdhmsFormat.format(nowTimeLong);
        //Date nowTimeDate = ymdhmsFormat.parse(nowTimeStr);
        return nowTimeStr;
    }

    /**
     * 获取(2019-03-22T09:11:52.000+0000)格式时间
     *
     * @return yyyy-MM-dd
     */
    public static String getDateWithTimeZone(Date date, TimeZone zone) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        if (zone != null) {
            df.setTimeZone(zone);
        }
        return df.format(date);
    }

    /**
     * @desc: 根据时间戳时区获取当前时间
     * @params: [timestamp, zone, format]
     * @return: java.lang.String
     * @author: David
     * @date: 15/05/2020
     */
    public static String getDateTimeWithTimeZone(Long timestamp, TimeZone zone, String format) {
        DateFormat df = new SimpleDateFormat(format);
        if (zone != null) {
            df.setTimeZone(zone);
        }
        return df.format(timestamp);
    }

    public static void main(String[] args) {
        log.info(getDateTimeWithTimeZone(1666712734000L, TimeZone.getTimeZone("UTC"), "yyyyMMddHHmmssSSSS"));
        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSS");
        //LocalDateTime parse1 = LocalDateTime.parse("2020-06-06 12:44:22.717", dateTimeFormatter);

//        System.out.println(Instant.now().getEpochSecond());
//
//
//        LocalDateTime parse1 = LocalDateTime.parse("2020-06-06T12:44:22.717");
//        ZoneId utc = ZoneId.ofOffset("UTC", ZoneOffset.of("+0"));
//
//        System.out.println(parse1.atZone(utc));
//        log.info(".............................");
//
//
//        ZonedDateTime zonedDateTime22 = parse1.atZone(utc);
//        log.info("UTC: " + utc);
//
//        log.info("============:" + zonedDateTime22.toInstant().toEpochMilli());
//
//        log.info(parse1.toLocalDate().toString());
//        log.info(parse1.toLocalTime().toString());
//
//        log.info("zonedDateTime22: " + zonedDateTime22.toLocalDate().toString());
//        log.info("zonedDateTime22: " + zonedDateTime22.toLocalTime().toString());
//        log.info("zonedDateTime22: " + zonedDateTime22.toLocalDateTime().toString());
//
//        log.info("-----------------------------------------");
//
//        ZonedDateTime zonedDateTime = zonedDateTime22.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
//        log.info("zonedDateTime22 Beijing: " + zonedDateTime.toLocalDateTime().toString());
//
//
//        log.info("-----------------------------------------");
//
//
//        Timestamp timestamp = Timestamp.from(Instant.now());
//        log.info(timestamp.toString());
//        log.info(String.valueOf(timestamp.getTime()));


        //String sDate = getDateWithTimeZone(new Date(System.currentTimeMillis()), TimeZone.getTimeZone("GMT-4"));
        ////LocalDate date = System.currentTimeMillis();
        //Long l = System.currentTimeMillis();
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //df.setTimeZone(TimeZone.getTimeZone("GMT-4"));

        //try {
        //    Date parse = df.parse(getDateWithTimeZone(new Date(), TimeZone.getTimeZone("GMT-4")));
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        log.info(yyyyMMddHHmmssSSS());
        log.info(yyyyMMddHHmmssffff());

        log.info("1111");
    }
}
