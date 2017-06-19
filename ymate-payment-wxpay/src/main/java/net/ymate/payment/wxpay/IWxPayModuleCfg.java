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

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/15 下午 13:12
 * @version 1.0
 */
public interface IWxPayModuleCfg {

    /**
     * @return 微信支付账户提供者接口实现类, 若未提供则使用默认配置
     */
    IWxPayAccountProvider getAccountProvider();

    /**
     * @return 支付事件处理器
     */
    IWxPayEventHandler getEventHandler();

    /**
     * @return 默认微信支付帐户ID, 默认值: 若采用账户提供者接口默认实现时取值默认应用ID, 否则为空
     */
    String getDefaultAccountId();

    /**
     * @return 调用JS支付的JSP视图文件路径, 默认值: wxpay_jsapi
     */
    String getJsApiView();

    /**
     * @return 调用扫码支付的JSP视图文件路径, 默认值: wxpay_native
     */
    String getNativeView();

    /**
     * @return 禁用报文签名验证(验签), 默认值: false
     */
    boolean isSignCheckDisabled();
}