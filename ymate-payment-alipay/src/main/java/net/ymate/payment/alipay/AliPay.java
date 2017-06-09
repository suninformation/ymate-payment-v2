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

import net.ymate.payment.alipay.impl.DefaultModuleCfg;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
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
            // Here to write your code
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            // Here to write your code
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
