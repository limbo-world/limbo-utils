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

package org.limbo.utils.wechat.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.limbo.utils.wechat.annotations.WeChatApiField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Brozen
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessTokenGetResponse extends WeChatResponse {

    /**
     * 获取到的凭证
     */
    @WeChatApiField("access_token")
    @JsonAlias("access_token")
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    @WeChatApiField("expires_in")
    @JsonAlias("expires_in")
    private Long expiresIn;

    /**
     * 错误码
     */
    @WeChatApiField("errcode")
    private Integer errCode;

    /**
     * 错误信息
     */
    @WeChatApiField("errmsg")
    private String errMsg;

    public interface ErrorCode {

        /**
         * 系统繁忙，此时请开发者稍候再试
         */
        int SYSTEM_ERROR = -1;

        /**
         * 请求成功
         */
        int SUCCESS = 0;

        /**
         * AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性
         */
        int SECRET_ERROR = 40001;

        /**
         * 请确保 grant_type 字段值为 client_credential
         */
        int GRANT_ERROR = 40002;

        /**
         * 不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写
         */
        int APPID_ERROR = 40002;

    }

}
