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

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brozen
 * @since 1.0
 */
@UtilityClass
public class InstantUtils {

    public static final String YMD_HMS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyy-MM-dd";
    public static final String HMS = "HH:mm:ss";

    /**
     * 默认使用的时区，与{@link #DEFAULT_ZONE_OFFSET}保持一致。
     */
    public static final ZoneId DEFAULT_ZONE;

    /**
     * 时区偏移量，可通过环境变量“limbo.instant.zone-offset”初始化，默认+8东八区。
     */
    public static final ZoneOffset DEFAULT_ZONE_OFFSET;

    static final Map<String, DateTimeFormatter> FORMATTERS = new ConcurrentHashMap<>();
    static {
        String zoneOffset = System.getProperty("limbo.instant.zone-offset", "+8");
        DEFAULT_ZONE = DEFAULT_ZONE_OFFSET = ZoneOffset.of(zoneOffset);

        FORMATTERS.put(YMD_HMS, DateTimeFormatter.ofPattern(YMD_HMS).withZone(DEFAULT_ZONE));
        FORMATTERS.put(YMD, DateTimeFormatter.ofPattern(YMD).withZone(DEFAULT_ZONE));
        FORMATTERS.put(HMS, DateTimeFormatter.ofPattern(HMS).withZone(DEFAULT_ZONE));
    }


    /**
     * 获取指定pattern的格式化器
     */
    private static DateTimeFormatter getFormatter(String pattern) {
        return FORMATTERS.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
    }


    /**
     * 将{@link Instant}格式化为指定格式，并格式化时进行时区转换。
     * @param instant 时间戳
     * @param pattern 日期格式
     * @param zone 格式化前，先将时间戳转换为指定时区，再进行格式化
     * @return 时间戳格式化后的字符串
     */
    public static String format(Instant instant, String pattern, ZoneId zone) {
        DateTimeFormatter formatter = getFormatter(pattern).withZone(zone);
        return formatter.format(instant);
    }


    /**
     * 将{@link Instant}格式化为指定格式，并格式化时进行时区转换。
     * @param instant 时间戳
     * @param pattern 日期格式
     * @param zone 格式化前，先将时间戳转换为指定时区，再进行格式化
     * @return 时间戳格式化后的字符串
     */
    public static String format(Instant instant, String pattern, String zone) {
        return format(instant, pattern, ZoneId.of(zone));
    }


    /**
     * 将{@link Instant}格式化为指定格式，并使用默认时区{@link #DEFAULT_ZONE}进行格式化。
     * @param instant 时间戳
     * @param pattern 日期格式
     * @return 时间戳格式化后的字符串
     */
    public static String format(Instant instant, String pattern) {
        return format(instant, pattern, DEFAULT_ZONE);
    }


    /**
     * 将{@link Instant}格式化为"<code>yyyy-MM-dd HH:mm:ss</code>"格式，并使用默认时区{@link #DEFAULT_ZONE}进行格式化。
     * @param instant 时间戳
     * @return 时间戳格式化后的字符串
     */
    public static String formatYMDHMS(Instant instant) {
        return FORMATTERS.get(YMD_HMS).format(instant);
    }


    /**
     * 将{@link Instant}格式化为"<code>yyyy-MM-dd</code>"格式，并使用默认时区{@link #DEFAULT_ZONE}进行格式化。
     * @param instant 时间戳
     * @return 时间戳格式化后的字符串
     */
    public static String formatYMD(Instant instant) {
        return FORMATTERS.get(YMD).format(instant);
    }


    /**
     * 将{@link Instant}格式化为"<code>HH:mm:ss</code>"格式，并使用默认时区{@link #DEFAULT_ZONE}进行格式化。
     * @param instant 时间戳
     * @return 时间戳格式化后的字符串
     */
    public static String formatHMS(Instant instant) {
        return FORMATTERS.get(HMS).format(instant);
    }


    /**
     * 将格式化日期字符串转换为{@link Instant}，日期字符串的时区通过参数指定。
     * @param instant 格式化的日期字符串
     * @param pattern 日期格式
     * @param zone 日期字符串的时区
     * @return 日期字符串对应的时间戳
     */
    public static Instant parse(String instant, String pattern, ZoneId zone) {
        DateTimeFormatter formatter = getFormatter(pattern).withZone(zone);
        return Instant.from(formatter.parse(instant));
    }


    /**
     * 将格式化日期字符串转换为{@link Instant}，日期字符串的时区通过参数指定。
     * @param instant 格式化的日期字符串
     * @param pattern 日期格式
     * @param zone 日期字符串的时区
     * @return 日期字符串对应的时间戳
     */
    public static Instant parse(String instant, String pattern, String zone) {
        return parse(instant, pattern, ZoneId.of(zone));
    }


    /**
     * 将格式化日期字符串转换为{@link Instant}，日期字符串的时区使用默认时区{@link #DEFAULT_ZONE}。
     * @param instant 格式化的日期字符串
     * @param pattern 日期格式
     * @return 日期字符串对应的时间戳
     */
    public static Instant parse(String instant, String pattern) {
        return parse(instant, pattern, DEFAULT_ZONE);
    }


    /**
     * 将"<code>yyyy-MM-dd HH:mm:ss</code>"格式的日期字符串转换为{@link Instant}，日期字符串的时区使用默认时区{@link #DEFAULT_ZONE}。
     * @param instant 格式化的日期字符串
     * @return 日期字符串对应的时间戳
     */
    public static Instant parseYMDHMS(String instant) {
        return Instant.from(FORMATTERS.get(YMD_HMS).parse(instant));
    }


    /**
     * 将"<code>yyyy-MM-dd</code>"格式的日期字符串转换为{@link Instant}，日期字符串的时区使用默认时区{@link #DEFAULT_ZONE}。
     * @param instant 格式化的日期字符串
     * @return 日期字符串对应的时间戳
     */
    public static Instant parseYMD(String instant) {
        return Instant.from(FORMATTERS.get(YMD).parse(instant));
    }


    /**
     * 将"<code>HH:mm:ss</code>"格式的日期字符串转换为{@link Instant}，日期字符串的时区使用默认时区{@link #DEFAULT_ZONE}。
     * @param instant 格式化的日期字符串
     * @return 日期字符串对应的时间戳
     */
    public static Instant parseHMS(String instant) {
        return Instant.from(FORMATTERS.get(HMS).parse(instant));
    }


    /**
     * 获取时间戳所在天的开始时间戳，即年月日保持不变，时分秒均为0的时间戳。
     * @param instant 时间戳
     * @return 入参时间戳当天的开始
     */
    public static Instant beginningOfDay(Instant instant) {
        return parse(formatYMD(instant) + " 00:00:00.000", YMD_HMS_SSS);
    }


    /**
     * 获取时间戳所在天的结束时间戳，即年月日保持不变，23时59分59秒999毫秒的时间戳。
     * @param instant 时间戳
     * @return 入参时间戳当天的结束
     */
    public static Instant endingOfDay(Instant instant) {
        return parse(formatYMD(instant) + " 23:59:59.999", YMD_HMS_SSS);
    }


    /**
     * 获取今天的开始时间戳（按默认时区{@link #DEFAULT_ZONE}获取时间），即年月日保持不变，时分秒均为0的时间戳。
     * @return 今天的开始
     */
    public static Instant beginningOfToday() {
        return beginningOfDay(Instant.now(Clock.system(DEFAULT_ZONE)));
    }


    /**
     * 获取今天的结束时间戳（按默认时区{@link #DEFAULT_ZONE}获取时间），即年月日保持不变，23时59分59秒999毫秒的时间戳。
     * @return 今天的结束
     */
    public static Instant endingOfToday() {
        return endingOfDay(Instant.now(Clock.system(DEFAULT_ZONE)));
    }

}
