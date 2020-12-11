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

import lombok.Getter;

/**
 * @author Brozen
 * @date 2019/8/5 3:25 PM
 */
public class WeChatException extends RuntimeException {

    private static final long serialVersionUID = 9062668598442979316L;

    @Getter
    private final String weChatErrorCode;

    @Getter
    private final String weChatErrorMsg;

    public WeChatException(String weChatErrorCode, String weChatErrorMsg) {
        super(weChatErrorMsg);
        this.weChatErrorCode = weChatErrorCode;
        this.weChatErrorMsg = weChatErrorMsg;
    }

    public WeChatException(String weChatErrorCode, String weChatErrorMsg, Throwable cause) {
        super(weChatErrorMsg, cause);
        this.weChatErrorCode = weChatErrorCode;
        this.weChatErrorMsg = weChatErrorMsg;
    }

    public String getWeChatErrorInfo() {
        return String.format("Code: %s  Message: %s", weChatErrorCode, weChatErrorMsg);
    }

}
