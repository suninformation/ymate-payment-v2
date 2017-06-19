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
package net.ymate.payment.wxpay.base;

import net.ymate.framework.commons.HttpClientHelper;
import net.ymate.framework.commons.IHttpResponse;
import net.ymate.payment.wxpay.IWxPay;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 16/6/28 下午2:50
 * @version 1.0
 */
public abstract class WxPayBaseRequest<RESPONSE extends WxPayBaseResponse> extends WxPayBaseData {

    private static final Log _LOG = LogFactory.getLog(WxPayBaseRequest.class);

    private WxPayAccountMeta __accountMeta;

    public WxPayBaseRequest(WxPayAccountMeta accountMeta) {
        if (StringUtils.isBlank(accountMeta.getMchId())) {
            throw new NullArgumentException(IWxPay.Const.MCH_ID);
        }
        if (StringUtils.isBlank(accountMeta.getMchKey())) {
            throw new NullArgumentException(IWxPay.Const.MCH_KEY);
        }
        __accountMeta = accountMeta;
        //
        this.mchId(__accountMeta.getMchId());
        this.nonceStr(__doCreateNonceStr());
    }

    /**
     * @return 返回用于签名的请求参数映射
     */
    protected Map<String, Object> buildSignatureParams() {
        Map<String, Object> _params = new HashMap<String, Object>();
        _params.put(IWxPay.Const.MCH_ID, this.mchId());
        _params.put(IWxPay.Const.NONCE_STR, this.nonceStr());
        return _params;
    }

    /**
     * @return 返回API请求地址
     */
    protected abstract String __doGetRequestURL();

    /**
     * @param httpResponse HTTP请求回应对象
     * @return 分析请求回应结果
     * @throws Exception 可能产生的任何异常
     */
    protected abstract RESPONSE __doParseResponse(IHttpResponse httpResponse) throws Exception;

    /**
     * @return 提交请求并返回回应结果
     * @throws Exception 可能产生的任何异常
     */
    public RESPONSE execute() throws Exception {
        Map<String, Object> _params = buildSignatureParams();
        _params.put(IWxPay.Const.SIGN, __doCreateSignature(_params, __accountMeta.getMchKey()));
        //
        IHttpResponse _response = HttpClientHelper.create()
                .customSSL(__accountMeta.getConnectionSocketFactory())
                .post(__doGetRequestURL(), __doBuildXML(_params));
        if (_response != null) {
            if (_response.getStatusCode() == 200) {
                return __doParseResponse(_response);
            } else if (_LOG.isDebugEnabled()) {
                _LOG.debug("ResponseBody: " + _response.toString());
            }
        }
        return null;
    }
}
