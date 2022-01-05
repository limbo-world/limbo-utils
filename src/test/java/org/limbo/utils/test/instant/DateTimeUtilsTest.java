package org.limbo.utils.test.instant;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.limbo.utils.time.DateTimeUtils.*;
import static org.limbo.utils.time.Formatters.YMD_HMS_SSS;

/**
 * @author Brozen
 * @since 2022-01-05
 */
public class DateTimeUtilsTest {



    @Test
    public void testFormat() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(format(now, "yyyy-MM-dd HH:mm", ZoneOffset.of("+10")));
        System.out.println(format(now, "yyyy-MM-dd HH:mm"));
        System.out.println(formatYMDHMS(now));
        System.out.println(formatYMD(now));
        System.out.println(formatHMS(now));
    }


    @Test
    public void testParse() {
        String nowString = "2022-01-04 18:50";
        LocalDateTime now = parse(nowString, "yyyy-MM-dd HH:mm", ZoneOffset.of("+9"));
        System.out.println(formatYMDHMS(now));

        nowString = "2022-01-04 18:50:12";
        now = parseYMDHMS(nowString);
        System.out.println(formatYMDHMS(now));

        nowString = "2022-01-04";
        now = parseYMD(nowString);
        System.out.println(formatYMDHMS(now));

        nowString = "18:50:12";
        now = parseHMS(nowString);
        System.out.println(formatYMDHMS(now));
    }


    @Test
    public void testBeginningAndEnd() {
        System.out.println(format(beginningOfToday(), YMD_HMS_SSS));
        System.out.println(format(endingOfToday(), YMD_HMS_SSS));
    }

}
