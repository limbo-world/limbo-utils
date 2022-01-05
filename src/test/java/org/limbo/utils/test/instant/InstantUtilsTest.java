package org.limbo.utils.test.instant;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.ZoneOffset;

import static org.limbo.utils.time.InstantUtils.*;
import static org.limbo.utils.time.Formatters.*;

/**
 * @author Brozen
 * @since 2022-01-04
 */
public class InstantUtilsTest {

    @Before
    public void testZone() {
        System.out.println("============= 默认时区信息 =============");
        System.out.println(DEFAULT_ZONE);
        System.out.println(DEFAULT_ZONE_OFFSET);
        System.out.println("============= 默认时区信息 [END] =============");
    }


    @Test
    public void testDefaultZoneAndOffset() {
//        System.setProperty("limbo.time.zone-offset", "+9");
        System.out.println(formatYMDHMS(Instant.now()));
        System.out.println(formatYMDHMS(beginningOfToday()));
        System.out.println(formatYMDHMS(endingOfToday()));
    }


    @Test
    public void testFormat() {
        Instant now = Instant.now();
        System.out.println(format(now, "yyyy-MM-dd HH:mm", ZoneOffset.of("+9")));
        System.out.println(format(now, "yyyy-MM-dd HH:mm"));
        System.out.println(formatYMDHMS(now));
        System.out.println(formatYMD(now));
        System.out.println(formatHMS(now));
    }


    @Test
    public void testParse() {
        String nowString = "2022-01-04 18:50";
        Instant now = parse(nowString, "yyyy-MM-dd HH:mm", ZoneOffset.of("+9"));
        System.out.println(formatYMDHMS(now));
    }


    @Test
    public void testBeginningAndEnd() {
        System.out.println(format(beginningOfToday(), YMD_HMS_SSS));
        System.out.println(format(endingOfToday(), YMD_HMS_SSS));
        System.out.println(endingOfToday().toEpochMilli());
    }


}
