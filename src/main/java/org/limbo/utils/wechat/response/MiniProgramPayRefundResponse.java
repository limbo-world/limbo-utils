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
import lombok.ToString;

/**
 *
 *
 * @author Brozen
 * @since 1.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MiniProgramPayRefundResponse extends WeChatResponse {

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

    @WeChatApiField
    private String appid;

    /**
     * 加密串 用于解密 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_16&index=10
     */
    @WeChatApiField("req_info")
    private String reqInfo;

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
     * 微信退款单号
     */
    @WeChatApiField("refund_id")
    private String refundId;

    /**
     * 退款总金额,单位为分,可以做部分退款
     */
    @WeChatApiField("refund_fee")
    private Integer refundFee;

    /**
     * 退款金额
     * 去掉非充值代金券退款金额后的退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
     */
    @WeChatApiField("settlement_refund_fee")
    private Integer settlementRefundFee;

    /**
     * 订单总金额，单位为分，只能为整数，详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("total_fee")
    private Integer totalFee;

    /**
     * 应结订单金额=订单金额-免充值代金券金额，应结订单金额<=订单金额。
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("settlement_total_fee")
    private Integer settlementTotalFee;

    /**
     * 订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("fee_type")
    private String feeType;

    /**
     * 现金支付金额，单位为分，只能为整数，详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("cash_fee")
    private Integer cashFee;

    /**
     * 现金退款金额，单位为分，只能为整数，详见支付金额
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=4_2
     */
    @WeChatApiField("cash_refund_fee")
    private Integer cashRefundFee;

    /**
     * 代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
     * https://pay.weixin.qq.com/wiki/doc/api/jsapi_sl.php?chapter=12_1
     */
    @WeChatApiField("coupon_refund_fee")
    private Integer couponRefundFee;

    /**
     * 退款代金券使用数量
     */
    @WeChatApiField("coupon_refund_count")
    private Integer couponRefundCount;

    /**
     * coupon_type_$n
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
     * coupon_refund_id_$n
     *
     * 退款代金券ID, $n为下标，从0开始编号
     */
    @WeChatApiField("coupon_refund_id_0")
    private String couponRefundId0;
    @WeChatApiField("coupon_refund_id_1")
    private String couponRefundId1;
    @WeChatApiField("coupon_refund_id_2")
    private String couponRefundId2;
    @WeChatApiField("coupon_refund_id_3")
    private String couponRefundId3;
    @WeChatApiField("coupon_refund_id_4")
    private String couponRefundId4;
    @WeChatApiField("coupon_refund_id_5")
    private String couponRefundId5;

    /**
     * coupon_refund_fee_$n
     *
     * 单个退款代金券支付金额, $n为下标，从0开始编号
     */
    @WeChatApiField("coupon_refund_fee_0")
    private String couponRefundFee0;
    @WeChatApiField("coupon_refund_fee_1")
    private String couponRefundFee1;
    @WeChatApiField("coupon_refund_fee_2")
    private String couponRefundFee2;
    @WeChatApiField("coupon_refund_fee_3")
    private String couponRefundFee3;
    @WeChatApiField("coupon_refund_fee_4")
    private String couponRefundFee4;
    @WeChatApiField("coupon_refund_fee_5")
    private String couponRefundFee5;

    private String xml;

    public enum ErrorCode {

        /**
         * 接口返回错误
         * 系统超时
         * 请不要更换商户退款单号，请使用相同参数再次调用API。
         */
        SYSTEMERROR ,

        /**
         * 退款业务流程错误，需要商户触发重试来解决
         * 并发情况下，业务被拒绝，商户重试即可解决
         * 请不要更换商户退款单号，请使用相同参数再次调用API。
         */
        BIZERR_NEED_RETRY ,

        /**
         * 订单已经超过退款期限
         * 订单已经超过可退款的最大期限(支付后一年内可退款)
         * 请选择其他方式自行退款
         */
        TRADE_OVERDUE,

        /**
         * 业务错误
         * 申请退款业务发生错误
         * 该错误都会返回具体的错误原因，请根据实际返回做相应处理。
         */
        ERROR,

        /**
         * 退款请求失败
         * 用户帐号注销
         * 此状态代表退款申请失败，商户可自行处理退款。
         */
        USER_ACCOUNT_ABNORMAL,

        /**
         * 无效请求过多
         * 连续错误请求数过多被系统短暂屏蔽
         * 请检查业务是否正常，确认业务正常后请在1分钟后再来重试
         */
        INVALID_REQ_TOO_MUCH,

        /**
         * 余额不足
         * 商户可用退款余额不足
         * 此状态代表退款申请失败，商户可根据具体的错误提示做相应的处理。
         */
        NOTENOUGH,

        /**
         * 无效transaction_id
         * 请求参数未按指引进行填写
         * 请求参数错误，检查原交易号是否存在或发起支付交易接口返回失败
         */
        INVALID_TRANSACTIONID ,

        /**
         * 参数错误
         * 请求参数未按指引进行填写
         * 请求参数错误，请重新检查再调用退款申请
         */
        PARAM_ERROR ,

        /**
         * APPID不存在
         * 参数中缺少APPID
         * 请检查APPID是否正确
         */
        APPID_NOT_EXIST ,

        /**
         * MCHID不存在
         * 参数中缺少MCHID
         * 请检查MCHID是否正确
         */
        MCHID_NOT_EXIST ,

        /**
         * 订单号不存在
         * 缺少有效的订单号
         * 请检查你的订单号是否正确且是否已支付，未支付的订单不能发起退款
         */
        ORDERNOTEXIST,

        /**
         * 请使用post方法
         * 未使用post传递参数
         * 请检查请求参数是否通过post方法提交
         */
        REQUIRE_POST_METHOD ,

        /**
         * 签名错误
         * 参数签名结果不正确
         * 请检查签名参数和方法是否都符合签名算法要求
         */
        SIGNERROR ,

        /**
         * XML格式错误
         * XML格式错误
         * 请检查XML参数格式是否正确
         */
        XML_FORMAT_ERROR ,

        /**
         * 频率限制
         * 2个月之前的订单申请退款有频率限制
         * 该笔退款未受理，请降低频率后重试
         */
        FREQUENCY_LIMITED ,

        /**
         * 异常IP请求不予受理
         * 请求ip异常
         * 如果是动态ip，请登录商户平台后台关闭ip安全配置；<br>
         *  如果是静态ip，请确认商户平台配置的请求ip在不在配的ip列表里
         */
        NOAUTH,

        ;
    }

}
