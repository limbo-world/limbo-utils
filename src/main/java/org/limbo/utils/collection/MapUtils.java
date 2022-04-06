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

import lombok.experimental.UtilityClass;
import org.limbo.utils.tuple.Entry;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Brozen
 * @since 1.0.4
 */
@UtilityClass
public class MapUtils {


    /**
     * 将{@link Map}的value映射为另一个类型
     * @param map 需要映射的map
     * @param valueTransformer value映射函数
     * @param <K> map的键类型
     * @param <V> map的值类型
     * @param <NV> 映射后map的值类型
     * @return 映射后的map
     */
    public <K, V, NV> Map<K, NV> mapValue(Map<K, V> map, Function<V, NV> valueTransformer) {
        return map.entrySet().stream()
                .map(entry -> new Entry<>(entry.getKey(), valueTransformer.apply(entry.getValue())))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }



}
