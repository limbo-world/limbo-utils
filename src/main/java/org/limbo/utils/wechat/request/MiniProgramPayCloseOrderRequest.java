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
import org.limbo.utils.wechat.Signable;
import org.limbo.utils.wechat.annotations.WeChatApi;
import org.limbo.utils.wechat.annotations.WeChatApiField;
import org.limbo.utils.wechat.response.MiniProgramPayCloseOrderResponse;

/**
 * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
 *
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=9_3
 *
 * @author Brozen
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@WeChatApi(url = "https://api.mch.weixin.qq.com/pay/closeorder", method = ApiRequestMethod.POST, bodyFormat = ApiDataFormat.XML)
public class MiniProgramPayCloseOrderRequest extends WeChatRequest<MiniProgramPayCloseOrderResponse> implements Signable {

    /**
     * 微信支付分配的商户号
     */
    @WeChatApiField("mch_id")
    private String mchId;

    /**
     * 微信分配的子商户公众账号ID
     */
    @WeChatApiField("sub_appid")
    private String subAppId;

    /**
     * 微信支付分配的子商户号
     */
    @WeChatApiField("sub_mch_id")
    private String subMchId;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    @WeChatApiField("out_trade_no")
    private String outTradeNo;

    /**
     * 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
     */
    @WeChatApiField("nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    @WeChatApiField("sign")
    private String sign;

    @Override
    public Class<MiniProgramPayCloseOrderResponse> getResponseClass() {
        return MiniProgramPayCloseOrderResponse.class;
    }

}
