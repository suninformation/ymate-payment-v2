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
 * 关闭订单
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/26 下午10:02
 * @version 1.0
 */
public class WxPayCloseOrder extends WxPayBaseRequest<WxPayCloseOrder.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    public WxPayCloseOrder(WxPayAccountMeta accountMeta, String outTradeNo) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.outTradeNo = outTradeNo;
    }

    public String appId() {
        return appId;
    }

    public WxPayCloseOrder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayCloseOrder outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    @Override
    protected Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.outTradeNo)) {
            throw new NullArgumentException(IWxPay.Const.OUT_TRADE_NO);
        }
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "pay/closeorder";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 关闭订单响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号ID
         */
        private String appId;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
        }

        public String appId() {
            return appId;
        }
    }
}
