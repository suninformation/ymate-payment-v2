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
package net.ymate.payment.alipay.controller;

import net.ymate.payment.alipay.AliPay;
import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.IAliPayRequest;
import net.ymate.payment.alipay.base.AliPayBaseNotify;
import net.ymate.payment.alipay.base.AliPayBaseReturn;
import net.ymate.payment.alipay.intercept.AliPaySignatureCheckInterceptor;
import net.ymate.platform.core.beans.annotation.Before;
import net.ymate.platform.core.beans.annotation.Clean;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HtmlView;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/8 下午11:06
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/alipay")
@Before(AliPaySignatureCheckInterceptor.class)
public class AliPayController {

    @RequestMapping(value = "/{app_id}/page", method = {Type.HttpMethod.POST, Type.HttpMethod.GET})
    @Clean
    public IView __pagePay(@PathVariable("app_id") String appId, @RequestParam String state, @RequestParam String attach) throws Exception {
        IAliPayRequest _request = AliPay.get().tradePagePay(appId, state, attach);
        return new HtmlView(_request.build().executeActionForm())
                .setContentType(Type.ContentType.HTML.getContentType()
                        .concat("; charset=")
                        .concat(StringUtils.defaultIfBlank(_request.getAccountMeta().getCharset(), IAliPay.Const.CHARSET_UTF8)));
    }

    @RequestMapping(value = "/{app_id}/wap", method = {Type.HttpMethod.POST, Type.HttpMethod.GET})
    @Clean
    public IView __wapPay(@PathVariable("app_id") String appId, @RequestParam String state, @RequestParam String attach) throws Exception {
        IAliPayRequest _request = AliPay.get().tradeWapPay(appId, state, attach);
        return new HtmlView(_request.build().executeActionForm())
                .setContentType(Type.ContentType.HTML.getContentType()
                        .concat("; charset=")
                        .concat(StringUtils.defaultIfBlank(_request.getAccountMeta().getCharset(), IAliPay.Const.CHARSET_UTF8)));
    }

    @RequestMapping(value = "/notify", method = Type.HttpMethod.POST)
    public IView __notify(@ModelBind AliPayBaseNotify baseNotify) throws Exception {
        return View.textView(AliPay.get().onNotify(baseNotify));
    }

    @RequestMapping("/callback")
    public IView __callback(@ModelBind AliPayBaseReturn baseReturn) throws Exception {
        IView _view = AliPay.get().onReturnCallback(baseReturn);
        if (_view == null) {
            return View.nullView();
        }
        return _view;
    }
}
