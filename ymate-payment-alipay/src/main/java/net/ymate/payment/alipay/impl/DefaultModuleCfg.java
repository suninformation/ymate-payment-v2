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

import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.IAliPayAccountProvider;
import net.ymate.payment.alipay.IAliPayEventHandler;
import net.ymate.payment.alipay.IAliPayModuleCfg;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/06 下午 17:15
 * @version 1.0
 */
public class DefaultModuleCfg implements IAliPayModuleCfg {

    private String __gatewayUrl;

    private IAliPayAccountProvider __accountProvider;

    private IAliPayEventHandler __eventHandler;

    private String __defaultAccountId;

    private boolean __signCheckDisabled;

    public DefaultModuleCfg(YMP owner) {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IAliPay.MODULE_NAME);
        //
        __gatewayUrl = StringUtils.defaultIfBlank(_moduleCfgs.get("gateway_url"), "https://openapi.alipay.com/gateway.do");
        if (!StringUtils.startsWithIgnoreCase(__gatewayUrl, "https://") && !StringUtils.startsWithIgnoreCase(__gatewayUrl, "http://")) {
            throw new IllegalArgumentException("gateway_url address is invalid");
        }
        //
        __accountProvider = ClassUtils.impl(_moduleCfgs.get("account_provider_class"), IAliPayAccountProvider.class, getClass());
        if (__accountProvider == null) {
            __accountProvider = new DefaultAliPayAccountProvider();
            //
            AliPayAccountMeta _meta = new AliPayAccountMeta(_moduleCfgs.get(IAliPay.Const.APP_ID), _moduleCfgs.get(IAliPay.Const.SIGN_TYPE), _moduleCfgs.get("private_key"), _moduleCfgs.get("public_key"));
            _meta.setCharset(StringUtils.defaultIfBlank(_moduleCfgs.get(IAliPay.Const.CHARSET), IAliPay.Const.CHARSET_UTF8));
            _meta.setFormat(StringUtils.defaultIfBlank(_moduleCfgs.get(IAliPay.Const.FORMAT), IAliPay.Const.FORMAT_JSON));
            _meta.setNotifyUrl(StringUtils.trimToNull(_moduleCfgs.get(IAliPay.Const.NOTIFY_URL)));
            _meta.setReturnUrl(StringUtils.trimToNull(_moduleCfgs.get(IAliPay.Const.RETURN_URL)));
            //
            __defaultAccountId = _meta.getAppId();
            __accountProvider.registerAccount(_meta);
        } else {
            __defaultAccountId = StringUtils.trimToNull(_moduleCfgs.get("default_account_id"));
        }
        //
        __eventHandler = ClassUtils.impl(_moduleCfgs.get("event_handler_class"), IAliPayEventHandler.class, getClass());
        if (__eventHandler == null) {
            throw new NullArgumentException("event_handler_class");
        }
        //
        __signCheckDisabled = BlurObject.bind(_moduleCfgs.get("sign_check_disabled")).toBooleanValue();
    }

    @Override
    public String getGatewayUrl() {
        return __gatewayUrl;
    }

    @Override
    public IAliPayAccountProvider getAccountProvider() {
        return __accountProvider;
    }

    @Override
    public IAliPayEventHandler getEventHandler() {
        return __eventHandler;
    }

    @Override
    public String getDefaultAccountId() {
        return __defaultAccountId;
    }

    @Override
    public boolean isSignCheckDisabled() {
        return __signCheckDisabled;
    }
}