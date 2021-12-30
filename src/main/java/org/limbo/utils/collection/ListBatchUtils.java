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

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * List批量处理工具
 *
 * @author brozen
 * @since 1.0
 */
@Slf4j
public class ListBatchUtils {

    private static final int BATCH_SIZE = 300;

    /**
     * 批量处理
     * @see #batchIgnoreException(Integer, List, Function)
     * @see #batch(Integer, List, Function)
     */
    public static <E> int batch(List<E> list, Function<List<E>, Integer> handler) {
        return batchIgnoreException(BATCH_SIZE, list, handler);
    }

    /**
     * 分批处理列表中的元素，忽略异常
     * @see #batch(Integer, List, Function)
     */
    public static <E> int batchIgnoreException(Integer batchSize, List<E> list, Function<List<E>, Integer> handler) {
        try {
            return batch(batchSize, list, handler);
        } catch (Exception e) {
            log.warn("error while batch process list", e);
        }
        return 0;
    }

    /**
     * 分批处理列表中的元素
     * @param batchSize 每批量处理的数据条数
     * @param list 原始列表
     * @param handler 处理函数，如返回{@link Integer#MIN_VALUE}则停止处理后续的数据，直接返回
     * @param <E> 元素类型
     * @return 返回已经处理的数据条数
     */
    public static <E> int batch(Integer batchSize, List<E> list, Function<List<E>, Integer> handler) {
        int back = 0;
        if (CollectionUtils.isNotEmpty(list)) {

            if (list.size() <= batchSize) {
                return handler.apply(list);
            }

            List<List<E>> partitions = Lists.partition(list, batchSize);
            for (List<E> part : partitions) {
                if (CollectionUtils.isNotEmpty(part)) {
                    int partBack = handler.apply(part);
                    back += partBack;
                    if (partBack == Integer.MIN_VALUE) {
                        break;
                    }
                }
            }
        }
        return back;
    }


    /**
     * 分批处理并转换列表中的元素
     * @param batchSize 每批处理的数据条数
     * @param list 原始列表
     * @param handler 处理函数
     * @param <E> 原始列表元素类型
     * @param <T> 返回列表元素类型
     * @return 转换后的列表
     */
    public static <E, T> List<T> batchMap(Integer batchSize, List<E> list, Function<List<E>, List<T>> handler) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }

        if (list.size() <= batchSize) {
            return handler.apply(list);
        }

        List<T> result = new ArrayList<>();
        List<T> partResult;
        List<List<E>> partitions = Lists.partition(list, batchSize);
        for (List<E> part : partitions) {
            partResult = handler.apply(part);
            if (CollectionUtils.isNotEmpty(partResult)) {
                result.addAll(partResult);
            }
        }
        return result;
    }
}
