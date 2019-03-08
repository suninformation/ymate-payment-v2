/*
 * Copyright 2007-2019 the original author or authors.
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
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付--APP模式
 *
 * @author 刘镇 (suninformation@163.com) on 2019-03-05 15:28
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/wxpay")
public class WxPayAppController {

    /**
     * @param appId  微信公众号应用ID
     * @param state  商品或订单ID
     * @param attach 附加信息
     * @return 微信支付 -- APP模式
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "/{app_id}/app", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView __doApp(@PathVariable("app_id") String appId,
                         @VRequired @RequestParam String state,
                         @RequestParam String attach) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            WxPayAccountMeta _meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                WxPayUnifiedOrder _request = _eventHandler.buildUnifiedOrderRequest(_meta, IWxPay.TradeType.APP, state, attach);
                WxPayUnifiedOrder.Response _response = _request.execute();
                //
                if (_response.checkReturnCode() && _response.checkResultCode() && (WxPay.get().getModuleCfg().isSignCheckDisabled() || _response.checkSignature(_meta.getMchKey()))) {
                    Map<String, Object> _paramMap = new HashMap<String, Object>();
                    _paramMap.put("appid", _meta.getAppId());
                    _paramMap.put("partnerid", _meta.getMchId());
                    _paramMap.put("prepayid", _response.prepayId());
                    _paramMap.put("package", "Sign=WXPay");
                    _paramMap.put("timestamp", String.valueOf(DateTimeUtils.currentTimeUTC()));
                    _paramMap.put("noncestr", WxPayBaseData.__doCreateNonceStr());
                    _paramMap.put("sign", WxPayBaseData.__doCreateSignature(_paramMap, _meta.getMchKey()));
                    //
                    return View.jsonView(_paramMap);
                }
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }
}
