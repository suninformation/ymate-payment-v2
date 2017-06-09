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

import net.ymate.platform.core.YMP;

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
     * 商户生成签名字符串所使用的签名算法类型
     */
    enum SignType {
        RSA, RSA2
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

        String ACCESS_TOKEN = "auth_token";

        String APP_AUTH_TOKEN = "app_auth_token";

        String TERMINAL_TYPE = "terminal_type";

        String TERMINAL_INFO = "terminal_info";

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

        String PROD_CODE = "prod_code";

        String PROD_CODE_PAGE = "FAST_INSTANT_TRADE_PAY";

        String PROD_CODE_WAP = "QUICK_WAP_PAY";

        String ERROR_RESPONSE = "error_response";

        String RESPONSE_SUFFIX = "_response";

        String RESPONSE_XML_ENCRYPT_NODE_NAME = "response_encrypted";
    }
}