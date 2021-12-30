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

package org.limbo.utils.wechat;

import org.limbo.utils.JacksonUtils;
import org.limbo.utils.wechat.request.WeChatRequest;
import org.limbo.utils.wechat.response.WeChatResponse;

/**
 * @author Brozen
 * @since 1.0
 */
public class JsonDataConverter<REQ extends WeChatRequest<RES>, RES extends WeChatResponse> implements DataConverter<REQ, RES> {

    private Class<RES> responseClass;

    public JsonDataConverter(Class<RES> responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public String serialize(REQ request) {
        return JacksonUtils.toJSONString(request);
    }

    @Override
    public RES deserialize(String data) {
        return JacksonUtils.parseObject(data, responseClass);
    }

}
