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

import org.limbo.utils.wechat.annotations.WeChatApiField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Brozen
 * @date 2019/10/22 4:15 PM
 *
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7&index=8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MiniProgramUnifiedOrderCallbackResponse extends WeChatResponse {

    @WeChatApiField
    private String appid;

    @WeChatApiField
    private String attach;

    @WeChatApiField("bank_type")
    private String bankType;

    @WeChatApiField("cash_fee")
    private String cashFee;

    @WeChatApiField("coupon_count")
    private String couponCount;

    @WeChatApiField("coupon_fee")
    private Integer couponFee;

    @WeChatApiField("coupon_fee_0")
    private Integer couponFee0;

    @WeChatApiField("coupon_id_0")
    private String couponId0;

    @WeChatApiField("coupon_type_0")
    private String coupon_type_0;

    @WeChatApiField("fee_type")
    private String feeType;

    @WeChatApiField("is_subscribe")
    private String isSubscribe;

    /**
     * 调用接口提交的商户号
     */
    @WeChatApiField("mch_id")
    private String mchId;

    /**
     * 微信返回的随机字符串
     */
    @WeChatApiField("nonce_str")
    private String nonceStr;


    @WeChatApiField
    private String openid;

    @WeChatApiField("out_trade_no")
    private String outTradeNo;

    @WeChatApiField("result_code")
    private String resultCode;

    @WeChatApiField("err_code")
    private String errCode;

    @WeChatApiField("err_code_des")
    private String errCodeDes;

    @WeChatApiField
    private String sign;

    @WeChatApiField("time_end")
    private String timeEnd;

    @WeChatApiField("total_fee")
    private String totalFee;

    @WeChatApiField("trade_type")
    private String tradeType;

    @WeChatApiField("transaction_id")
    private String transactionId;

    private String xml;
}
