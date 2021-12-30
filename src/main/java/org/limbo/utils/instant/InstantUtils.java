/*
 *
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.limbo.utils.instant;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brozen
 * @since 1.0
 */
public class InstantUtils {

    private static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    private static final String YMD = "yyyy-MM-dd";
    private static final String HMS = "HH:mm:ss";

    /**
     * DEFAULT_ZONE 通过环境变量“limbo.instant.zone”初始化，默认UTC+8东八区
     */
    static final ZoneId DEFAULT_ZONE;
    static final Map<String, DateTimeFormatter> FORMATTERS = new ConcurrentHashMap<>();
    static {
        String zoneId = System.getProperty("limbo.instant.zone", "UTC+8");
        DEFAULT_ZONE = ZoneId.of(zoneId);

        FORMATTERS.put(YMD_HMS, DateTimeFormatter.ofPattern(YMD_HMS).withZone(DEFAULT_ZONE));
        FORMATTERS.put(YMD, DateTimeFormatter.ofPattern(YMD).withZone(DEFAULT_ZONE));
        FORMATTERS.put(HMS, DateTimeFormatter.ofPattern(HMS).withZone(DEFAULT_ZONE));
    }

    private static DateTimeFormatter getFormatter(String pattern, ZoneId zone) {
        return FORMATTERS.computeIfAbsent(pattern, _pattern -> DateTimeFormatter.ofPattern(_pattern).withZone(zone));
    }

    private static DateTimeFormatter getFormatter(String pattern) {
        return getFormatter(pattern, DEFAULT_ZONE);
    }

    public static String format(Instant instant, String pattern, ZoneId zone) {
        DateTimeFormatter formatter = getFormatter(pattern);
        return formatter.format(instant);
    }

    public static String format(Instant instant, String pattern, String zone) {
        return format(instant, pattern, ZoneId.of(zone));
    }

    public static String format(Instant instant, String pattern) {
        return format(instant, pattern, DEFAULT_ZONE);
    }

    public static String formatYMDHMS(Instant instant) {
        return FORMATTERS.get(YMD_HMS).format(instant);
    }

    public static String formatYMD(Instant instant) {
        return FORMATTERS.get(YMD).format(instant);
    }

    public static String formatHMS(Instant instant) {
        return FORMATTERS.get(HMS).format(instant);
    }

    public static Instant parse(String instant, String pattern, ZoneId zone) {
        DateTimeFormatter formatter = getFormatter(pattern, zone);
        return Instant.from(formatter.parse(instant));
    }

    public static Instant parse(String instant, String pattern, String zone) {
        return parse(instant, pattern, ZoneId.of(zone));
    }

    public static Instant parse(String instant, String pattern) {
        return parse(instant, pattern, DEFAULT_ZONE);
    }

    public static Instant parseYMDHMS(String instant) {
        return Instant.from(FORMATTERS.get(YMD_HMS).parse(instant));
    }

    public static Instant parseYMD(String instant) {
        return Instant.from(FORMATTERS.get(YMD).parse(instant));
    }

    public static Instant parseHMS(String instant) {
        return Instant.from(FORMATTERS.get(HMS).parse(instant));
    }

    public static Instant startOfDay(Instant instant) {
        return parseYMDHMS(formatYMD(instant) + " 00:00:00");
    }

    public static Instant endOfDay(Instant instant) {
        return parseYMDHMS(formatYMD(instant) + " 23:59:59");
    }

    public static Instant startOfToday() {
        return startOfDay(Instant.now(Clock.system(DEFAULT_ZONE)));
    }

    public static Instant endOfToday() {
        return endOfDay(Instant.now(Clock.system(DEFAULT_ZONE)));
    }


    public static void main(String[] args) {
        System.setProperty("limbo.instant.zone", "UTC+9");
        System.out.println(formatYMDHMS(Instant.now()));
        System.out.println(formatYMDHMS(startOfToday()));
        System.out.println(formatYMDHMS(endOfToday()));
    }
}
