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
package net.ymate.payment.wxpay;

import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.impl.DefaultModuleCfg;
import net.ymate.payment.wxpay.request.*;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/15 下午 13:12
 * @version 1.0
 */
@Module
public class WxPay implements IModule, IWxPay {

    private static final Log _LOG = LogFactory.getLog(WxPay.class);

    public static final Version VERSION = new Version(1, 0, 0, WxPay.class.getPackage().getImplementationVersion(), Version.VersionType.Alphal);

    private static volatile IWxPay __instance;

    private YMP __owner;

    private IWxPayModuleCfg __moduleCfg;

    private boolean __inited;

    public static IWxPay get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(WxPay.class);
                }
            }
        }
        return __instance;
    }

    public String getName() {
        return IWxPay.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-payment-wxpay-" + VERSION);
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

    public WxPayUnifiedOrder unifiedOrder(String appId, String body, String outTradeNo, Integer totalFee, String spbillCreateIp, String notifyUrl, TradeType tradeType) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayUnifiedOrder(_meta, body, outTradeNo, totalFee, spbillCreateIp, notifyUrl, tradeType.name());
        }
        return null;
    }

    public WxPayCloseOrder closeOrder(String appId, String outTradeNo) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayCloseOrder(_meta, outTradeNo);
        }
        return null;
    }

    public WxPayDownloadBill downloadBill(String appId, String billData, BillType billType) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayDownloadBill(_meta, billData, billType);
        }
        return null;
    }

    public WxPayMchPay mchPay(String appId, String partnerTradeNo, String openId, boolean checkName, Integer amount, String desc, String spbillCreateIp) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayMchPay(_meta, partnerTradeNo, openId, checkName, amount, desc, spbillCreateIp);
        }
        return null;
    }

    public WxPayMchPayQuery mchPayQuery(String appId, String partnerTradeNo) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayMchPayQuery(_meta, partnerTradeNo);
        }
        return null;
    }

    public WxPayOrderQuery orderQuery(String appId, String transactionId, String outTradeNo) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayOrderQuery(_meta, transactionId, outTradeNo);
        }
        return null;
    }

    public WxPayRedPackSend redPackSend(String appId, String mchBillNo, String sendName, String reOpenId, Integer totalAmount, Integer totalNum, String wishing, String clientIp, String actName, String remark) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayRedPackSend(_meta, mchBillNo, sendName, reOpenId, totalAmount, totalNum, wishing, clientIp, actName, remark);
        }
        return null;
    }

    public WxPayRedPackSendGroup redPackSendGroup(String appId, String mchBillNo, String sendName, String reOpenId, Integer totalAmount, Integer totalNum, String wishing, String clientIp, String actName, String remark) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayRedPackSendGroup(_meta, mchBillNo, sendName, reOpenId, totalAmount, totalNum, wishing, clientIp, actName, remark);
        }
        return null;
    }

    public WxPayRedPackInfo redPackInfo(String appId, String mchBillNo) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayRedPackInfo(_meta, mchBillNo);
        }
        return null;
    }

    public WxPayRefund refund(String appId, String transactionId, String outTradeNo, String outRefundNo, Integer totalFee, Integer refundFee) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayRefund(_meta, transactionId, outTradeNo, outRefundNo, totalFee, refundFee);
        }
        return null;
    }

    public WxPayRefundQuery refundQuery(String appId, String transactionId, String outTradeNo, String outRefundNo, String refundId) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayRefundQuery(_meta, transactionId, outTradeNo, outRefundNo, refundId);
        }
        return null;
    }

    public WxPayShortUrl shortUrl(String appId, String longUrl) throws Exception {
        WxPayAccountMeta _meta = __moduleCfg.getAccountProvider().getAccount(appId);
        if (_meta != null) {
            return new WxPayShortUrl(_meta, longUrl);
        }
        return null;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            this.__moduleCfg.getAccountProvider().destroy();
            //
            __moduleCfg = null;
            __owner = null;
        }
    }

    public YMP getOwner() {
        return __owner;
    }

    public IWxPayModuleCfg getModuleCfg() {
        return __moduleCfg;
    }
}
