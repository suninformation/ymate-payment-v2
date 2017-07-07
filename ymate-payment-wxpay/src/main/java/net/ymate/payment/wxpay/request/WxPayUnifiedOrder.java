/*
 * Copyright 2007-2016 the original author or authors.
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
package net.ymate.payment.wxpay.request;

import net.ymate.framework.commons.IHttpResponse;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayBaseResponse;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 统一下单
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/26 上午12:44
 * @version 1.0
 */
public class WxPayUnifiedOrder extends WxPayBaseRequest<WxPayUnifiedOrder.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 货币类型
     */
    private String feeType;

    /**
     * 总金额
     */
    private Integer totalFee;

    /**
     * 终端IP
     */
    private String spbillCreateIp;

    /**
     * 交易起始时间
     */
    private String timeStart;

    /**
     * 交易结束时间
     */
    private String timeExpire;

    /**
     * 商品标记
     */
    private String goodsTag;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 指定支付方式
     */
    private String limitPay;

    /**
     * 用户标识
     */
    private String openId;

    /**
     * 场景信息
     */
    private String sceneInfo;

    public WxPayUnifiedOrder(WxPayAccountMeta accountMeta, String body, String outTradeNo, Integer totalFee, String spbillCreateIp, String notifyUrl, String tradeType) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.body = body;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.notifyUrl = notifyUrl;
        this.tradeType = tradeType;
    }

    public String appId() {
        return appId;
    }

    public WxPayUnifiedOrder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String deviceInfo() {
        return deviceInfo;
    }

    public WxPayUnifiedOrder deviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String body() {
        return body;
    }

    public WxPayUnifiedOrder body(String body) {
        this.body = body;
        return this;
    }

    public String detail() {
        return detail;
    }

    public WxPayUnifiedOrder detail(String detail) {
        this.detail = detail;
        return this;
    }

    public String attach() {
        return attach;
    }

    public WxPayUnifiedOrder attach(String attach) {
        this.attach = attach;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayUnifiedOrder outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String feeType() {
        return feeType;
    }

    public WxPayUnifiedOrder feeType(String feeType) {
        this.feeType = feeType;
        return this;
    }

    public Integer totalFee() {
        return totalFee;
    }

    public WxPayUnifiedOrder totalFee(Integer totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public String spbillCreateIp() {
        return spbillCreateIp;
    }

    public WxPayUnifiedOrder spbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
        return this;
    }

    public String timeStart() {
        return timeStart;
    }

    public WxPayUnifiedOrder timeStart(String timeStart) {
        this.timeStart = timeStart;
        return this;
    }

    public String timeExpire() {
        return timeExpire;
    }

    public WxPayUnifiedOrder timeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
        return this;
    }

    public String goodsTag() {
        return goodsTag;
    }

    public WxPayUnifiedOrder goodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
        return this;
    }

    public String notifyUrl() {
        return notifyUrl;
    }

    public WxPayUnifiedOrder notifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String tradeType() {
        return tradeType;
    }

    public WxPayUnifiedOrder tradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String productId() {
        return productId;
    }

    public WxPayUnifiedOrder productId(String productId) {
        this.productId = productId;
        return this;
    }

    public String limitPay() {
        return limitPay;
    }

    public WxPayUnifiedOrder limitPay(String limitPay) {
        this.limitPay = limitPay;
        return this;
    }

    public String openId() {
        return openId;
    }

    public WxPayUnifiedOrder openId(String openId) {
        this.openId = openId;
        return this;
    }

    public String sceneInfo() {
        return sceneInfo;
    }

    public WxPayUnifiedOrder sceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.body)) {
            throw new NullArgumentException("body");
        }
        if (StringUtils.isBlank(this.outTradeNo)) {
            throw new NullArgumentException(IWxPay.Const.OUT_TRADE_NO);
        }
        if (this.totalFee == null || this.totalFee <= 0) {
            throw new NullArgumentException("total_fee");
        }
        if (StringUtils.isBlank(this.spbillCreateIp)) {
            throw new NullArgumentException("spbill_create_ip");
        }
        if (StringUtils.isBlank(this.notifyUrl)) {
            throw new NullArgumentException(IWxPay.Const.NOTIFY_URL);
        }
        if (StringUtils.isBlank(this.tradeType)) {
            throw new NullArgumentException("trade_type");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put(IWxPay.Const.DEVICE_INFO, deviceInfo);
        _params.put("body", body);
        _params.put("detail", detail);
        _params.put("attach", attach);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        _params.put("fee_type", feeType);
        _params.put("total_fee", totalFee);
        _params.put("spbill_create_ip", spbillCreateIp);
        _params.put("time_start", timeStart);
        _params.put("time_expire", timeExpire);
        _params.put("goods_tag", goodsTag);
        _params.put(IWxPay.Const.NOTIFY_URL, notifyUrl);
        _params.put("trade_type", tradeType);
        _params.put("product_id", productId);
        _params.put("limit_pay", limitPay);
        _params.put("openid", openId);
        _params.put("scene_info", sceneInfo);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "pay/unifiedorder";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 统一下单响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号ID
         */
        private String appId;

        /**
         * 设备号
         */
        private String deviceInfo;

        /**
         * 交易类型
         */
        private String tradeType;

        /**
         * 预支付交易会话标识
         */
        private String prepayId;

        /**
         * 二维码链接
         */
        private String codeUrl;

        /**
         * 支付跳转链接
         */
        private String mwebUrl;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
            this.deviceInfo = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.DEVICE_INFO)).toStringValue();
            this.tradeType = BlurObject.bind(this.getResponseParams().get("trade_type")).toStringValue();
            this.prepayId = BlurObject.bind(this.getResponseParams().get("prepay_id")).toStringValue();
            this.codeUrl = BlurObject.bind(this.getResponseParams().get("code_url")).toStringValue();
            this.mwebUrl = BlurObject.bind(this.getResponseParams().get("mweb_url")).toStringValue();
        }

        public String appId() {
            return appId;
        }

        public String deviceInfo() {
            return deviceInfo;
        }

        public String tradeType() {
            return tradeType;
        }

        public String prepayId() {
            return prepayId;
        }

        public String codeUrl() {
            return codeUrl;
        }

        public String mwebUrl() {
            return mwebUrl;
        }
    }
}
