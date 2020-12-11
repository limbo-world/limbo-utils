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
 * @date 2019/12/9 2:40 PM
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MiniProgramPayCloseOrderResponse extends WeChatResponse {

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
     * 对业务结果的补充说明
     */
    @WeChatApiField("result_msg")
    private String resultMsg;

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

    public enum ErrorCode {

        /**
         * 订单已支付
         * 订单已支付，不能发起关单
         * 订单已支付，不能发起关单，请当作已支付的正常交易
         */
        ORDERPAID,

        /**
         * 系统错误
         * 系统错误
         * 系统异常，请重新调用该API
         */
        SYSTEMERROR,

        /**
         * 订单已关闭
         * 订单已关闭，无法重复关闭
         * 订单已关闭，无需继续调用
         */
        ORDERCLOSED,

        /**
         * 签名错误
         * 参数签名结果不正确
         * 请检查签名参数和方法是否都符合签名算法要求
         */
        SIGNERROR,

        /**
         * 请使用post方法
         * 未使用post传递参数
         * 请检查请求参数是否通过post方法提交
         */
        REQUIRE_POST_METHOD,

        /**
         * XML格式错误
         * XML格式错误
         * 请检查XML参数格式是否正确
         */
        XML_FORMAT_ERROR,

        ;
    }

}
