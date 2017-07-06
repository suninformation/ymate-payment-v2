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
package net.ymate.payment.wxpay.impl;

import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.IWxPayAccountProvider;
import net.ymate.payment.wxpay.IWxPayEventHandler;
import net.ymate.payment.wxpay.IWxPayModuleCfg;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/15 下午 13:12
 * @version 1.0
 */
public class DefaultModuleCfg implements IWxPayModuleCfg {

    private IWxPayAccountProvider __accountProvider;

    private IWxPayEventHandler __eventHandler;

    private String __defaultAccountId;

    private String __jsApiView;

    private String __nativeView;

    private boolean __signCheckDisabled;

    public DefaultModuleCfg(YMP owner) {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IWxPay.MODULE_NAME);
        //
        __accountProvider = ClassUtils.impl(_moduleCfgs.get("account_provider_class"), IWxPayAccountProvider.class, getClass());
        if (__accountProvider == null) {
            __accountProvider = new DefaultWxPayAccountProvider();
            //
            WxPayAccountMeta _meta = new WxPayAccountMeta(_moduleCfgs.get("app_id"),
                    _moduleCfgs.get(IWxPay.Const.MCH_ID),
                    _moduleCfgs.get(IWxPay.Const.MCH_KEY),
                    _moduleCfgs.get("cert_file_path"),
                    _moduleCfgs.get(IWxPay.Const.NOTIFY_URL));
            _meta.setSandboxEnabled(BlurObject.bind(_moduleCfgs.get("sandbox_enabled")).toBooleanValue());
            _meta.setSandboxPrefix(StringUtils.defaultIfBlank(_moduleCfgs.get("sandbox_prefix"), "sandboxnew"));
            //
            __defaultAccountId = _meta.getAppId();
            __accountProvider.registerAccount(_meta);
        } else {
            __defaultAccountId = StringUtils.trimToNull(_moduleCfgs.get("default_account_id"));
        }
        //
        __eventHandler = ClassUtils.impl(_moduleCfgs.get("event_handler_class"), IWxPayEventHandler.class, getClass());
        if (__eventHandler == null) {
            throw new NullArgumentException("event_handler_class");
        }
        //
        __jsApiView = StringUtils.defaultIfBlank(_moduleCfgs.get("jsapi_view"), "wxpay_jsapi");
        __nativeView = StringUtils.defaultIfBlank(_moduleCfgs.get("native_view"), "wxpay_native");
        //
        __signCheckDisabled = BlurObject.bind(_moduleCfgs.get("sign_check_disabled")).toBooleanValue();
    }

    public IWxPayAccountProvider getAccountProvider() {
        return __accountProvider;
    }

    public IWxPayEventHandler getEventHandler() {
        return __eventHandler;
    }

    public String getDefaultAccountId() {
        return __defaultAccountId;
    }

    public String getJsApiView() {
        return __jsApiView;
    }

    public String getNativeView() {
        return __nativeView;
    }

    public boolean isSignCheckDisabled() {
        return __signCheckDisabled;
    }
}