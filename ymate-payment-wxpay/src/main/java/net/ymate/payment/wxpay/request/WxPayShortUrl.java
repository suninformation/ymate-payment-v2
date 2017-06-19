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
 * 转换短链接
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/27 上午12:23
 * @version 1.0
 */
public class WxPayShortUrl extends WxPayBaseRequest<WxPayShortUrl.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * URL链接
     */
    private String longUrl;

    public WxPayShortUrl(WxPayAccountMeta accountMeta, String longUrl) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.longUrl = longUrl;
    }

    public String appId() {
        return appId;
    }

    public WxPayShortUrl appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String longUrl() {
        return longUrl;
    }

    public WxPayShortUrl longUrl(String longUrl) {
        this.longUrl = longUrl;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.longUrl)) {
            throw new NullArgumentException("long_url");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put("long_url", longUrl);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "https://api.mch.weixin.qq.com/tools/shorturl";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号ID
         */
        private String appId;

        /**
         * 转换后的URL
         */
        private String shortUrl;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
            this.shortUrl = BlurObject.bind(this.getResponseParams().get("short_url")).toStringValue();
        }

        public String appId() {
            return appId;
        }

        public String shortUrl() {
            return shortUrl;
        }
    }
}
