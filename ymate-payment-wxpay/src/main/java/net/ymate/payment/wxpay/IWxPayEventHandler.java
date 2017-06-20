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
import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/9 上午8:57
 * @version 1.0
 */
public interface IWxPayEventHandler {

    /**
     * @param accountMeta 微信支付账户
     * @param tradeType   交易类型
     * @param orderId     订单ID
     * @param attach      附加信息
     * @return 构建微信统一支付请求数据对象
     * @throws Exception 可能产生的任何异常
     */
    WxPayUnifiedOrder buildUnifiedOrderRequest(WxPayAccountMeta accountMeta, IWxPay.TradeType tradeType, String orderId, String attach) throws Exception;

    /**
     * 异步支付通知消息到达事件处理方法，该方法的执行过程中若无任何异常被抛出则视为执行成功并向微信通知服务返回SUCCESS字符串
     *
     * @param notifyData 异步通知对象
     * @throws Exception 可能产生的任何异常
     */
    void onNotifyReceived(WxPayNotifyResponse notifyData) throws Exception;

    /**
     * @param orderId 订单ID
     * @return 返回是否需要发启订单状态查询
     * @throws Exception 可能产生的任何异常
     */
    boolean onReturnCallback(String orderId) throws Exception;

    /**
     * 异常处理方法
     *
     * @param cause 产生的异常对象
     * @throws Exception 可能产生的任何异常
     */
    void onExceptionCaught(Throwable cause) throws Exception;

    /**
     * @param appId 微信公众号应用ID
     * @return 获取微信JS接口的临时票据
     * @throws Exception 可能产生的任何异常
     */
    String getJsApiTicket(String appId) throws Exception;
}
