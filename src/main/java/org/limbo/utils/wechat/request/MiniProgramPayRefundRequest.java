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

import org.limbo.utils.wechat.ApiDataFormat;
import org.limbo.utils.wechat.ApiRequestMethod;
import org.limbo.utils.wechat.Signable;
import org.limbo.utils.wechat.annotations.WeChatApi;
import org.limbo.utils.wechat.annotations.WeChatApiField;
import org.limbo.utils.wechat.response.MiniProgramPayRefundResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，微信支付将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
 *
 * 注意：
 *
 * 1.交易时间超过一年的订单无法提交退款；
 *
 * 2、微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。申请退款总金额不能超过订单金额。 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号。
 *
 * 3、请求频率限制：150qps，即每秒钟正常的申请退款请求次数不超过150次
 *
 *     错误或无效请求频率限制：6qps，即每秒钟异常或错误的退款申请请求不超过6次
 *
 * 4、每个支付订单的部分退款次数不能超过50次
 *
 *
 *
 *
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=9_4
 *
 * @author Brozen
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@WeChatApi(url = "https://api.mch.weixin.qq.com/secapi/pay/refund", method = ApiRequestMethod.POST, bodyFormat = ApiDataFormat.XML)
public class MiniProgramPayRefundRequest extends WeChatRequest<MiniProgramPayRefundResponse> implements Signable {

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

    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     */
    @WeChatApiField("sign_type")
    private String signType;

    /**
     * 微信生成的订单号，在支付通知中有返回
     */
    @WeChatApiField("transaction_id")
    private String transactionId;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    @WeChatApiField("out_trade_no")
    private String outTradeNo;

    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    @WeChatApiField("out_refund_no")
    private String outRefundNo;

    /**
     * 订单总金额，单位为分，只能为整数，详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("total_fee")
    private Integer totalFee;

    /**
     * 退款总金额，单位为分，只能为整数，可部分退款。详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("refund_fee")
    private Integer refundFee;

    /**
     * 退款货币类型，需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("refund_fee_type")
    private String refundFeeType;

    /**
     * 若商户传入，会在下发给用户的退款消息中体现退款原因
     * 注意：若订单退款金额≤1元，且属于部分退款，则不会在退款消息中体现退款原因
     */
    @WeChatApiField("refund_desc")
    private String refundDesc;

    /**
     * 仅针对老资金流商户使用
     * REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
     * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     */
    @WeChatApiField("refund_account")
    private String refundAccount;

    /**
     * 异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
     * 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。
     */
    @WeChatApiField("notify_url")
    private String notifyUrl;

    @Override
    public Class<MiniProgramPayRefundResponse> getResponseClass() {
        return MiniProgramPayRefundResponse.class;
    }

}
