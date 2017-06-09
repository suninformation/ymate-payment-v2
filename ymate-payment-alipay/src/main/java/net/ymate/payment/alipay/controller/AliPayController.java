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
import net.ymate.payment.alipay.IAliPayEventHandler;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.payment.alipay.base.AliPayBaseNotify;
import net.ymate.payment.alipay.base.AliPayBaseReturn;
import net.ymate.payment.alipay.request.AliPayTradePagePayRequest;
import net.ymate.payment.alipay.request.AliPayTradeWapPayRequest;
import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HtmlView;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/8 下午11:06
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/alipay")
public class AliPayController {

    private static String __HTMP_TMPL = "<!DOCTYPE html><!--[if IE 8]><html lang=\"en\" class=\"ie8 no-js\"> <![endif]--><!--[if IE 9]><html lang=\"en\" class=\"ie9 no-js\"> <![endif]--><!--[if !IE]><!--><html lang=\"en\" class=\"no-js\"><!--<![endif]--><head><meta charset=\"${charset}\"><title>跳转支付</title></head><body>${body}</body></html>";

    @RequestMapping(value = "/{app_id}/page", method = {Type.HttpMethod.POST, Type.HttpMethod.GET})
    public IView __pagePay(@PathVariable("app_id") String appId, @RequestParam String state, @RequestParam String attach) throws Exception {
        try {
            AliPayAccountMeta _meta = AliPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                String _charset = StringUtils.defaultIfBlank(_meta.getCharset(), IAliPay.Const.CHARSET_UTF8);
                //
                AliPayTradePagePayRequest _request = new AliPayTradePagePayRequest(_meta, AliPay.get().getModuleCfg().getEventHandler().buildTradePagePayRequestData(state, attach));
                String _content = ExpressionUtils.bind(__HTMP_TMPL).set("charset", _charset).set("body", _request.build().executeActionForm()).getResult();
                return new HtmlView(_content);
            }
        } catch (Exception e) {
            AliPay.get().getModuleCfg().getEventHandler().onExceptionCaught(RuntimeUtils.unwrapThrow(e));
        }
        return HttpStatusView.METHOD_NOT_ALLOWED;
    }

    @RequestMapping(value = "/{app_id}/wap", method = {Type.HttpMethod.POST, Type.HttpMethod.GET})
    public IView __wapPay(@PathVariable("app_id") String appId, @RequestParam String state, @RequestParam String attach) throws Exception {
        try {
            AliPayAccountMeta _meta = AliPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                String _charset = StringUtils.defaultIfBlank(_meta.getCharset(), IAliPay.Const.CHARSET_UTF8);
                //
                AliPayTradeWapPayRequest _request = new AliPayTradeWapPayRequest(_meta, AliPay.get().getModuleCfg().getEventHandler().buildTradeWapPayRequestData(state, attach));
                String _content = ExpressionUtils.bind(__HTMP_TMPL).set("charset", _charset).set("body", _request.build().executeActionForm()).getResult();
                return new HtmlView(_content);
            }
        } catch (Exception e) {
            AliPay.get().getModuleCfg().getEventHandler().onExceptionCaught(RuntimeUtils.unwrapThrow(e));
        }
        return HttpStatusView.METHOD_NOT_ALLOWED;
    }

    @RequestMapping(value = "/notify", method = Type.HttpMethod.POST)
    public IView __notify(@ModelBind AliPayBaseNotify baseNotify) throws Exception {
        IAliPayEventHandler _handler = AliPay.get().getModuleCfg().getEventHandler();
        try {
            _handler.onNotifyReceived(baseNotify);
        } catch (Exception e) {
            _handler.onExceptionCaught(RuntimeUtils.unwrapThrow(e));
            return View.textView("fail");
        }
        return View.textView("success");
    }

    @RequestMapping("/calback")
    public IView __calback(@ModelBind AliPayBaseReturn baseReturn) throws Exception {
        IAliPayEventHandler _handler = AliPay.get().getModuleCfg().getEventHandler();
        try {
            IView _view = _handler.onReturnCallback(baseReturn);
            if (_view != null) {
                return _view;
            }
        } catch (Exception e) {
            _handler.onExceptionCaught(RuntimeUtils.unwrapThrow(e));
        }
        return HttpStatusView.METHOD_NOT_ALLOWED;
    }
}
