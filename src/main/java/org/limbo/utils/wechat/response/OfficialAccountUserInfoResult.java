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

import lombok.Data;

/**
 * @author Brozen
 * @since 1.0
 */
@Data
public class OfficialAccountUserInfoResult {

    /**
     * 是否还在订阅公众号
     */
    private Integer subscribe;

    private String openid;

    private String nickname;

    private Integer sex;

    private String language;

    private String city;

    private String province;

    private String headimgurl;

    private Long subscribe_time;

    private String unionid;

    private String remark;

    private Integer groupid;

    private Integer[] tagid_list;

    private String subscribe_scene;

    private Integer qr_scene;

    private String qr_scene_str;
}
