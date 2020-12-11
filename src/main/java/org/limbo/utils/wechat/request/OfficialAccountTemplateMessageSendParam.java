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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Brozen
 * @date 2019/8/15 2:05 PM
 */
@Data
public class OfficialAccountTemplateMessageSendParam {

    /**
     * openId
     */
    @NotNull(message = "需要指定OpenId")
    private String touser;

    /**
     * 消息模板ID
     */
    @NotBlank(message = "需要指定模板ID")
    private String template_id;

    /**
     * 点击后进入的页面
     * 置空则无法点击(android)，或打开空白页面(ios)
     */
    private String url;

    private String topcolor = "#FF0000";

    /**
     * 根据模板不同，数据不同
     */
    @NotNull(message = "需要指定模板数据")
    private Map<String, Data> data;

    public static Data data(String data) {
        return new Data(data);
    }

    /**
     * 不同类型的字段长度限制不一样
     *
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
     */
    @lombok.Data
    public static class Data {
        private String value;
        private String color;

        public Data(String value) {
            this(value, "#000000");
        }

        public Data(String value, String color) {
            this.value = value;
            this.color = color;
        }
    }

}
