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


package org.limbo.utils.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Brozen
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -7844608116500392515L;

    private int code;

    private String msg;

    private T data;

    public Response(T data) {
        this(HttpStatus.SC_OK, null, data);
    }

    public Response(String msg) {
        this(HttpStatus.SC_BAD_REQUEST, msg, null);
    }

    public Response(int code, T data) {
        this(code, null, data);
    }

    public Response(int code, String msg) {
        this(code, msg, null);
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 是否为ok状态
     */
    public boolean isOk() {
        return this.code == HttpStatus.SC_OK;
    }

    /**
     * 调用正常
     */
    public static <T> Response<T> ok() {
        return new Response<>(null);
    }

    /**
     * 调用正常
     */
    public static <T> Response<T> ok(T data) {
        return new Response<>(data);
    }

    /**
     * 参数错误
     */
    public static <T> Response<T> badRequest(String msg) {
        return new Response<>(HttpStatus.SC_BAD_REQUEST, msg);
    }

    /**
     * 未认证，未登录
     */
    public static <T> Response<T> unauthorized(String msg) {
        return new Response<>(HttpStatus.SC_UNAUTHORIZED, msg);
    }

    /**
     * 未授权，无权限
     */
    public static <T> Response<T> forbidden(String msg) {
        return new Response<>(HttpStatus.SC_FORBIDDEN, msg);
    }

    /**
     * 服务器内部错误
     */
    public static <T> Response<T> serviceError(String msg) {
        return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }
}
