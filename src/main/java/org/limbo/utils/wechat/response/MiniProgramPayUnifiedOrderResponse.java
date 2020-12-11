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
 * @author Brozen
 * @date 2019/10/22 2:21 PM
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MiniProgramPayUnifiedOrderResponse extends WeChatResponse {

    @WeChatApiField
    private String appid;

    /**
     * 调用接口提交的商户号
     */
    @WeChatApiField("mch_id")
    private String mchId;

    /**
     * 自定义参数，可以为请求支付的终端设备号等
     */
    @WeChatApiField("device_info")
    private String deviceInfo;

    /**
     * 微信返回的随机字符串
     */
    @WeChatApiField("nonce_str")
    private String nonceStr;

    /**
     * 微信返回的签名值，详见签名算法
     */
    @WeChatApiField
    private String sign;

    /**
     * SUCCESS/FAIL
     */
    @WeChatApiField("result_code")
    private String resultCode;

    /**
     * 详细参见下文错误列表
     */
    @WeChatApiField("err_code")
    private String errCode;

    /**
     * 错误信息描述
     */
    @WeChatApiField("err_code_des")
    private String errCodeDes;

    // 以下字段在return_code 和result_code都为SUCCESS的时候有返回

    /**
     * 交易类型，取值为：JSAPI，NATIVE，APP等，说明详见参数规定
     */
    @WeChatApiField("trade_type")
    private String tradeType;

    /**
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    @WeChatApiField("prepay_id")
    private String prepayId;

    /**
     * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。
     * 注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
     */
    @WeChatApiField("code_url")
    private String codeUrl;


    public enum ErrorCode {

        /**
         * 参数错误	参数格式有误或者未按规则上传	订单重入时，要求参数值与原请求一致，请确认参数问题
         */
        INVALID_REQUEST,

        /**
         * 商户无此接口权限	商户未开通此接口权限	请商户前往申请此接口权限
         */
        NOAUTH,

        /**
         * 余额不足	用户帐号余额不足	用户帐号余额不足，请用户充值或更换支付卡后再支付
         */
        NOTENOUGH,

        /**
         * 商户订单已支付	商户订单已支付，无需重复操作	商户订单已支付，无需更多操作
         */
        ORDERPAID,

        /**
         * 订单已关闭	当前订单已关闭，无法支付	当前订单已关闭，请重新下单
         */
        ORDERCLOSED,

        /**
         * 系统错误	系统超时	系统异常，请用相同参数重新调用
         */
        SYSTEMERROR,

        /**
         * APPID不存在	参数中缺少APPID	请检查APPID是否正确
         */
        APPID_NOT_EXIST,

        /**
         * MCHID不存在	参数中缺少MCHID	请检查MCHID是否正确
         */
        MCHID_NOT_EXIST,

        /**
         * appid和mch_id不匹配	appid和mch_id不匹配	请确认appid和mch_id是否匹配
         */
        APPID_MCHID_NOT_MATCH,

        /**
         * 缺少参数	缺少必要的请求参数	请检查参数是否齐全
         */
        LACK_PARAMS,

        /**
         * 商户订单号重复	同一笔交易不能多次提交	请核实商户订单号是否重复提交
         */
        OUT_TRADE_NO_USED,

        /**
         * 签名错误	参数签名结果不正确	请检查签名参数和方法是否都符合签名算法要求
         */
        SIGNERROR,

        /**
         * XML格式错误	XML格式错误	请检查XML参数格式是否正确
         */
        XML_FORMAT_ERROR,

        /**
         * 请使用post方法	未使用post传递参数 	请检查请求参数是否通过post方法提交
         */
        REQUIRE_POST_METHOD,

        /**
         * post数据为空	post数据不能为空	请检查post数据是否为空
         */
        POST_DATA_EMPTY,

        /**
         * 编码格式错误	未使用指定编码格式	请使用UTF-8编码格式
         */
        NOT_UTF8,



    }


}
