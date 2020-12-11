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

package org.limbo.utils.wechat.cache;

import lombok.Data;
import org.limbo.utils.wechat.annotations.WeChatApi;
import org.limbo.utils.wechat.annotations.WeChatApiField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * @author Brozen
 * @date 2019/10/22 11:41 AM
 */
@Data
public class RequestMetaInfo {

    private Class<?> clazz;

    private WeChatApi weChatApi;

    private TreeMap<String, RequestParamInfo> paramInfo;


    @Data
    public static class RequestParamInfo {

        private Field field;

        private Method getter;

        private WeChatApiField weChatApiField;

    }

}
