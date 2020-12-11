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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.limbo.utils.wechat.annotations.WeChatApi;
import org.limbo.utils.wechat.annotations.WeChatApiField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brozen
 * @date 2019/10/22 11:42 AM
 */
@Slf4j
public class MetaInfoCache {

    private static final ConcurrentHashMap<Class<?>, RequestMetaInfo> RequestCache = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<?>, ResponseMetaInfo> ResponseCache = new ConcurrentHashMap<>();


    public static RequestMetaInfo parseRequest(Class<?> clazz) {
        return RequestCache.computeIfAbsent(clazz, MetaInfoCache::parseRequestClass);
    }

    private static RequestMetaInfo parseRequestClass(Class<?> clazz) {
        Objects.requireNonNull(clazz);

        log.info("开始解析请求类：{}", clazz.getName());
        try {
            RequestMetaInfo requestMetaInfo = new RequestMetaInfo();
            WeChatApi weChatApi = (WeChatApi) clazz.getAnnotation(WeChatApi.class);
            requestMetaInfo.setWeChatApi(weChatApi);
            requestMetaInfo.setClazz(clazz);

            List<RequestMetaInfo.RequestParamInfo> paramInfo = new LinkedList<>();

            while (clazz != Object.class) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    WeChatApiField weChatApiField = field.getAnnotation(WeChatApiField.class);
                    if (weChatApiField == null) {
                        continue;
                    }

                    String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method getter = clazz.getMethod(getterName);

                    RequestMetaInfo.RequestParamInfo requestParamInfo = new RequestMetaInfo.RequestParamInfo();
                    requestParamInfo.setField(field);
                    requestParamInfo.setGetter(getter);
                    requestParamInfo.setWeChatApiField(weChatApiField);
                    paramInfo.add(requestParamInfo);
                }
                clazz = clazz.getSuperclass();
            }
            paramInfo.sort(Comparator.comparingInt(p -> p.getWeChatApiField().sort()));

            TreeMap<String, RequestMetaInfo.RequestParamInfo> sortedParamInfo = new TreeMap<>();
            paramInfo.forEach(p -> {
                String fieldNameAlias = p.getWeChatApiField().value();
                if (StringUtils.isBlank(fieldNameAlias)) {
                    fieldNameAlias = p.getWeChatApiField().name();
                }
                if (StringUtils.isBlank(fieldNameAlias)) {
                    fieldNameAlias = p.getField().getName();
                }
                sortedParamInfo.put(fieldNameAlias, p);
            });
            requestMetaInfo.setParamInfo(sortedParamInfo);
            log.info("请求类解析完成！");
            return requestMetaInfo;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(String.format("解析Request类：%s 失败！", clazz.getName()), e);
        }
    }

    public static ResponseMetaInfo parseResponse(Class<?> clazz) {
        return ResponseCache.computeIfAbsent(clazz, MetaInfoCache::parseResponseClass);
    }

    private static ResponseMetaInfo parseResponseClass(Class<?> clazz) {
        Objects.requireNonNull(clazz);

        try {
            ResponseMetaInfo responseMetaInfo = new ResponseMetaInfo();
            responseMetaInfo.setClazz(clazz);

            List<ResponseMetaInfo.ResponseParamInfo> paramInfo = new LinkedList<>();
            while (clazz != Object.class) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    WeChatApiField weChatApiField = field.getAnnotation(WeChatApiField.class);
                    if (weChatApiField == null) {
                        continue;
                    }

                    String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method setter = clazz.getMethod(setterName, field.getType());

                    ResponseMetaInfo.ResponseParamInfo requestParamInfo = new ResponseMetaInfo.ResponseParamInfo();
                    requestParamInfo.setField(field);
                    requestParamInfo.setSetter(setter);
                    requestParamInfo.setWeChatApiField(weChatApiField);
                    paramInfo.add(requestParamInfo);
                }
                clazz = clazz.getSuperclass();
            }
            paramInfo.sort(Comparator.comparingInt(p -> p.getWeChatApiField().sort()));

            TreeMap<String, ResponseMetaInfo.ResponseParamInfo> sortedParamInfo = new TreeMap<>();
            paramInfo.forEach(p -> {
                String fieldNameAlias = p.getWeChatApiField().value();
                if (StringUtils.isBlank(fieldNameAlias)) {
                    fieldNameAlias = p.getWeChatApiField().name();
                }
                if (StringUtils.isBlank(fieldNameAlias)) {
                    fieldNameAlias = p.getField().getName();
                }
                sortedParamInfo.put(fieldNameAlias, p);
            });
            responseMetaInfo.setParamInfo(sortedParamInfo);
            return responseMetaInfo;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(String.format("解析Response类：%s 失败！", clazz.getName()), e);
        }
    }


}
