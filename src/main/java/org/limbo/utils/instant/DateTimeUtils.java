package org.limbo.utils.instant;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author brozen
 * @date 2020/12/10
 */
public class DateTimeUtils {

    public static String format(LocalDateTime date, String pattern, ZoneId zone) {
        return InstantUtils.format(Instant.from(date), pattern, zone);
    }

    public static String format(LocalDateTime date, String pattern, String zone) {
        return InstantUtils.format(Instant.from(date), pattern, zone);
    }

    public static String format(LocalDateTime date, String pattern) {
        return InstantUtils.format(Instant.from(date), pattern);
    }

    public static String formatYMDHMS(LocalDateTime date) {
        return InstantUtils.formatYMDHMS(Instant.from(date));
    }

    public static String formatYMD(LocalDateTime date) {
        return InstantUtils.formatYMD(Instant.from(date));
    }

    public static String formatHMS(LocalDateTime date) {
        return InstantUtils.formatHMS(Instant.from(date));
    }

    public static LocalDateTime parse(String date, String pattern, ZoneId zone) {
        return LocalDateTime.from(InstantUtils.parse(date, pattern, zone));
    }

    public static LocalDateTime parse(String date, String pattern, String zone) {
        return LocalDateTime.from(InstantUtils.parse(date, pattern, zone));
    }

    public static LocalDateTime parse(String date, String pattern) {
        return LocalDateTime.from(InstantUtils.parse(date, pattern));
    }

    public static LocalDateTime parseYMDHMS(String date) {
        return LocalDateTime.from(InstantUtils.parseYMDHMS(date));
    }

    public static LocalDateTime parseYMD(String date) {
        return LocalDateTime.from(InstantUtils.parseYMD(date));
    }

    public static LocalDateTime parseHMS(String date) {
        return LocalDateTime.from(InstantUtils.parseHMS(date));
    }

    public static LocalDateTime startOfDay(LocalDateTime dateTime) {
        return dateTime.toLocalDate().atTime(0, 0, 0);
    }

    public static LocalDateTime endOfDay(LocalDateTime dateTime) {
        return dateTime.toLocalDate().atTime(23, 59, 59);
    }

    public static LocalDateTime startOfToday() {
        return LocalDate.now(InstantUtils.DEFAULT_ZONE).atTime(0, 0, 0);
    }

    public static LocalDateTime endOfToday() {
        return LocalDate.now(InstantUtils.DEFAULT_ZONE).atTime(23, 59, 59);
    }


}
