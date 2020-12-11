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

package org.limbo.utils.wechat.annotations;

import java.lang.annotation.*;

/**
 * 微信API
 *
 * @author Brozen
 * @date 2019/10/22 11:24 AM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WeChatApiField {

    // 重写参数名称
    String value() default "";

    // 重写参数名称，value与name同时存在时，使用value
    String name() default "";

    // 参数在请求参数中的顺序
    int sort() default 0;

    // 是否是CDATA字段，
    boolean cdata() default false;
}
