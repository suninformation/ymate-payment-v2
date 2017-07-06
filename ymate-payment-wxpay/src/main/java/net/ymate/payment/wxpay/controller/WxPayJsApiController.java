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

import com.alibaba.fastjson.JSONObject;
import net.ymate.framework.core.util.WebUtils;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.IWxPayEventHandler;
import net.ymate.payment.wxpay.WxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseData;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.validation.validate.VRequried;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.PathVariable;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.View;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付--JS_API模式
 * <p>
 * 在微信浏览器里面打开H5网页中执行JS调起支付，接口输入输出数据格式为JSON。
 * 成功调起支付需要三个步骤：
 * 步骤1：网页授权获取用户openid
 * 步骤2：使用统一支付接口，获取prepay_id
 * 步骤3：使用JSAPI调起支付
 * </p>
 *
 * @author 刘镇 (suninformation@163.com) on 15/1/4 上午10:41
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/wxpay")
public class WxPayJsApiController {

    /**
     * @param appId  微信公众号应用ID
     * @param openId 微信用户身份唯一标识
     * @param state  商品或订单ID
     * @param attach 附加信息
     * @return 微信支付 -- JS_API模式
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "{app_id}/jsapi", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView __doJsApi(@PathVariable("app_id") String appId,
                           @VRequried @RequestParam("open_id") String openId,
                           @VRequried @RequestParam String state,
                           @RequestParam String attach,
                           @RequestParam boolean debug) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            WxPayAccountMeta _meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (_meta != null) {
                WxPayUnifiedOrder _request = _eventHandler.buildUnifiedOrderRequest(_meta, IWxPay.TradeType.JSAPI, state, attach).openId(openId);
                WxPayUnifiedOrder.Response _response = _request.execute();
                //
                if (_response.checkReturnCode() && _response.checkResultCode() && (WxPay.get().getModuleCfg().isSignCheckDisabled() || _response.checkSignature(_meta.getMchKey()))) {
                    // 封装JSAPI初始化相关参数
                    String _currentURL = WebUtils.buildURL(WebContext.getRequest(), "payment/wxpay/" + appId + "/js_api" + "?" + WebContext.getRequest().getQueryString(), true);
                    //
                    String _timestamp = DateTimeUtils.currentTimeUTC() + "";
                    String _nonceStr = WxPayBaseData.__doCreateNonceStr();
                    String _config = __buildJsApiConfigStr(appId, _eventHandler.getJsApiTicket(appId), StringUtils.substringBefore(_currentURL, "#"), _timestamp, _nonceStr, debug);
                    // 封装基于JSAPI的支付调用相关参数
                    Map<String, Object> _paramMap = new HashMap<String, Object>();
                    _paramMap.put("timestamp", _timestamp);
                    _paramMap.put("nonceStr", _nonceStr);
                    _paramMap.put("package", "prepay_id=" + _response.prepayId());
                    _paramMap.put("signType", IWxPay.Const.SIGN_TYPE_MD5);
                    _paramMap.put("paySign", WxPayBaseData.__doCreateSignature(_paramMap, _meta.getMchKey()));
                    //
                    return View.jspView(WxPay.get().getModuleCfg().getJsApiView())
                            .addAttribute("_config", _config)
                            .addAttribute("_data", _paramMap)
                            .addAttribute("_trade_no", state).addAttribute("_app_id", appId);
                }
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String __buildJsApiConfigStr(String appId, String jsapiTicket, String url, String timestamp, String noncestr, boolean debug) throws Exception {
        String _signature = "jsapi_ticket=" + jsapiTicket + "&" + "noncestr=" + noncestr + "&" + "timestamp=" + timestamp + "&" + "url=" + url;
        _signature = DigestUtils.sha1Hex(_signature);
        //
        JSONObject _json = new JSONObject();
        _json.put("debug", debug);
        _json.put("appId", appId);
        _json.put("timestamp", timestamp);
        _json.put("nonceStr", noncestr);
        _json.put("signature", _signature);
        _json.put("jsApiList", new String[]{"chooseWXPay"});
        //
        return _json.toJSONString();
    }
}
