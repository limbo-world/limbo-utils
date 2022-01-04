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

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Brozen
 * @since 1.0.3
 */
@Setter
@Accessors(fluent = true)
public final class ResponseBuilder<T> {

    /**
     * 响应状态码
     */
    private int code = HttpStatus.SC_OK;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    private ResponseBuilder() {
    }

    /**
     * 构造{@link Response}。重复调用此方法，每次会生成不同的{@link Response}
     */
    public Response<T> build() {
        return new Response<>(code, msg, data);
    }


    /**
     * 调用正常。code={@link HttpStatus#SC_OK}
     */
    public static <T> ResponseBuilder<T> ok() {
        return new ResponseBuilder<T>().code(HttpStatus.SC_OK);
    }

    /**
     * 调用正常。code={@link HttpStatus#SC_OK}
     */
    public static <T> ResponseBuilder<T> ok(T data) {
        return new ResponseBuilder<T>()
                .code(HttpStatus.SC_OK)
                .data(data);
    }

    /**
     * 参数错误。code={@link HttpStatus#SC_BAD_REQUEST}
     */
    public static <T> ResponseBuilder<T> badRequest(String msg) {
        return new ResponseBuilder<T>()
                .code(HttpStatus.SC_BAD_REQUEST)
                .msg(msg);
    }

    /**
     * 未认证，未登录。code={@link HttpStatus#SC_UNAUTHORIZED}
     */
    public static <T> ResponseBuilder<T> unauthorized(String msg) {
        return new ResponseBuilder<T>()
                .code(HttpStatus.SC_UNAUTHORIZED)
                .msg(msg);
    }

    /**
     * 未授权，无权限。code={@link HttpStatus#SC_FORBIDDEN}
     */
    public static <T> ResponseBuilder<T> forbidden(String msg) {
        return new ResponseBuilder<T>()
                .code(HttpStatus.SC_FORBIDDEN)
                .msg(msg);
    }

    /**
     * 服务器内部错误。code={@link HttpStatus#SC_INTERNAL_SERVER_ERROR}
     */
    public static <T> ResponseBuilder<T> serviceError(String msg) {
        return new ResponseBuilder<T>()
                .code(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .msg(msg);
    }

}
