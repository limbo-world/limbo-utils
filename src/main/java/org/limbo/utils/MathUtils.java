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

package org.limbo.utils;

import java.math.BigDecimal;

/**
 * 数学工具类，主要是处理了空指针
 *
 * @author Brozen
 * @since 2021-08-18
 */
public class MathUtils {

    /**
     * 长整型数值相加，null被处理为0
     * @param n1 加数
     * @param n2 被加数
     * @return 和
     */
    public static Long sum(Long n1, Long n2) {
        BigDecimal d1 = new BigDecimal(n1 == null ? 0L : n1);
        BigDecimal d2 = new BigDecimal(n2 == null ? 0L : n2);
        return d1.add(d2).longValueExact();
    }


}
