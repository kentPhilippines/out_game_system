package com.lt.win.utils;


import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate时间处理
 *
 * @author william
 * @version 1.0
 * @date
 */
public class LocalDateUtils {

    private final static DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
    private final static DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDate strToDate(String time) {
        LocalDate date = LocalDate.parse(time, yyyyMMddHHmmss);
        return date;
    }

    public static LocalDate strToDateYYYYMMDD(String time) {
        LocalDate date = LocalDate.parse(time, yyyyMMdd);
        return date;
    }


    public static String getNowString() {
        String date = LocalDateTime.now().format(yyyyMMddHHmmss);
        return date;
    }






    /**
     * java 毫秒类型时间转DateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime longTransferToDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * java 毫秒类型时间转Time
     *
     * @param timestamp
     * @return
     */
    public static LocalTime longTransferToTime(long timestamp) {
        LocalDateTime localDateTimeOfTimestamp = longTransferToDateTime(timestamp);
        LocalTime localTime = localDateTimeOfTimestamp.toLocalTime();
        return localTime;
    }

    /**
     * java 毫秒类型时间转Date
     *
     * @param timestamp
     * @return
     */
    public static LocalDate longTransferToDate(long timestamp) {
        LocalDateTime localDateTimeOfTimestamp = longTransferToDateTime(timestamp);
        LocalDate localDate = localDateTimeOfTimestamp.toLocalDate();
        return localDate;
    }

    /**
     * java 毫秒类型时间转Time字符串
     *
     * @param timestamp
     * @return
     */
    public static String longTransferToTimeText(long timestamp) {
        LocalDateTime localDateTimeOfTimestamp = longTransferToDateTime(timestamp);
        LocalTime localTime = localDateTimeOfTimestamp.toLocalTime();
        String format = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return format;
    }

    /**
     * java 毫秒类型时间转Date字符串
     *
     * @param timestamp
     * @return
     */
    public static String longTransferToDateText(long timestamp, String ofPattern) {
        LocalDateTime localDateTimeOfTimestamp = longTransferToDateTime(timestamp);
        LocalDate localDate = localDateTimeOfTimestamp.toLocalDate();
        String format = localDate.format(DateTimeFormatter.ofPattern(ofPattern));
        return format;
    }


    public static String longTransferString(long timestamp, String ofPattern) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.format(DateTimeFormatter.ofPattern(ofPattern));
    }

    /**
     * java 毫秒类型时间转DateTime字符串
     *
     * @param timestamp
     * @return
     */
    public static String longTransferToDateTimeText(long timestamp) {
        LocalDateTime localDateTimeOfTimestamp = longTransferToDateTime(timestamp);
        String format = localDateTimeOfTimestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return format;
    }


    public static String addMins(String dateTime,long time) {
        LocalDateTime parse = LocalDateTime.parse(dateTime, yyyyMMddHHmmss);
        LocalDateTime localDateTime = parse.plusMinutes(time);
        String format = localDateTime.format(yyyyMMddHHmmss);
        return format;
    }


    /**
     * 根据dateTime获取10位的Integer类型的时间戳
     *
     * @param dateTime
     * @return
     */
    public static Integer dateTimeToTimstamp(LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Timestamp timestamp = Timestamp.from(dateTime.atZone(zoneId).toInstant());
        long time = timestamp.getTime();
        return (int) (time / 1000);
    }
}
