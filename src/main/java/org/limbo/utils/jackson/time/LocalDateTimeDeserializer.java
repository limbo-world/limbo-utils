package org.limbo.utils.jackson.time;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.limbo.utils.time.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Brozen
 * @since 2022-01-05
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    /**
     * 反序列化时，从JSON字符串中读取到的日期格式
     */
    private final String pattern;

    public LocalDateTimeDeserializer(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public LocalDateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        return DateTimeUtils.parse(jsonParser.getValueAsString(), pattern);
    }

}
