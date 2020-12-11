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

import org.limbo.utils.wechat.ApiDataFormat;
import org.limbo.utils.wechat.ApiRequestMethod;

import java.lang.annotation.*;

/**
 * 微信API
 *
 * @author Brozen
 * @date 2019/10/22 11:24 AM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WeChatApi {

    // 请求地址
    String value() default "";

    // 请求地址，value与url同时设置则以value为准
    String url() default "";

    // 请求方式
    ApiRequestMethod method() default ApiRequestMethod.GET;

    // 请求参数是否有序，如果有序则WeChatApiField必须指定sort，否则参数顺序不可预估
    boolean sorted() default false;

    // POST参数组织方式，只在method=post时有效
    ApiDataFormat bodyFormat() default ApiDataFormat.XML;

    // 返回数据的组织方式
    ApiDataFormat responseFormat() default ApiDataFormat.XML;

}
