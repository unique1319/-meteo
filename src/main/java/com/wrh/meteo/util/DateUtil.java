package com.wrh.meteo.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/25 17:19
 * @describe 时间处理工具类
 */
public class DateUtil {

    public static final String YM_FMT = "yyyy-MM";
    public static final String YMD_FMT = "yyyy-MM-dd";
    public static final String YMDH_FMT = "yyyy-MM-dd HH";
    public static final String YMDHM_FMT = "yyyy-MM-dd HH:mm";
    public static final String YMDHMS_FMT = "yyyy-MM-dd HH:mm:ss";
    public static final String YM_COMPACT_FMT = "yyyyMM";
    public static final String YMD_COMPACT_FMT = "yyyyMMdd";
    public static final String YMDH_COMPACT_FMT = "yyyyMMddHH";
    public static final String YMDHM_COMPACT_FMT = "yyyyMMddHHmm";
    public static final String YMDHMS_COMPACT_FMT = "yyyyMMddHHmmss";
    public static final String YMD_CHINESE_FMT = "yyyy年MM月dd日";
    public static final String YMDH_CHINESE_FMT = "yyyy年MM月dd日HH时";
    public static final String YMDHM_CHINESE_FMT = "yyyy年MM月dd日HH时mm分";
    public static final String YMD_SLASH_FMT = "yyyy/MM/dd";

    private DateUtil() {
    }

    /**
     * Date转LocalDateTime
     * 使用系统时区
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     * 使用系统时区
     *
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDateTime 格式化
     *
     * @param localDateTime LocalDateTime
     * @param pattern       format
     * @return String
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * LocalDateTime 解析
     *
     * @param dateTimeStr 时间字符串
     * @param pattern     format
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 北京时转世界时
     *
     * @param bjTime 北京时
     * @return 世界时
     */
    public static LocalDateTime bj2Utc(LocalDateTime bjTime) {
        ZonedDateTime zonedTime = bjTime.atZone(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime converted = zonedTime.withZoneSameInstant(ZoneOffset.UTC);
        return converted.toLocalDateTime();
    }

    /**
     * 世界时转北京时
     *
     * @param utcTime 世界时
     * @return 北京时
     */
    public static LocalDateTime utc2Bj(LocalDateTime utcTime) {
        ZonedDateTime zonedTime = utcTime.atZone(ZoneOffset.UTC);
        ZonedDateTime converted = zonedTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        return converted.toLocalDateTime();
    }

    /**
     * 解析Date
     *
     * @param dateTime 时间字符串
     * @param pattern  时间格式
     * @return Date
     */
    public static Date parseDate(String dateTime, String pattern) {
        LocalDateTime localDateTime = parse(dateTime, pattern);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 格式化Date
     *
     * @param date    Date对象
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String formatDate(Date date, String pattern) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return format(localDateTime, pattern);
    }

    /**
     * 获取两时间相差天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差天数
     */
    public static long getDurationDays(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toDays();
    }

    /**
     * 获取两时间相差小时数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差小时数
     */
    public static long getDurationHours(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toHours();
    }

    /**
     * 获取两时间相差分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差分钟数
     */
    public static long getDurationMinutes(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toMinutes();
    }


}
