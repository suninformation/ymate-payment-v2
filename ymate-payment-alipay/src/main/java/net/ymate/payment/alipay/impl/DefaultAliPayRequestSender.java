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
package net.ymate.payment.alipay.impl;

import net.ymate.framework.commons.HttpClientHelper;
import net.ymate.framework.commons.IHttpResponse;
import net.ymate.framework.commons.ParamUtils;
import net.ymate.payment.alipay.AliPay;
import net.ymate.payment.alipay.IAliPayReqeustSender;
import net.ymate.payment.alipay.IAliPayResponse;
import net.ymate.payment.alipay.IAliPayResponseParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/7 下午5:33
 * @version 1.0
 */
public class DefaultAliPayRequestSender<RESPONSE extends IAliPayResponse> implements IAliPayReqeustSender<RESPONSE> {

    private static final Log _LOG = LogFactory.getLog(DefaultAliPayRequestSender.class);

    private String gatewayUrl;

    private String charset;

    private Map<String, String> requestParameters;

    private IAliPayResponseParser<RESPONSE> responseParser;

    public DefaultAliPayRequestSender(Map<String, String> requestParameters, String charset, IAliPayResponseParser<RESPONSE> responseParser) {
        this.gatewayUrl = AliPay.get().getModuleCfg().getGatewayUrl();
        this.charset = charset;
        if (requestParameters != null) {
            this.requestParameters = requestParameters;
        } else {
            this.requestParameters = new HashMap<String, String>();
        }
        this.responseParser = responseParser;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public RESPONSE execute() throws Exception {
        IHttpResponse _response = HttpClientHelper.create().post(this.gatewayUrl, this.requestParameters);
        if (_response != null) {
            if (_response.getStatusCode() == 200) {
                return this.responseParser.parserResponse(_response.getContent());
            } else if (_LOG.isDebugEnabled()) {
                _LOG.debug("ResponseBody: " + _response.toString());
            }
        }
        return null;
    }

    public String executeActionForm() {
        return ParamUtils.buildActionForm(this.gatewayUrl, true, false, true, charset, requestParameters);
    }
}
