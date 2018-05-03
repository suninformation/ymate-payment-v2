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
 * 企业付款
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/27 上午12:51
 * @version 1.0
 */
public class WxPayMchPay extends WxPayBaseRequest<WxPayMchPay.Response> {

    /**
     * 公众账号appid
     */
    private String mchAppId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 商户订单号
     */
    private String partnerTradeNo;

    /**
     * 用户openid
     */
    private String openId;

    /**
     * 校验用户姓名选项
     */
    private boolean checkName;

    /**
     * 收款用户姓名
     */
    private String reUserName;

    /**
     * 金额
     */
    private Integer amount;

    /**
     * 企业付款描述信息
     */
    private String desc;

    /**
     * Ip地址
     */
    private String spbillCreateIp;

    public WxPayMchPay(WxPayAccountMeta accountMeta, String partnerTradeNo, String openId, boolean checkName, Integer amount, String desc, String spbillCreateIp) {
        super(accountMeta);
        this.mchAppId = accountMeta.getAppId();
        this.partnerTradeNo = partnerTradeNo;
        this.openId = openId;
        this.checkName = checkName;
        this.amount = amount;
        this.desc = desc;
        this.spbillCreateIp = spbillCreateIp;
    }

    public String mchAppId() {
        return mchAppId;
    }

    public WxPayMchPay mchAppId(String mchAppId) {
        this.mchAppId = mchAppId;
        return this;
    }

    public String deviceInfo() {
        return deviceInfo;
    }

    public WxPayMchPay deviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String partnerTradeNo() {
        return partnerTradeNo;
    }

    public WxPayMchPay partnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
        return this;
    }

    public String openId() {
        return openId;
    }

    public WxPayMchPay openId(String openId) {
        this.openId = openId;
        return this;
    }

    public boolean checkName() {
        return checkName;
    }

    public WxPayMchPay checkName(boolean checkName) {
        this.checkName = checkName;
        return this;
    }

    public String reUserName() {
        return reUserName;
    }

    public WxPayMchPay reUserName(String reUserName) {
        this.reUserName = reUserName;
        return this;
    }

    public Integer amount() {
        return amount;
    }

    public WxPayMchPay amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public String desc() {
        return desc;
    }

    public WxPayMchPay desc(String desc) {
        this.desc = desc;
        return this;
    }

    public String spbillCreateIp() {
        return spbillCreateIp;
    }

    public WxPayMchPay spbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.mchAppId)) {
            throw new NullArgumentException("mch_appid");
        }
        if (StringUtils.isBlank(this.partnerTradeNo)) {
            throw new NullArgumentException("partner_trade_no");
        }
        if (StringUtils.isBlank(this.openId)) {
            throw new NullArgumentException("openid");
        }
        if (checkName && StringUtils.isBlank(reUserName)) {
            throw new NullArgumentException("re_user_name");
        }
        if (this.amount == null || this.amount <= 0) {
            throw new NullArgumentException("amount");
        }
        if (StringUtils.isBlank(this.desc)) {
            throw new NullArgumentException("desc");
        }
        if (StringUtils.isBlank(this.spbillCreateIp)) {
            throw new NullArgumentException("spbill_create_ip");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put("mch_appid", mchAppId);
        _params.put("device_info", deviceInfo);
        _params.put("partner_trade_no", partnerTradeNo);
        _params.put("openid", openId);
        _params.put("check_name", checkName ? "FORCE_CHECK" : "NO_CHECK");
        if (checkName) {
            _params.put("re_user_name", reUserName);
        }
        _params.put("amount", amount);
        _params.put("desc", desc);
        _params.put("spbill_create_ip", spbillCreateIp);
        return _params;
    }

    @Override
    protected String __doGetRequestURL() {
        return "mmpaymkttransfers/promotion/transfers";
    }

    @Override
    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 企业付款响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号appid
         */
        private String mchAppId;

        /**
         * 设备号
         */
        private String deviceInfo;

        /**
         * 商户订单号
         */
        private String partnerTradeNo;

        /**
         * 微信订单号
         */
        private String paymentNo;

        /**
         * 微信支付成功时间
         */
        private String paymentTime;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.mchAppId = BlurObject.bind(this.getResponseParams().get("mch_appid")).toStringValue();
            this.deviceInfo = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.DEVICE_INFO)).toStringValue();
            this.partnerTradeNo = BlurObject.bind(this.getResponseParams().get("partner_trade_no")).toStringValue();
            this.paymentNo = BlurObject.bind(this.getResponseParams().get("payment_no")).toStringValue();
            this.paymentTime = BlurObject.bind(this.getResponseParams().get("payment_time")).toStringValue();
        }

        public String mchAppId() {
            return mchAppId;
        }

        public String deviceInfo() {
            return deviceInfo;
        }

        public String partnerTradeNo() {
            return partnerTradeNo;
        }

        public String paymentNo() {
            return paymentNo;
        }

        public String paymentTime() {
            return paymentTime;
        }
    }
}
