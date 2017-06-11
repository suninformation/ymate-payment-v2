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
package net.ymate.payment.alipay;

import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.payment.alipay.base.AliPayBaseNotify;
import net.ymate.payment.alipay.base.AliPayBaseReturn;
import net.ymate.payment.alipay.data.*;
import net.ymate.payment.alipay.impl.DefaultModuleCfg;
import net.ymate.payment.alipay.request.*;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/06 下午 17:15
 * @version 1.0
 */
@Module
public class AliPay implements IModule, IAliPay {

    private static final Log _LOG = LogFactory.getLog(AliPay.class);

    public static final Version VERSION = new Version(2, 0, 0, AliPay.class.getPackage().getImplementationVersion(), Version.VersionType.Alphal);

    private static volatile IAliPay __instance;

    private YMP __owner;

    private IAliPayModuleCfg __moduleCfg;

    private boolean __inited;

    public static IAliPay get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(AliPay.class);
                }
            }
        }
        return __instance;
    }

    public String getName() {
        return IAliPay.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-payment-alipay-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(owner);
            //
            __moduleCfg.getAccountProvider().init(this);
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public IAliPayRequest tradePagePay(String appId, String orderId, String attach) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null && __moduleCfg.getEventHandler() != null) {
            return new AliPayTradePagePay(_meta, __moduleCfg.getEventHandler().buildTradePagePayData(orderId, attach));
        }
        return null;
    }

    public IAliPayRequest tradeWapPay(String appId, String orderId, String attach) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null && __moduleCfg.getEventHandler() != null) {
            return new AliPayTradeWapPay(_meta, __moduleCfg.getEventHandler().buildTradeWapPayData(orderId, attach));
        }
        return null;
    }

    public IAliPayRequest<AliPayTradeQuery.Response> tradeQuery(String appId, String tradeNo, String outTradeNo) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new AliPayTradeQuery(_meta, new TradeQueryData(outTradeNo, tradeNo));
        }
        return null;
    }

    public IAliPayRequest<AliPayTradeRefund.Response> tradeRefund(String appId, TradeRefundData refundData) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new AliPayTradeRefund(_meta, refundData);
        }
        return null;
    }

    public IAliPayRequest<AliPayTradeRefundQuery.Response> tradeRefundQuery(String appId, String tradeNo, String outTradeNo, String outRequestNo) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new AliPayTradeRefundQuery(_meta, new TradeRefundQueryData(outTradeNo, tradeNo, outRequestNo));
        }
        return null;
    }

    public IAliPayRequest<AliPayTradeClose.Response> tradeClose(String appId, String tradeNo, String outTradeNo) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new AliPayTradeClose(_meta, new TradeCloseData(outTradeNo, tradeNo));
        }
        return null;
    }

    public IAliPayRequest<AliPayBillDownloadUrlQuery.Response> billDownloadUrlQuery(String appId, String billType, String billDate) throws Exception {
        AliPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new AliPayBillDownloadUrlQuery(_meta, new BillDownloadUrlQueryData(billType, billDate));
        }
        return null;
    }

    public String onNotify(AliPayBaseNotify baseNotify) throws Exception {
        IAliPayEventHandler _handler = __moduleCfg.getEventHandler();
        try {
            _handler.onNotifyReceived(baseNotify);
        } catch (Exception e) {
            _handler.onExceptionCaught(RuntimeUtils.unwrapThrow(e));
            return "fail";
        }
        return "success";
    }

    public IView onReturnCallback(AliPayBaseReturn baseReturn) throws Exception {
        return __moduleCfg.getEventHandler().onReturnCallback(baseReturn);
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            __moduleCfg.getAccountProvider().destroy();
            //
            __moduleCfg = null;
            __owner = null;
        }
    }

    public YMP getOwner() {
        return __owner;
    }

    public IAliPayModuleCfg getModuleCfg() {
        return __moduleCfg;
    }
}
