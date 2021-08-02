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

package org.limbo.utils.functional;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 三元function，入参有三个，出参一个
 *
 * @author Brozen
 * @since 2021-08-02
 */
public interface TriFunction<P1, P2, P3, R> {

    /**
     * 对给定参数执行对应方法
     * @param param1 接口第一个泛型参数
     * @param param2 接口第二个泛型参数
     * @param param3 接口第三个泛型参数
     * @return 返回接口第四个泛型参数
     */
    R apply(P1 param1, P2 param2, P3 param3);


    /**
     * 返回一个组合方法，先应用当前tri-function，然后将返回值传递给入参function，并返回入参function的返回值。
     * 此tri-function或入参function的处理流程中出现异常，均会向上抛出，由调用者进行捕获处理。
     * @see Function#andThen(Function)
     * @see BiFunction#andThen(Function)
     */
    default <V> TriFunction<P1, P2, P3, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (P1 p1, P2 p2, P3 p3) -> after.apply(apply(p1, p2, p3));
    }

}
