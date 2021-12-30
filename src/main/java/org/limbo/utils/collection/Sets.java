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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Map工具，JDK9 Sets polyfill
 *
 * @author brozen
 * @since 1.0
 */
public class Sets {

    public static <T> Set<T> of (T e) {
        return Collections.singleton(e);
    }

    public static <T> Set<T> of (T e1, T e2) {
        Set<T> set = new HashSet<>();
        set.add(e1);
        set.add(e2);
        return Collections.unmodifiableSet(set);
    }

    public static <T> Set<T> of (T e1, T e2, T e3) {
        Set<T> set = new HashSet<>();
        set.add(e1);
        set.add(e2);
        set.add(e3);
        return Collections.unmodifiableSet(set);
    }

    public static <T> Set<T> of (T e1, T e2, T e3, T e4) {
        Set<T> set = new HashSet<>();
        set.add(e1);
        set.add(e2);
        set.add(e3);
        set.add(e4);
        return Collections.unmodifiableSet(set);
    }

    public static <T> Set<T> of (T e1, T e2, T e3, T e4, T e5) {
        Set<T> set = new HashSet<>();
        set.add(e1);
        set.add(e2);
        set.add(e3);
        set.add(e4);
        set.add(e5);
        return Collections.unmodifiableSet(set);
    }

    public static <T> Set<T> of (T e1, T e2, T e3, T e4, T e5, T e6) {
        Set<T> set = new HashSet<>();
        set.add(e1);
        set.add(e2);
        set.add(e3);
        set.add(e4);
        set.add(e5);
        set.add(e6);
        return Collections.unmodifiableSet(set);
    }


}
