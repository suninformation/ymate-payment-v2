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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.ymate.payment.alipay.*;
import net.ymate.payment.alipay.impl.DefaultAliPayRequestSender;
import net.ymate.payment.alipay.support.SignatureUtils;
import net.ymate.platform.core.util.DateTimeUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝接口请求对象
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/7 上午9:42
 * @version 1.0
 */
public class AliPayBaseRequest<DATA extends IAliPayRequestData, RESPONSE extends IAliPayResponse> implements IAliPayRequest<RESPONSE> {

    private AliPayAccountMeta accountMeta;

    private String method;

    private String version;

    private DATA bizContent;

    private IAliPayResponseParser<RESPONSE> responseParser;

    private boolean needNotify;

    private boolean needReturn;

    public AliPayBaseRequest(AliPayAccountMeta accountMeta, String method, String version, DATA bizContent, boolean needNotify, boolean needReturn, IAliPayResponseParser<RESPONSE> responseParser) {
        if (accountMeta == null) {
            throw new NullArgumentException("accountMeta");
        }
        if (StringUtils.isBlank(method)) {
            throw new NullArgumentException("method");
        }
        if (bizContent == null) {
            throw new NullArgumentException("bizContent");
        }
        if (responseParser == null) {
            throw new NullArgumentException("responseParser");
        }
        //
        this.accountMeta = accountMeta;
        this.method = method;
        this.version = StringUtils.defaultIfBlank(version, "1.0");
        this.bizContent = bizContent;
        //
        this.needNotify = needNotify;
        this.needReturn = needReturn;
        //
        this.responseParser = responseParser;
    }

    @Override
    public IAliPayReqeustSender<RESPONSE> build() throws Exception {
        Map<String, String> _params = new HashMap<String, String>();
        //
        String _charset = StringUtils.defaultIfBlank(accountMeta.getCharset(), IAliPay.Const.CHARSET_UTF8);
        //
        _params.put(IAliPay.Const.APP_ID, accountMeta.getAppId());
        _params.put(IAliPay.Const.METHOD, method);
        _params.put(IAliPay.Const.CHARSET, _charset);
        _params.put(IAliPay.Const.FORMAT, accountMeta.getFormat());
        _params.put(IAliPay.Const.SIGN_TYPE, accountMeta.getSignType().name());
        _params.put(IAliPay.Const.TIMESTAMP, DateTimeUtils.formatTime(System.currentTimeMillis(), IAliPay.Const.DATE_TIME_FORMAT));
        _params.put(IAliPay.Const.VERSION, version);
        //
        if (needNotify) {
            if (StringUtils.isNotBlank(accountMeta.getNotifyUrl())) {
                _params.put(IAliPay.Const.NOTIFY_URL, accountMeta.getNotifyUrl());
            }
        }
        if (needReturn) {
            if (StringUtils.isNotBlank(accountMeta.getReturnUrl())) {
                _params.put(IAliPay.Const.RETURN_URL, accountMeta.getReturnUrl());
            }
        }
        //
        String _bizContentStr = JSON.toJSONString(this.bizContent.buildRequestParams(), SerializerFeature.QuoteFieldNames);
        _params.put(IAliPay.Const.BIZ_CONTENT_KEY, _bizContentStr);
        //
        String _signStr = SignatureUtils.sign(_params, accountMeta.getPrivateKey(), _charset, accountMeta.getSignType());
        _params.put(IAliPay.Const.SIGN, _signStr);
        //
        return new DefaultAliPayRequestSender<RESPONSE>(_params, _charset, this.responseParser);
    }

    @Override
    public AliPayAccountMeta getAccountMeta() {
        return accountMeta;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public DATA getBizContent() {
        return bizContent;
    }
}
