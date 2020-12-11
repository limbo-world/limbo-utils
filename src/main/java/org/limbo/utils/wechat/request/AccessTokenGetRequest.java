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

package org.limbo.utils.wechat.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.limbo.utils.wechat.ApiDataFormat;
import org.limbo.utils.wechat.ApiRequestMethod;
import org.limbo.utils.wechat.annotations.WeChatApi;
import org.limbo.utils.wechat.annotations.WeChatApiField;
import org.limbo.utils.wechat.response.AccessTokenGetResponse;

/**
 *
 *
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html
 *
 * @author Brozen
 * @date 2019/12/10 10:40 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@WeChatApi(url = "https://api.weixin.qq.com/cgi-bin/token", method = ApiRequestMethod.GET, responseFormat = ApiDataFormat.JSON)
public class AccessTokenGetRequest extends WeChatRequest<AccessTokenGetResponse> {

    @WeChatApiField("secret")
    private String appSecret;

    @WeChatApiField("grant_type")
    private final String grantType = "client_credential";

    @Override
    public Class<AccessTokenGetResponse> getResponseClass() {
        return AccessTokenGetResponse.class;
    }

}
