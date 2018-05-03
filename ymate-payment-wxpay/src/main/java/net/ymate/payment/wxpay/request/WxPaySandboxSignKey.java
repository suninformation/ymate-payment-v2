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
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayBaseResponse;
import net.ymate.platform.core.lang.BlurObject;

/**
 * 获取验签环境密钥
 *
 * @author 刘镇 (suninformation@163.com) on 2017/7/6 下午8:05
 * @version 1.0
 */
public class WxPaySandboxSignKey extends WxPayBaseRequest<WxPaySandboxSignKey.Response> {

    public WxPaySandboxSignKey(WxPayAccountMeta accountMeta) {
        super(accountMeta);
    }

    @Override
    protected String __doGetRequestURL() {
        return "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
    }

    @Override
    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 获取验签环境密钥响应
     */
    public static class Response extends WxPayBaseResponse {

        private String signkey;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.signkey = BlurObject.bind(this.getResponseParams().get("sandbox_signkey")).toStringValue();
        }

        public String signkey() {
            return signkey;
        }
    }
}
