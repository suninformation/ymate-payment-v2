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
import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 查询订单
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/26 上午1:05
 * @version 1.0
 */
public class WxPayOrderQuery extends WxPayBaseRequest<WxPayOrderQuery.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    public WxPayOrderQuery(WxPayAccountMeta accountMeta, String transactionId, String outTradeNo) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.transactionId = transactionId;
        this.outTradeNo = outTradeNo;
    }

    public String appId() {
        return appId;
    }

    public WxPayOrderQuery appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String transactionId() {
        return transactionId;
    }

    public WxPayOrderQuery transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayOrderQuery outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.transactionId) && StringUtils.isBlank(this.outTradeNo)) {
            throw new NullArgumentException("transaction_id or out_trade_no");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put("transaction_id", transactionId);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        return _params;
    }

    @Override
    protected String __doGetRequestURL() {
        return "pay/orderquery";
    }

    @Override
    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 查询订单响应
     */
    public static class Response extends WxPayNotifyResponse {

        /**
         * 交易状态
         */
        private String tradeState;

        /**
         * 交易状态描述
         */
        private String tradeStateDesc;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.tradeState = BlurObject.bind(this.getResponseParams().get("trade_state")).toStringValue();
            this.tradeStateDesc = BlurObject.bind(this.getResponseParams().get("trade_state_desc")).toStringValue();
        }

        public String tradeState() {
            return tradeState;
        }

        public String tradeStateDesc() {
            return tradeStateDesc;
        }
    }
}
