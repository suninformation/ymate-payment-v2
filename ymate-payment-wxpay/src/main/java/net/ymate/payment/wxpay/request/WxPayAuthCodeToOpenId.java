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
 * 授权码查询openid
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/21 上午12:17
 * @version 1.0
 */
public class WxPayAuthCodeToOpenId extends WxPayBaseRequest<WxPayAuthCodeToOpenId.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 授权码
     */
    private String authCode;

    public WxPayAuthCodeToOpenId(WxPayAccountMeta accountMeta, String authCode) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.authCode = authCode;
    }

    public String appId() {
        return appId;
    }

    public WxPayAuthCodeToOpenId appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String authCode() {
        return authCode;
    }

    public WxPayAuthCodeToOpenId authCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.authCode)) {
            throw new NullArgumentException("auth_code");
        }
        //
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put("auth_code", authCode);
        return _params;
    }

    @Override
    protected String __doGetRequestURL() {
        return "tools/authcodetoopenid";
    }

    @Override
    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 授权码查询openid响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号ID
         */
        private String appId;

        private String openId;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
            this.openId = BlurObject.bind(this.getResponseParams().get("openid")).toStringValue();
        }

        public String appId() {
            return appId;
        }

        public String openId() {
            return openId;
        }
    }
}
