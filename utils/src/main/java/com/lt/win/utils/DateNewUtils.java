package com.lt.win.utils;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间处理类: 使用Java1.8 新的日期和时间API
 * 主要涉及类:LocalDateTime,LocalDate,LocalTime,ZonedDateTime,Instant,ZoneId,ZoneOffset,Duration
 * 【 Date Calendar 旧类禁用 】
 *
 * @author: David
 * @date: 07/06/2020
 */
public class DateNewUtils {
    public static final char SEP_SECOND_MILLS;
    public static final String UTC = "UTC";
    public static final String BEIJING_ZONE = "+8";
    public static final String GMT = "GMT";

    static {
        SEP_SECOND_MILLS = '.';
    }

    /**
     * 今日开始时间
     *
     * @return 今日开始时间
     */
    public static int todayStart() {
        return (int) ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).toEpochSecond();
    }

    /**
     * 今日结束时间
     *
     * @return 今日开始时间
     */
    public static int todayEnd() {
        return (int) ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59).toEpochSecond();
    }

    /**
     * 昨日开始时间
     *
     * @return 昨日开始时间
     */
    public static int yesterdayStart() {
        return (int) ZonedDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0).toEpochSecond();
    }

    /**
     * 昨日结束时间
     *
     * @return 昨日结束时间
     */
    public static int yesterdayEnd() {
        return (int) ZonedDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59).toEpochSecond();
    }

    /**
     * 昨日结束时间
     *
     * @return 昨日结束时间
     */
    public static String yesterday() {
        return DateTimeFormatter.ofPattern(Format.yyyyMMdd.value).format(ZonedDateTime.now().minusDays(1));
    }


    /**
     * 把指定时间转为UTC8 Int返回
     *
     * @param time 时间 2019-11-01 12:11:11
     * @return 目的地时间
     */
    public static int utc8Int(@NotNull Integer time) {
        return (int) ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneOffset.of("+08:00")).toInstant().getEpochSecond();
    }

    /**
     * 把指定时间转为UTC8 Zoned返回
     */
    @NotNull
    public static ZonedDateTime utc8Zoned(@NotNull Integer time) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneOffset.of("+08:00"));
    }

    /**
     * 把指定时间转为UTC8 Zoned返回
     */
    @NotNull
    public static ZonedDateTime utc0Zoned(@NotNull Integer time) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneOffset.of("+00:00"));
    }

    /**
     * 获取UTC+8 当前时间
     *
     * @param time   当前时区时间
     * @param format 返回时间格式
     * @return UTC+8时间
     */
    @NotNull
    public static String utc8Str(@NotNull ZonedDateTime time, @NotNull Format format) {
        ZonedDateTime utc = time.withZoneSameInstant(getZoneId("UTC", "+8"));
        return DateTimeFormatter.ofPattern(format.value).format(utc);
    }

    /**
     * 把指定时区时间 转为 UTC+0
     *
     * @param time   时间 2019-11-01 12:11:11
     * @param format yyyy-MM-dd HH:mm:ss 多种格式参考枚取
     * @return 目的地时间
     */
    @NotNull
    public static String utc0Str(@NotNull ZonedDateTime time, @NotNull Format format) {
        ZonedDateTime utc = time.withZoneSameInstant(getZoneId("UTC", "+0"));
        return DateTimeFormatter.ofPattern(format.value).format(utc);
    }

    /**
     * 把指定时区时间 转为 UTC-4
     *
     * @param time 时间 2019-11-01 12:11:11
     * @return 目的地时间
     */
    @NotNull
    public static String utc4Str(@NotNull Integer time, @NotNull Format format) {
        ZonedDateTime utc = ZonedDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneOffset.of("-04:00"));
        return DateTimeFormatter.ofPattern(format.value).format(utc);
    }

    /**
     * 获取指定时区时间
     *
     * @param time   当前时区时间
     * @param format 时间格式
     * @return 指定时区时间
     */
    @NotNull
    public static String timeToDestZoneTime(@NotNull ZonedDateTime time, @NotNull Format format, ZoneId destZoneId) {
        return DateTimeFormatter.ofPattern(format.value).format(time.withZoneSameInstant(destZoneId));
    }

    /**
     * 获取当前时间(int)
     *
     * @return 当前时间
     */
    public static int now() {
        return (int) Instant.now().getEpochSecond();
    }

    /**
     * 获取当前时间(int)
     *
     * @return 当前时间
     */
    public static Long timestamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取当前时间(int)
     *
     * @return 当前时间
     */
    public static int getGMT0() {
        return (int) Instant.now().atZone(getZoneId(GMT, "+0")).toEpochSecond();
    }

    /**
     * @param prefix UTC/GMT/UT
     * @param offset <li>{@code Z} - for UTC
     *               <li>{@code +h}
     *               <li>{@code +hh}
     *               <li>{@code +hh:mm}
     *               <li>{@code -hh:mm}
     *               <li>{@code +hhmm}
     *               <li>{@code -hhmm}
     *               <li>{@code +hh:mm:ss}
     *               <li>{@code -hh:mm:ss}
     *               <li>{@code +hhmmss}
     *               <li>{@code -hhmmss}
     * @return ZoneId
     */
    public static ZoneId getZoneId(String prefix, String offset) {
        return ZoneId.ofOffset(prefix, ZoneOffset.of(offset));
    }

    /**
     * 获取默认时区
     *
     * @return ZoneId
     */
    public static ZoneId getZoneId() {
        return ZoneId.ofOffset(UTC, ZoneOffset.of(BEIJING_ZONE));
    }

    /**
     * 根据指定字符串 设定 日期格式
     *
     * @param format 格式字符串枚举
     * @return DateTimeFormatter
     */
    @NotNull
    public static DateTimeFormatter getDateTimeFormatter(@NotNull Format format) {
        return DateTimeFormatter.ofPattern(format.value);
    }

    /**
     * @param format 时间格式
     * @return 当前时间字符串 2019-01-01 11:11:11
     */
    @NotNull
    public static String getNowFormatTime(@NotNull Format format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format.value);
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    /**
     * 指定时间 按格式换成为 ZonedDateTime
     *
     * @param time   时间字符串 2020-06-06T04:44:22.717
     * @param format dateTimeFormatter
     * @param zoneId 时区
     * @return ZonedDateTime
     */
    @NotNull
    public static ZonedDateTime getZonedDateTime(String time, Format format, ZoneId zoneId) {
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern(format.value);
        LocalDateTime localDateTime = LocalDateTime.parse(strTimeAdaptFormat(time, format), dateTimeFormatter1);
        return localDateTime.atZone(zoneId);
    }

    /**
     * 指定时间 按格式换成为 ZonedDateTime
     *
     * @param time              时间字符串 2020-06-06T04:44:22.717
     * @param dateTimeFormatter dateTimeFormatter
     * @param zoneId            时区
     * @return ZonedDateTime
     */
    @NotNull
    public static ZonedDateTime getZonedDateTime(String time, DateTimeFormatter dateTimeFormatter, ZoneId zoneId) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.atZone(zoneId);
    }

    /**
     * 把指定时区时间 转为 目的地时区时间 以int类型时间返回
     *
     * @param time      时间 2019-11-01 12:11:11
     * @param format    yyyy-MM-dd HH:mm:ss 多种格式参考枚取
     * @param oriPrefix 原时区 UTC
     * @param oriOffset 偏移量 -4
     * @param desPrefix 新时区 UTC
     * @param desOffset 偏移量 +8
     * @return 目的地时间
     */
    public static int oriTimeZoneToDesTimeZone(String time, Format format, String oriPrefix, String oriOffset, String desPrefix, String desOffset) {
        // 旧时间 按 Format 转成 ZonedDateTime格式
        ZonedDateTime zCreatedAt = DateNewUtils.getZonedDateTime(
                time,
                format,
                DateNewUtils.getZoneId(oriPrefix, oriOffset)
        );
        // 旧的时区 转为新的时区 以Int类型返回
        return (int) zCreatedAt.withZoneSameInstant(
                DateNewUtils.getZoneId(desPrefix, desOffset)
        ).toInstant().getEpochSecond();

    }

    /**
     * 把指定时区时间 转为 目的地时区时间 以int类型时间返回
     *
     * @param time      时间 2019-11-01 12:11:11
     * @param format    yyyy-MM-dd HH:mm:ss 多种格式参考枚取
     * @param oriPrefix 原时区 UTC
     * @param oriOffset 偏移量 -4
     * @return 目的地时间
     */
    public static int oriTimeZoneToBeiJingZone(String time, Format format, String oriPrefix, String oriOffset) {
        return oriTimeZoneToDesTimeZone(time, format, oriPrefix, oriOffset, UTC, BEIJING_ZONE);
    }

    /**
     * 按时区转date 类型
     *
     * @param time      时间 2019-11-01 12:11:11
     * @param format    yyyy-MM-dd HH:mm:ss 多种格式参考枚取
     * @param oriPrefix 原时区 GMT
     * @param oriOffset 偏移量 -4
     * @return Date
     */
    public static Date zonedDateTime2Date(String time, Format format, String oriPrefix, String oriOffset) {
        ZonedDateTime zonedDateTime = DateNewUtils.getZonedDateTime(
                time,
                format,
                DateNewUtils.getZoneId(oriPrefix, oriOffset)
        );
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 根据ZonedDateTime计算 -> 相差几天
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return days
     */
    public static long daysDiff(ZonedDateTime start, ZonedDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 根据LocalDate计算 -> 相差几天
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return days
     */
    public static long daysDiff(LocalDate start, LocalDate end) {
        Period between = Period.between(start, end);
        return between.getDays();
    }

    /**
     * 时间字符串 和 指定格式匹配 位数不足补0
     *
     * @param time   时间字符串
     * @param format 指定格式
     * @return 补齐后的值
     */
    public static String strTimeAdaptFormat(String time, Format format) {
        int len;
        // 字符串长度不够自动补0
        switch (format.value) {
            case "yyyy-MM-dd HH:mm:ss":
            case "yyyy-MM-dd'T'HH:mm:ss":
                len = 0;
                break;
            case "yyyy-MM-dd HH:mm:ss.S":
            case "yyyy-MM-dd'T'HH:mm:ss.S":
                len = 1;
                break;
            case "yyyy-MM-dd HH:mm:ss.SS":
            case "yyyy-MM-dd'T'HH:mm:ss.SS":
                len = 2;
                break;
            case "yyyy-MM-dd HH:mm:ss.SSS":
            case "yyyy-MM-dd'T'HH:mm:ss.SSS":
            default:
                len = 3;
                break;
        }

        StringBuilder stringBuilder = new StringBuilder(time);
        int i = time.indexOf(SEP_SECOND_MILLS);
        if (len == 0) {
            return stringBuilder.toString();
        } else if (i == -1) {
            stringBuilder.append(SEP_SECOND_MILLS).append("0".repeat(len));
        } else if (i == 0) {
            String substring = time.substring(time.indexOf(SEP_SECOND_MILLS));
            stringBuilder.append("0".repeat(Math.max(0, len - substring.length())));
        } else {
            String substring = time.substring(time.indexOf(SEP_SECOND_MILLS));
            if (substring.length() < len + 1) {
                stringBuilder.append("0".repeat(Math.max(0, len - substring.length()) + 1));
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 计算年月日日期范围列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    日期格式化yyyy-MM-dd 或 yyyyMMdd
     * @return List<String> String=yyyy-MM-dd 或 yyyyMMdd
     */
    public static List<String> daysRangeList(int startTime, int endTime, @NotNull Format format) {
        List<String> list = new ArrayList<>();
        ZonedDateTime starZonedDateTime = DateNewUtils.utc8Zoned(startTime);
        long range = DateNewUtils.daysDiff(starZonedDateTime, DateNewUtils.utc8Zoned(endTime));
        for (int i = 0; i < range + 1; i++) {
            ZonedDateTime tmp = starZonedDateTime.plusDays(i);
            String dayName = tmp.format(DateNewUtils.getDateTimeFormatter(format));
            list.add(dayName);
        }
        return list;
    }

    /**
     * 指定的DateTimeFormatter 格式
     */
    @Getter
    public enum Format {
        /**
         * yyyy-MM-dd HH:mm:ss
         */
        yyyyMMddHHmmss("yyyyMMddHHmmss"),
        yyyyMMdd("yyyyMMdd"),
        yyyy_MM_dd("yyyy-MM-dd"),
        yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
        yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),
        yyyy_MM_dd_T_HH_mm_ss("yyyy-MM-dd'T'HH:mm:ss"),
        yyyy_MM_dd_HH_mm_ss_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
        yyyy_MM_dd_T_HH_mm_ss_SS("yyyy-MM-dd'T'HH:mm:ss.SS"),
        yyyy_MM_dd_T_HH_mm_ss_SSS("yyyy-MM-dd'T'HH:mm:ss.SSS"),
        yyyy_MM_dd_T_HH_mm_ss_XXX("yyyy-MM-dd'T'HH:mm:ssXXX"),
        dd_MM_yyyy_HH_mm_ss_SSS("dd/MM/yyyy HH:mm:ss.SSS"),//29/12/2020 18:00:00
        ;

        String value;

        Format(String value) {
            this.value = value;
        }
    }
}
