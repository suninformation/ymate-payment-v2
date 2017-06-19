/*
 * Copyright 2007-2016 the original author or authors.
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

import java.util.Collection;

/**
 * @author 刘镇 (suninformation@163.com) on 16/5/23 下午5:58
 * @version 1.0
 */
public interface IWxPayAccountProvider {

    void init(IWxPay owner) throws Exception;

    void destroy() throws Exception;

    void registerAccount(WxPayAccountMeta accountMeta);

    WxPayAccountMeta unregisterAccount(String accountId);

    Collection<String> getAccountIds();

    boolean hasAccount(String accountId);

    WxPayAccountMeta getAccount(String accountId);
}
