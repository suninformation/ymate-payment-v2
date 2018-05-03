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
package net.ymate.payment.wxpay.controller;

import net.ymate.framework.core.util.WebUtils;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.IWxPayEventHandler;
import net.ymate.payment.wxpay.WxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.PathVariable;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 微信支付--H5支付模式
 *
 * @author 刘镇 (suninformation@163.com) on 2017/7/7 下午12:00
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/wxpay")
public class WxPayMWebController {

    @RequestMapping(value = "{app_id}/mweb", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView __doMWeb(@PathVariable("app_id") String appId,
                          @VRequired @RequestParam String state,
                          @RequestParam String attach,
                          @RequestParam("redirect_url") String redirectUrl) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            WxPayAccountMeta _meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                WxPayUnifiedOrder _request = _eventHandler.buildUnifiedOrderRequest(_meta, IWxPay.TradeType.MWEB, state, attach);
                WxPayUnifiedOrder.Response _response = _request.execute();
                //
                if (_response.checkReturnCode() && _response.checkResultCode() && (WxPay.get().getModuleCfg().isSignCheckDisabled() || _response.checkSignature(_meta.getMchKey()))) {
                    if (StringUtils.isNotBlank(redirectUrl)) {
                        return View.redirectView(_response.mwebUrl() + "&redirect_url=" + WebUtils.encodeURL(redirectUrl));
                    }
                    return View.redirectView(_response.mwebUrl());
                }
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }
}
