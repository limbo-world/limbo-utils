package org.limbo.utils;

/**
 * 可描述枚举
 *
 * @author brozen
 * @date 2020/12/10
 */
public interface DescribableEnum<T> {

    /**
     * 获取枚举值
     */
    T getValue();

    /**
     * 获取枚举描述
     */
    String getDesc();

}
