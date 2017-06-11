/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.payment.alipay;

import net.ymate.payment.alipay.base.AliPayBaseNotify;
import net.ymate.payment.alipay.base.AliPayBaseReturn;
import net.ymate.platform.core.YMP;
import net.ymate.platform.webmvc.view.IView;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/06 下午 17:15
 * @version 1.0
 */
public interface IAliPay {

    String MODULE_NAME = "payment.alipay";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    IAliPayModuleCfg getModuleCfg();

    /**
     * @return 返回模块是否已初始化
     */
    boolean isInited();

    /**
     * @param appId   开发者的应用ID
     * @param orderId 订单Id
     * @param attach  附加数据
     * @return PC场景下单并支付
     * @throws Exception 可能产生的任何异常
     */
    IAliPayRequest tradePagePay(String appId, String orderId, String attach) throws Exception;

    /**
     * @param appId   开发者的应用ID
     * @param orderId 订单Id
     * @param attach  附加数据
     * @return WAP场景下单并支付
     * @throws Exception 可能产生的任何异常
     */
    IAliPayRequest tradeWapPay(String appId, String orderId, String attach) throws Exception;

    /**
     * @param baseNotify 异步通知对象
     * @return 处理异步通知并返回回应字符串
     * @throws Exception 可能产生的任何异常
     */
    String onNotify(AliPayBaseNotify baseNotify) throws Exception;

    IView onReturnCallback(AliPayBaseReturn baseReturn) throws Exception;

    /**
     * 商户生成签名字符串所使用的签名算法类型
     */
    enum SignType {
        RSA, RSA2
    }

    /**
     * 交易状态
     */
    enum TradeStatus {
        WAIT_BUYER_PAY, TRADE_CLOSE, TRADE_SUCCESS, TRADE_FINISHED
    }

    /**
     * 支付渠道
     */
    enum PayChannel {

        BALANCE("余额"),

        MONEYFUND("余额宝"),

        COUPON("红包"),

        PCREDIT("花呗"),

        PCREDITPAYINSTALLMENT("花呗分期"),

        CREDITCARD("信用卡"),

        CREDITCARDEXPRESS("信用卡快捷"),

        CREDITCARDCARTOON("信用卡卡通"),

        /**
         * 信用支付类型（包含信用卡卡通、信用卡快捷、花呗、花呗分期）
         */
        CREDIT_GROUP("信用支付类型"),

        DEBITCARDEXPRESS("借记卡快捷"),

        MCARD("商户预存卡"),

        PCARD("个人预存卡"),

        /**
         * 优惠（包含实时优惠+商户优惠）
         */
        PROMOTION("优惠"),

        VOUCHER("营销券"),

        POINT("积分"),

        MDISCOUNT("商户优惠"),

        BANKPAY("网银");

        private String channelName;

        PayChannel(String channelName) {
            this.channelName = channelName;
        }

        public String channelName() {
            return this.channelName;
        }
    }

    /**
     * 常量
     */
    interface Const {

        String SIGN_TYPE = "sign_type";

        String SIGN_TYPE_RSA = "RSA";

        String SIGN_TYPE_RSA2 = "RSA2";

        String SIGN_ALGORITHMS = "SHA1WithRSA";

        String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

        String ENCRYPT_TYPE_AES = "AES";

        String APP_ID = "app_id";

        String FORMAT = "format";

        String METHOD = "method";

        String TIMESTAMP = "timestamp";

        String VERSION = "version";

        String SIGN = "sign";

        String CHARSET = "charset";

        String NOTIFY_URL = "notify_url";

        String RETURN_URL = "return_url";

        String ENCRYPT_TYPE = "encrypt_type";

        String BIZ_CONTENT_KEY = "biz_content";

        String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

        String DATE_TIMEZONE = "GMT+8";

        String CHARSET_UTF8 = "UTF-8";

        String CHARSET_GBK = "GBK";

        String FORMAT_JSON = "json";

        String PROD_CODE_PAGE = "FAST_INSTANT_TRADE_PAY";

        String PROD_CODE_WAP = "QUICK_WAP_PAY";

        String RESPONSE_SUFFIX = "_response";

        String RESPONSE_XML_ENCRYPT_NODE_NAME = "response_encrypted";
    }
}