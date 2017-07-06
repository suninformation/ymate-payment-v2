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

import net.ymate.framework.webmvc.WebResult;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.IWxPayEventHandler;
import net.ymate.payment.wxpay.WxPay;
import net.ymate.payment.wxpay.WxPayException;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseData;
import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.payment.wxpay.request.WxPayOrderQuery;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;
import net.ymate.payment.wxpay.support.WxPayRequestProcessor;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.validation.validate.VRequried;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;
import net.ymate.platform.webmvc.view.impl.TextView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/1/4 上午10:37
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/wxpay")
public class WxPayNotifyController {

    private static final Log _LOG = LogFactory.getLog(WxPayNotifyController.class);

    /**
     * @param notify 微信支付异步通知对象
     * @return 接收微信异步通知
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "/notify", method = Type.HttpMethod.POST)
    @RequestProcessor(WxPayRequestProcessor.class)
    public IView __doNotify(@RequestParam WxPayNotifyResponse notify) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            //
            Map<String, Object> _returnValues = new HashMap<String, Object>();
            //
            WxPayAccountMeta _meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(notify.appId());
            if (_meta != null) {
                try {
                    if (!WxPay.get().getModuleCfg().isSignCheckDisabled() || notify.checkSignature(_meta.getMchKey())) {
                        if (StringUtils.isNotBlank(notify.productId())) {
                            WxPayUnifiedOrder _request = _eventHandler.buildUnifiedOrderRequest(_meta, IWxPay.TradeType.NATIVE, notify.productId(), notify.attach());
                            WxPayUnifiedOrder.Response _response = _request.execute();
                            if (_response.checkReturnCode() && _response.checkResultCode()) {
                                if (_meta.isSandboxEnabled() || _response.checkSignature(_meta.getMchKey())) {
                                    _returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.SUCCESS.name());
                                    _returnValues.put(IWxPay.Const.RESULT_CODE, IWxPay.ResultCode.SUCCESS.name());
                                    _returnValues.put(IWxPay.Const.APP_ID, _meta.getAppId());
                                    _returnValues.put(IWxPay.Const.MCH_ID, _meta.getMchId());
                                    _returnValues.put(IWxPay.Const.NONCE_STR, WxPayBaseData.__doCreateNonceStr());
                                    _returnValues.put("prepay_id", _response.prepayId());
                                    _returnValues.put("trade_type", IWxPay.TradeType.NATIVE.name());
                                    _returnValues.put(IWxPay.Const.SIGN, WxPayBaseData.__doCreateSignature(_returnValues, _meta.getMchKey()));
                                } else {
                                    _returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.FAIL.name());
                                    _returnValues.put(IWxPay.Const.RETURN_MSG, IWxPay.ErrCode.SIGNERROR.desc());
                                }
                            }
                        } else {
                            _eventHandler.onNotifyReceived(notify);
                            //
                            _returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.SUCCESS.name());
                        }
                    } else {
                        _returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.FAIL.name());
                        _returnValues.put(IWxPay.Const.RETURN_MSG, IWxPay.ErrCode.SIGNERROR.desc());
                    }
                } catch (Exception e) {
                    _returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.FAIL.name());
                    if (e instanceof WxPayException) {
                        _returnValues.put(IWxPay.Const.RETURN_MSG, ((WxPayException) e).getErrMsg());
                    } else {
                        _eventHandler.onExceptionCaught(RuntimeUtils.unwrapThrow(e));
                        _returnValues.put(IWxPay.Const.RETURN_MSG, IWxPay.ErrCode.SYSTEMERROR);
                    }
                }
                String _returnStr = WxPayBaseData.__doBuildXML(_returnValues);
                _LOG.debug("WxPay Notify Response: [" + _returnStr + "]");
                return new TextView(_returnStr, "text/xml");
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * @param appId 微信公众号应用ID
     * @param state 商品或订单ID
     * @return 同步回调(订单状态主动检查)
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "{app_id}/callback", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView __doCallback(@PathVariable("app_id") String appId, @VRequried @RequestParam String state) throws Exception {
        IWxPayEventHandler _eventHandler = WxPay.get().getModuleCfg().getEventHandler();
        if (_eventHandler != null) {
            if (_eventHandler.onReturnCallback(state)) {
                WxPayOrderQuery _query = WxPay.get().orderQuery(appId, null, state);
                WxPayOrderQuery.Response _response = _query.execute();
                _eventHandler.onNotifyReceived(_response);
                //
                return WebResult.SUCCESS().dataAttr("trade_state", _response.tradeState()).dataAttr("trade_state_desc", _response.tradeStateDesc()).toJSON();
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }
}
