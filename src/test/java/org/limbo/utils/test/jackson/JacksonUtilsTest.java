package org.limbo.utils.test.jackson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.limbo.utils.collection.Maps;
import org.limbo.utils.jackson.JacksonUtils;

import java.time.LocalDateTime;

/**
 * @author Brozen
 * @since 2022-01-05
 */
public class JacksonUtilsTest {

    @Test
    public void test() {
        String json = JacksonUtils.toJSONString(new Bean(LocalDateTime.now()));
        System.out.println(json);

        Bean bean = JacksonUtils.parseObject(json, Bean.class);
        System.out.println(bean);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bean {
        private LocalDateTime now;
    }

}
