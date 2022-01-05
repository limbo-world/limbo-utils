package org.limbo.utils.jackson.time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.limbo.utils.time.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Brozen
 * @since 2022-01-05
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    /**
     * 序列化时，写出到JSON字符串中的日期的格式
     */
    private final String pattern;

    public LocalDateTimeSerializer(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void serialize(LocalDateTime localDateTime,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateTimeUtils.format(localDateTime, pattern));
    }


    @Override
    public Class<LocalDateTime> handledType() {
        return LocalDateTime.class;
    }
}
