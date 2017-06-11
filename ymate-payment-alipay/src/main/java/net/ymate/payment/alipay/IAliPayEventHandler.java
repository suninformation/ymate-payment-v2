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

import net.ymate.payment.alipay.base.AliPayBaseNotify;
import net.ymate.payment.alipay.base.AliPayBaseReturn;
import net.ymate.payment.alipay.data.TradePagePayData;
import net.ymate.payment.alipay.data.TradeWapPayData;
import net.ymate.platform.webmvc.view.IView;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/9 上午8:57
 * @version 1.0
 */
public interface IAliPayEventHandler {

    /**
     * @param orderId 订单ID
     * @param attach  附加信息
     * @return 构建PC端支付请求数据对象
     * @throws Exception 可能产生的任何异常
     */
    TradePagePayData buildTradePagePayData(String orderId, String attach) throws Exception;

    /**
     * @param orderId 订单ID
     * @param attach  附加信息
     * @return 构建PC端支付请求数据对象
     * @throws Exception 可能产生的异常
     */
    TradeWapPayData buildTradeWapPayData(String orderId, String attach) throws Exception;

    /**
     * 异步支付通知消息到达事件处理方法，该方法的执行过程中若无任何异常被抛出则视为执行成功并向支付宝通知服务返回success字符串
     *
     * @param notifyData 异步通知对象
     * @throws Exception 可能产生的任何异常
     */
    void onNotifyReceived(AliPayBaseNotify notifyData) throws Exception;

    /**
     * @param returnData 页面跳转参数对象
     * @return 返回视图对象
     * @throws Exception 可能产生的任何异常
     */
    IView onReturnCallback(AliPayBaseReturn returnData) throws Exception;

    /**
     * 异常处理方法
     *
     * @param cause 产生的异常对象
     * @throws Exception 可能产生的任何异常
     */
    void onExceptionCaught(Throwable cause) throws Exception;
}
