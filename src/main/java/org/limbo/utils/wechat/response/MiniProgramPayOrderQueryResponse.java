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
 * @date 2019/12/10 2:06 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MiniProgramPayOrderQueryResponse extends WeChatResponse {

    @WeChatApiField
    private String appid;

    /**
     * 调用接口提交的商户号
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
     * 商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
     */
    @WeChatApiField("nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    @WeChatApiField("sign")
    private String sign;

    /**
     * SUCCESS/FAIL
     */
    @WeChatApiField("result_code")
    private String resultCode;

    /**
     * 当result_code为FAIL时返回错误代码
     * 详细参见下文错误列表
     */
    @WeChatApiField("err_code")
    private String errCode;

    /**
     * 当result_code为FAIL时返回错误代码
     * 错误信息描述
     */
    @WeChatApiField("err_code_des")
    private String errCodeDes;

    // 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。


    /**
     * 微信支付分配的终端设备号
     */
    @WeChatApiField("device_info")
    private String deviceInfo;

    /**
     * 用户在商户appid下的唯一标识
     */
    @WeChatApiField("openid")
    private String openId;

    /**
     * 用户是否关注公众账号，Y-关注，N-未关注（机构商户不返回）
     */
    @WeChatApiField("is_subscribe")
    private String isSubscribe;

    /**
     * 用户在子商户appid下的唯一标识
     */
    @WeChatApiField("sub_openid")
    private String subOpenid;

    /**
     * 用户是否关注子公众账号，Y-关注，N-未关注（机构商户不返回）
     */
    @WeChatApiField("sub_is_subscribe")
    private String subIsSubscribe;

    /**
     * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("trade_type")
    private String trade_type;

    /**
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销(刷卡支付)
     * USERPAYING--用户支付中
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    @WeChatApiField("trade_state")
    private String tradeState;

    /**
     * 银行类型，采用字符串类型的银行标识
     */
    @WeChatApiField("bank_type")
    private String bankType;

    /**
     * 商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。如果使用了单品优惠，会有单品优惠信息返回
     *
     * discount_detail []：
     *  └ goods_id String 必填 32 商品的编号
     *  └ goods_name String 必填 256 商品名称
     *  └ coupon_batch_id String 必填 代金券批次ID
     *  └ coupon_id String 必填 代金卷ID
     *  └ coupon_fee Int 必填 代金券支付金额，单位为分
     *
     * 类似：
     * {
     * 	"discount_detail": [{
     * 			"goods_id": "iphone6s_16G",
     * 			"goods_name": "iPhone6s 16G",
     * 			"coupon_batch_id": 888,
     * 			"coupon_id": 666888,
     * 			"coupon_fee": 1000
     *                },
     *        {
     * 			"goods_id": "iphone6s_32G",
     * 			"goods_name": "iPhone6s 32G",
     * 			"coupon_batch_id": 999,
     * 			"coupon_id": 666999,
     * 			"coupon_fee": 1500
     *        }
     * 	]
     * }
     */
    @WeChatApiField("detail")
    private String detail;

    /**
     * 订单总金额，单位为分
     */
    @WeChatApiField("total_fee")
    private Integer totalFee;

    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("fee_type")
    private String feeType;

    /**
     * 当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额。
     */
    @WeChatApiField("settlement_total_fee")
    private Integer settlementTotalFee;

    /**
     * 现金支付金额订单现金支付金额，详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("cash_fee")
    private Integer cashFee;

    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("cash_fee_type")
    private String cashFeeType;

    /**
     * “代金券或立减优惠”金额<=订单总金额，订单总金额-“代金券或立减优惠”金额=现金支付金额，详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("coupon_fee")
    private Integer couponFee;

    /**
     * 代金券或立减优惠使用数量
     */
    @WeChatApiField("coupon_count")
    private Integer couponCount;

    /**
     * coupon_id_$n 代金券或立减优惠ID, $n为下标，从0开始编号
     * 这里先声明5个，应该够用了
     */
    @WeChatApiField("coupon_id_0")
    private String couponId0;
    @WeChatApiField("coupon_id_1")
    private String couponId1;
    @WeChatApiField("coupon_id_2")
    private String couponId2;
    @WeChatApiField("coupon_id_3")
    private String couponId3;
    @WeChatApiField("coupon_id_4")
    private String couponId4;
    @WeChatApiField("coupon_id_5")
    private String couponId5;

    /**
     * coupon_id_$n
     *
     * CASH--充值代金券
     * NO_CASH---非充值代金券
     * 订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
     */
    @WeChatApiField("coupon_type_0")
    private String couponType0;
    @WeChatApiField("coupon_type_1")
    private String couponType1;
    @WeChatApiField("coupon_type_2")
    private String couponType2;
    @WeChatApiField("coupon_type_3")
    private String couponType3;
    @WeChatApiField("coupon_type_4")
    private String couponType4;
    @WeChatApiField("coupon_type_5")
    private String couponType5;

    /**
     * coupon_fee_$n 单个代金券或立减优惠支付金额, $n为下标，从0开始编号
     */
    @WeChatApiField("coupon_fee_0")
    private Integer couponFee0;
    @WeChatApiField("coupon_fee_1")
    private Integer couponFee1;
    @WeChatApiField("coupon_fee_2")
    private Integer couponFee2;
    @WeChatApiField("coupon_fee_3")
    private Integer couponFee3;
    @WeChatApiField("coupon_fee_4")
    private Integer couponFee4;
    @WeChatApiField("coupon_fee_5")
    private Integer couponFee5;

    /**
     * 微信支付订单号
     */
    @WeChatApiField("transaction_id")
    private String transactionId;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    @WeChatApiField("out_trade_no")
    private String outTradeNo;

    /**
     * 商家数据包，原样返回
     */
    @WeChatApiField("attach")
    private String attach;

    /**
     * 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("time_end")
    private String timeEnd;

    /**
     * 对当前查询订单状态的描述和下一步操作的指引
     */
    @WeChatApiField("trade_state_desc")
    private String tradeStateDesc;


    public enum ErrorCode {

        /**
         * 此交易订单号不存在
         * 查询系统中不存在此交易订单号
         * 该API只能查提交支付交易返回成功的订单，请商户检查需要查询的订单号是否正确
         */
        ORDERNOTEXIST,

        /**
         * 系统错误
         * 后台系统返回错误
         * 系统异常，请再调用发起查询
         */
        SYSTEMERROR,


        ;
    }
}
