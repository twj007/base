package com.mall.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DateFormatUtil {

    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter MONTH_FORMATTER    = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter DATE_FORMATTER     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER     = DateTimeFormatter.ofPattern("HH:mm:ss");


    public static String getNow(String pattern){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return now.format(formatter);
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /**
     * 日期相隔天数
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    /**
     * 日期相隔小时
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 日期相隔分钟
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 日期相隔毫秒数
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 返回当前的日期
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前时间
     * @return
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    public static LocalDateTime get() {
        return LocalDateTime.now();
    }

    public static int getYear() {
        return get().getYear();
    }

    public static LocalDateTime withYear(int year) {
        return get().withYear(year);
    }

    public static int getMonth() {
        return get().getMonthValue();
    }

    public static LocalDateTime firstDayOfThisYear(int year) {
        return withYear(year).with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    /**
     * @Title: getFirstDayOfThisYear
     * @Description: 获取设置所属年最初时间
     * @param year
     * @return String
     */
    public static String getFirstDayOfThisYear(int year) {
        LocalDateTime firstDayOfThisYear = firstDayOfThisYear(year);
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    public static LocalDateTime lastDayOfThisYear(int year) {
        return withYear(year).with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }

    /**
     * @Title: getLastDayOfThisYear
     * @Description: 获取设置所属年最后时间
     * @param year
     * @return String
     */
    public static String getLastDayOfThisYear(int year) {
        LocalDateTime lastDayOfThisYear = lastDayOfThisYear(year);
        return DATETIME_FORMATTER.format(lastDayOfThisYear);
    }

    /**
     * @Title: getFirstDayOfThisMonth
     * @Description: 获取本月的第一天
     * @return String
     */
    public static String getFirstDayOfThisMonth() {
        LocalDateTime firstDayOfThisYear = get().with(TemporalAdjusters.firstDayOfMonth());
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * @Title: getFirstDayOfThisMonth
     * @Description: 获取本月的最末天
     * @return String
     */
    public static String getLastDayOfThisMonth() {
        LocalDateTime firstDayOfThisYear = get().with(TemporalAdjusters.lastDayOfMonth());
        return DATETIME_FORMATTER.format(firstDayOfThisYear);
    }

    /**
     * @Title: plusDays
     * @Description: 当前日期向后推多少天
     * @param days
     * @return LocalDateTime
     */
    public static LocalDateTime plusDays(int days) {
        return get().plusDays(days);
    }

    /**
     * @Title: firstDayOfWeekInYearMonth
     * @Description: 获取指定年月的第一个周一
     * @param year
     * @param month
     * @return LocalDateTime
     */
    public static LocalDateTime firstDayOfWeekInYearMonth(int year, int month) {
        return get().withYear(year).withMonth(month).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    /**
     * @Title: todayStart
     * @Description: 当天开始时间
     * @return LocalDateTime
     */
    public static LocalDateTime todayStart() {
        return LocalDateTime.of(getCurrentLocalDate(), LocalTime.MIN);
    }

    /**
     * @Title: todayEnd
     * @Description: 当天结束时间
     * @return LocalDateTime
     */
    public static LocalDateTime todayEnd() {
        return LocalDateTime.of(getCurrentLocalDate(), LocalTime.MAX);
    }

    /**
     * @Title: getStartDayOfWeekToString
     * @Description: 获取周第一天
     * @return String
     */
    public static String getStartDayOfWeekToString() {
        return formatLocalDate(getStartDayOfWeek());
    }

    public static LocalDate getStartDayOfWeek() {
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate
                .getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        return getCurrentLocalDate().with(FIRST_OF_WEEK);
    }

    /**
     * @Title: getEndDayOfWeekToString
     * @Description: 获取周最后一天
     * @return String
     */
    public static String getEndDayOfWeekToString() {
        return formatLocalDate(getEndDayOfWeek());
    }

    public static LocalDate getEndDayOfWeek() {
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(
                DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return getCurrentLocalDate().with(LAST_OF_WEEK);
    }

    public static void main(String[] args) {
        //
        Integer year = 2019;
        System.out.println(getFirstDayOfThisYear(year));
        System.out.println(getLastDayOfThisYear(year));
        //
        System.out.println(DATETIME_FORMATTER.format(plusDays(1)));
        System.out.println(DATETIME_FORMATTER.format(plusDays(-1)));

        // 取第一个周一
        LocalDate ld = LocalDate.parse("2019-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println(DATE_FORMATTER.format(ld));
        //
        System.out.println(DATETIME_FORMATTER.format(firstDayOfWeekInYearMonth(year, 3)));
        System.out.println("-------------------");
        // new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format();
        System.out.println(getStartDayOfWeekToString());
        System.out.println(getEndDayOfWeekToString());
        System.out.println("-------------------");
        System.out.println(DATETIME_FORMATTER.format(todayStart()));
        System.out.println(DATETIME_FORMATTER.format(todayEnd()));
    }

}
