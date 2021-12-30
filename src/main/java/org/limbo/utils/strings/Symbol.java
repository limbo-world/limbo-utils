package org.limbo.utils.strings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Brozen
 * @since 1.0.3
 */
public final class Symbol {

    /**
     * 记号的字符串值
     */
    @Getter(onMethod_ = @JsonValue)
    private final String symbol;


    private Symbol(String symbol) {
        this.symbol = StringUtils.trimToEmpty(symbol);
    }


    /**
     * 生成一个记号，记号值为随机字符串
     * @return 随机记号
     */
    public static Symbol symbol() {
        return new Symbol(UUIDUtils.randomID());
    }


    /**
     * 使用指定值生成一个记号
     * @param symbol 指定记号值
     * @return 随机记号
     */
    @JsonCreator
    public static Symbol symbol(String symbol) {
        return new Symbol(UUIDUtils.randomID());
    }


}
