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
package net.ymate.payment.alipay.impl;

import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.IAliPayAccountProvider;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/8 上午9:32
 * @version 1.0
 */
public class DefaultAliPayAccountProvider implements IAliPayAccountProvider {

    private static Map<String, AliPayAccountMeta> __CACHES = new ConcurrentHashMap<String, AliPayAccountMeta>();

    private IAliPay __owner;

    public void init(IAliPay owner) throws Exception {
        __owner = owner;
    }

    public void destroy() throws Exception {
    }

    public void registerAccount(AliPayAccountMeta accountMeta) {
        __CACHES.put(accountMeta.getAppId(), accountMeta);
    }

    public AliPayAccountMeta unregisterAccount(String accountId) {
        return __CACHES.remove(accountId);
    }

    public Collection<String> getAccountIds() {
        return __CACHES.keySet();
    }

    public boolean hasAccount(String accountId) {
        return __CACHES.containsKey(accountId);
    }

    public AliPayAccountMeta getAccount(String accountId) {
        if (StringUtils.equalsIgnoreCase(accountId, "default")) {
            return __CACHES.get(__owner.getModuleCfg().getDefaultAccountId());
        }
        return __CACHES.get(accountId);
    }
}
