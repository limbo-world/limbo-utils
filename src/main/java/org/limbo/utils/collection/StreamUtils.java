/*
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.limbo.utils.collection;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Brozen
 * @since 2021-08-30
 */
public class StreamUtils {

    /**
     * Stream API 中关于map操作的缩写，最终转换为Set返回
     * @param origin 原始集合
     * @param transformer 转换函数
     * @param <E> 原始集合元素类型
     * @param <R> 转换后的集合元素类型
     * @return List类型的集合，元素是R类型
     */
    public static <E, R> Set<R> mapSet(Collection<E> origin,
                                       Function<E, R> transformer) {
        return Objects.requireNonNull(origin)
                .stream()
                .map(Objects.requireNonNull(transformer))
                .collect(Collectors.toSet());
    }


    /**
     * Stream API 中关于map操作的缩写，最终转换为List返回
     * @param origin 原始集合
     * @param transformer 转换函数
     * @param <E> 原始集合元素类型
     * @param <R> 转换后的集合元素类型
     * @return List类型的集合，元素是R类型
     */
    public static <E, R> List<R> mapList(Collection<E> origin,
                                         Function<E, R> transformer) {
        return Objects.requireNonNull(origin)
                .stream()
                .map(Objects.requireNonNull(transformer))
                .collect(Collectors.toList());
    }


    /**
     * Stream API 中关于map操作的缩写
     * @param origin 原始集合
     * @param transformer 转换函数
     * @param collector 收集器
     * @param <E> 原始集合元素类型
     * @param <R> 转换后的集合元素类型
     * @param <C> 转换后的集合类型
     * @return C类型的集合，元素是R类型
     */
    public static <E, R, C extends Collection<E>> C map(Collection<E> origin,
                                                        Function<E, R> transformer,
                                                        Collector<? super R, R, C> collector) {
        return Objects.requireNonNull(origin)
                .stream()
                .map(Objects.requireNonNull(transformer))
                .collect(Objects.requireNonNull(collector));
    }


}
