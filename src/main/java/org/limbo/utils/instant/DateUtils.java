package org.limbo.utils.instant;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author brozen
 * @since 1.0
 */
public class DateUtils {

    public static String format(LocalDate date, String pattern, ZoneId zone) {
        return InstantUtils.format(Instant.from(date), pattern, zone);
    }

    public static String format(LocalDate date, String pattern, String zone) {
        return InstantUtils.format(Instant.from(date), pattern, zone);
    }

    public static String format(LocalDate date, String pattern) {
        return InstantUtils.format(Instant.from(date), pattern);
    }

    public static String formatYMDHMS(LocalDate date) {
        return InstantUtils.formatYMDHMS(Instant.from(date));
    }

    public static String formatYMD(LocalDate date) {
        return InstantUtils.formatYMD(Instant.from(date));
    }

    public static String formatHMS(LocalDate date) {
        return InstantUtils.formatHMS(Instant.from(date));
    }

    public static LocalDate parse(String date, String pattern, ZoneId zone) {
        return LocalDate.from(InstantUtils.parse(date, pattern, zone));
    }

    public static LocalDate parse(String date, String pattern, String zone) {
        return LocalDate.from(InstantUtils.parse(date, pattern, zone));
    }

    public static LocalDate parse(String date, String pattern) {
        return LocalDate.from(InstantUtils.parse(date, pattern));
    }

    public static LocalDate parseYMDHMS(String date) {
        return LocalDate.from(InstantUtils.parseYMDHMS(date));
    }

    public static LocalDate parseYMD(String date) {
        return LocalDate.from(InstantUtils.parseYMD(date));
    }

    public static LocalDate parseHMS(String date) {
        return LocalDate.from(InstantUtils.parseHMS(date));
    }
    
}
