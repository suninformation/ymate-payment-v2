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

import com.alibaba.fastjson.annotation.JSONField;
import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * 支付宝页面回跳参数对象
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/9 上午11:07
 * @version 1.0
 */
public class AliPayBaseReturn {

    // ----- 页面回跳公共参数 -----

    /**
     * 支付宝分配给开发者的应用ID
     */
    @RequestParam("app_id")
    @JSONField(name = "app_id")
    private String appId;

    /**
     * 接口名称
     */
    private String method;

    /**
     * 签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    @RequestParam("sign_type")
    @JSONField(name = "sign_type")
    private String signType;

    /**
     * 支付宝对本次支付结果的签名，开发者必须使用支付宝公钥验证签名
     */
    private String sign;

    /**
     * 编码格式
     */
    private String charset;

    /**
     * 前台回跳的时间，格式"yyyy-MM-dd HH:mm:ss"
     */
    private String timestamp;

    /**
     * 调用的接口版本，固定为：1.0
     */
    private String version;

    /**
     * 授权方的appid
     * 注：由于本接口暂不开放第三方应用授权，因此auth_app_id=app_id
     */
    @RequestParam("auth_app_id")
    @JSONField(name = "auth_app_id")
    private String authAppId;

    // ----- 业务参数 -----

    /**
     * 商户网站唯一订单号
     */
    @RequestParam("out_trade_no")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 该交易在支付宝系统中的交易流水号。最长64位
     */
    @RequestParam("trade_no")
    @JSONField(name = "trade_no")
    private String tradeNo;

    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位
     */
    @RequestParam("total_amount")
    @JSONField(name = "total_amount")
    private String totalAmount;

    /**
     * 收款支付宝账号对应的支付宝唯一用户号。 以2088开头的纯16位数字
     */
    @RequestParam("seller_id")
    @JSONField(name = "seller_id")
    private String sellerId;


    // ----------


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthAppId() {
        return authAppId;
    }

    public void setAuthAppId(String authAppId) {
        this.authAppId = authAppId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
