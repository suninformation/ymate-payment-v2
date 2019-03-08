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

import net.ymate.framework.commons.ParamUtils;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.IWxPayEventHandler;
import net.ymate.payment.wxpay.WxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseData;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.PathVariable;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebUtils;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付--Native(原生)支付模式
 * <p>
 * 模式一：商户按固定格式生成链接二维码，用户扫码后调微信会将productid和用户openid发送到商户设置的链接上，
 * 商户收到请求生成订单，调用统一支付接口下单提交到微信，微信会返回给商户prepayid。
 * <br>
 * 模式二：商户生成订单，先调用统一支付接口获取到code_url，此URL直接生成二维码，用户扫码后调起支付。
 * </p>
 *
 * @author 刘镇 (suninformation@163.com) on 15/1/4 上午10:42
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/wxpay")
public class WxPayNativeController {

    @RequestMapping(value = "{app_id}/native/static", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView __doNativeStatic(@PathVariable("app_id") String appId,
                                  @VRequired @RequestParam String state,
                                  @RequestParam String attach) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            WxPayAccountMeta _meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                Map<String, Object> _paramMap = new HashMap<String, Object>();
                _paramMap.put(IWxPay.Const.APP_ID, _meta.getAppId());
                _paramMap.put(IWxPay.Const.MCH_ID, _meta.getMchId());
                _paramMap.put("time_stamp", DateTimeUtils.currentTimeUTC() + "");
                _paramMap.put(IWxPay.Const.NONCE_STR, WxPayBaseData.__doCreateNonceStr());
                _paramMap.put("product_id", state);
                _paramMap.put("sign", WxPayBaseData.__doCreateSignature(_paramMap, _meta.getMchKey()));
                //
                String _qrCodeData = WebUtils.encryptStr(WebContext.getRequest(), "weixin://wxpay/bizpayurl?".concat(ParamUtils.buildQueryParamStr(_paramMap, false, null)));
                return View.jspView(WxPay.get().getModuleCfg().getNativeView())
                        .addAttribute("_qrcode_data", _qrCodeData)
                        .addAttribute("_state", state)
                        .addAttribute("_attach", attach).addAttribute("_app_id", appId);
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }

    @RequestMapping(value = "{app_id}/native/dynamic", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView __doNativeDynamic(@PathVariable("app_id") String appId,
                                   @VRequired @RequestParam String state,
                                   @RequestParam String attach) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            WxPayAccountMeta _meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                WxPayUnifiedOrder _request = _eventHandler.buildUnifiedOrderRequest(_meta, IWxPay.TradeType.NATIVE, state, attach);
                WxPayUnifiedOrder.Response _response = _request.execute();
                //
                if (_response.checkReturnCode() && _response.checkResultCode() && (WxPay.get().getModuleCfg().isSignCheckDisabled() || _response.checkSignature(_meta.getMchKey()))) {
                    String _qrCodeData = WebUtils.encryptStr(WebContext.getRequest(), _response.codeUrl());
                    return View.jspView(WxPay.get().getModuleCfg().getNativeView())
                            .addAttribute("_qrcode_data", _qrCodeData)
                            .addAttribute("_state", state)
                            .addAttribute("_attach", attach).addAttribute("_app_id", appId);
                }
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }
}
