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
package net.ymate.payment.alipay.intercept;

import net.ymate.framework.commons.ParamUtils;
import net.ymate.payment.alipay.AliPay;
import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.payment.alipay.support.SignatureUtils;
import net.ymate.platform.core.beans.intercept.IInterceptor;
import net.ymate.platform.core.beans.intercept.InterceptContext;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 验签拦截器
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/13 下午4:05
 * @version 1.0
 */
public class AliPaySignatureCheckInterceptor implements IInterceptor {

    private static final Log _LOG = LogFactory.getLog(AliPaySignatureCheckInterceptor.class);

    @Override
    public Object intercept(InterceptContext context) throws Exception {
        if (!AliPay.get().getModuleCfg().isSignCheckDisabled()) {
            if (context.getDirection() == Direction.BEFORE) {
                return __doSignCheck(context);
            }
        }
        return null;
    }

    private IView __doSignCheck(InterceptContext context) throws Exception {
        String _accountId = WebContext.getRequest().getParameter(IAliPay.Const.APP_ID);
        String _sign = WebContext.getRequest().getParameter(IAliPay.Const.SIGN);
        //
        if (StringUtils.isNotBlank(_accountId) && StringUtils.isNotBlank(_sign)) {
            AliPayAccountMeta _meta = AliPay.get().getModuleCfg().getAccountProvider().getAccount(_accountId);
            if (_meta != null) {
                Map<String, Object> _params = new HashMap<String, Object>(WebContext.getContext().getParameters());
                _params.remove(IAliPay.Const.SIGN_TYPE);
                _params.remove(IAliPay.Const.SIGN);
                //
                String _paramsStr = ParamUtils.buildQueryParamStr(_params, false, _meta.getCharset());
                if (SignatureUtils.verify(_paramsStr, _sign, _meta.getPublicKey(), _meta.getCharset(), _meta.getSignType())) {
                    return null;
                } else if (_LOG.isDebugEnabled()) {
                    _LOG.debug("Signature verification failed: " + _paramsStr);
                }
            }
        }
        return HttpStatusView.METHOD_NOT_ALLOWED;
    }
}
