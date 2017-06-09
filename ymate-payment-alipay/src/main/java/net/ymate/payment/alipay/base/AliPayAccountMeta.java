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
package net.ymate.payment.alipay.base;

import net.ymate.payment.alipay.IAliPay;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 支付宝开放平台应用账户信息
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/6 下午5:38
 * @version 1.0
 */
public class AliPayAccountMeta implements Serializable {

    /**
     * 支付宝分配给开发者的应用ID
     */
    private String appId;

    /**
     * 同步返回地址，HTTP/HTTPS开头字符串
     */
    private String returnUrl;

    /**
     * 支付宝服务器主动通知商户服务器里指定的页面http/https路径
     */
    private String notifyUrl;

    /**
     * 返回格式
     */
    private String format;

    /**
     * 请求使用的编码格式
     */
    private String charset;

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private IAliPay.SignType signType;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    public AliPayAccountMeta(String appId, String signType, String privateKey, String publicKey) {
        if (StringUtils.isBlank(appId)) {
            throw new NullArgumentException("app_id");
        }
        if (StringUtils.isBlank(signType) || !StringUtils.equalsIgnoreCase(signType, IAliPay.Const.SIGN_TYPE_RSA2) && !StringUtils.equalsIgnoreCase(signType, IAliPay.Const.SIGN_TYPE_RSA)) {
            throw new IllegalArgumentException("sign_type");
        }
        if (StringUtils.isBlank(privateKey)) {
            throw new NullArgumentException("private_key");
        }
        if (StringUtils.isBlank(publicKey)) {
            throw new NullArgumentException("public_key");
        }
        this.appId = appId;
        this.signType = IAliPay.SignType.valueOf(signType.toUpperCase());
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public AliPayAccountMeta(String appId, String returnUrl, String notifyUrl, String signType, String privateKey, String publicKey) {
        this(appId, signType, privateKey, publicKey);
        //
        this.returnUrl = returnUrl;
        this.notifyUrl = notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public IAliPay.SignType getSignType() {
        return signType;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
