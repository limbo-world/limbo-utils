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
import org.limbo.utils.wechat.response.MiniProgramPayOrderQueryResponse;

/**
 * 该接口提供所有微信支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
 *
 * 需要调用查询接口的情况：
 *
 * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
 * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
 * ◆ 调用被扫支付API，返回USERPAYING的状态；
 * ◆ 调用关单或撤销接口API之前，需确认支付状态；
 *
 *
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=9_2
 *
 * @author Brozen
 * @date 2019/12/10 2:05 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@WeChatApi(url = "https://api.mch.weixin.qq.com/pay/orderquery", method = ApiRequestMethod.POST, bodyFormat = ApiDataFormat.XML)
public class MiniProgramPayOrderQueryRequest extends WeChatRequest<MiniProgramPayOrderQueryResponse> implements Signable {

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
     * transactionId 与 outTradeNo 二选一
     * 微信的订单号，优先使用
     */
    @WeChatApiField("transaction_id")
    private String transactionId;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    @WeChatApiField("out_trade_no")
    private String outTradeNo;

    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_3
     */
    @WeChatApiField("nonce_str")
    private String nonceStr;

    /**
     * 通过签名算法计算得出的签名值，详见签名生成算法
     */
    @WeChatApiField
    private String sign;

    @Override
    public Class<MiniProgramPayOrderQueryResponse> getResponseClass() {
        return MiniProgramPayOrderQueryResponse.class;
    }
}
