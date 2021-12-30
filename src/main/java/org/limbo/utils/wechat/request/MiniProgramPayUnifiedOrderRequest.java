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

import org.limbo.utils.JacksonUtils;
import org.limbo.utils.collection.Maps;
import org.limbo.utils.wechat.ApiDataFormat;
import org.limbo.utils.wechat.ApiRequestMethod;
import org.limbo.utils.wechat.Signable;
import org.limbo.utils.wechat.annotations.WeChatApi;
import org.limbo.utils.wechat.annotations.WeChatApiField;
import org.limbo.utils.wechat.response.MiniProgramPayUnifiedOrderResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 统一下单接口
 * 除付款码支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易会话标识后再按Native、JSAPI、APP等不同场景生成交易串调起支付。
 *
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=9_1
 *
 * @author Brozen
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@WeChatApi(url = "https://api.mch.weixin.qq.com/pay/unifiedorder", method = ApiRequestMethod.POST, bodyFormat = ApiDataFormat.XML)
public class MiniProgramPayUnifiedOrderRequest extends WeChatRequest<MiniProgramPayUnifiedOrderResponse> implements Signable {

    /**
     * 微信支付分配的商户号
     */
    @WeChatApiField("mch_id")
    private String mchId;

    /**
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     */
    @WeChatApiField("device_info")
    private String deviceInfo;

    /**
     * 随机字符串，长度要求在32位以内。推荐随机数生成算法
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
     * 商品简单描述，该字段请按照规范传递，具体请见参数规定
     */
    @WeChatApiField
    private String body;

    /**
     * 商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见“单品优惠参数说明”
     */
    @WeChatApiField
    private String detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     */
    @WeChatApiField
    private String attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
     */
    @WeChatApiField("out_trade_no")
    private String outTradeNo;

    /**
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
     */
    @WeChatApiField("fee_type")
    private String feeType;

    /**
     * 订单总金额，单位为分，详见支付金额
     */
    @WeChatApiField("total_fee")
    private String totalFee;

    /**
     * 支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
     */
    @WeChatApiField("spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    @WeChatApiField("time_start")
    private String timeStart;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，
     * 由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。
     * 其他详见时间规则
     *
     * 建议：最短失效时间间隔大于1分钟
     */
    @WeChatApiField("time_expire")
    private String timeExpire;

    private Date expireTime;

    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
     */
    @WeChatApiField("goods_tag")
    private String goodsTag;

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    @WeChatApiField("notify_url")
    private String notifyUrl;

    /**
     * 小程序取值如下：JSAPI，详细说明见参数规定
     */
    @WeChatApiField("trade_type")
    private String tradeType;

    /**
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     */
    @WeChatApiField("product_id")
    private String productId;

    /**
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    @WeChatApiField("limit_pay")
    private String limitPay;

    /**
     * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。
     */
    @WeChatApiField
    private String openid;

    /**
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     */
    @WeChatApiField
    private String receipt;

    /**
     * 该字段常用于线下活动时的场景信息上报，支持上报实际门店信息，商户也可以按需求自己上报相关信息。
     * 该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }} ，
     * 字段详细说明请点击行前的+展开
     */
    @WeChatApiField("scene_info")
    private String sceneInfo;

    public void setSceneInfo(MiniProgramUnifiedOrderScene scene) {
        this.sceneInfo = JacksonUtils.toJSONString(Maps.of("store_info", scene));
    }

    @Override
    public Class<MiniProgramPayUnifiedOrderResponse> getResponseClass() {
        return MiniProgramPayUnifiedOrderResponse.class;
    }

    @Data
    public static class MiniProgramUnifiedOrderScene {

        /**
         * 门店编号，由商户自定义
         */
        private String id;

        /**
         * 门店名称 ，由商户自定义
         */
        private String name;

        /**
         * 门店所在地行政区划码
         */
        private String area_code;

        /**
         * 门店详细地址 ，由商户自定义
         */
        private String address;

    }
}
