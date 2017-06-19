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
package net.ymate.payment.wxpay.support;

import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.platform.webmvc.IRequestProcessor;
import net.ymate.platform.webmvc.IWebMvc;
import net.ymate.platform.webmvc.RequestMeta;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/19 下午3:09
 * @version 1.0
 */
public class WxPayRequestProcessor implements IRequestProcessor {

    public Map<String, Object> processRequestParams(IWebMvc owner, RequestMeta requestMeta) throws Exception {
        Map<String, Object> _params = new HashMap<String, Object>();
        //
        if (!requestMeta.getMethodParamNames().isEmpty()) {
            String _protocol = IOUtils.toString(WebContext.getRequest().getInputStream(), owner.getModuleCfg().getDefaultCharsetEncoding());
            if (StringUtils.isNotBlank(_protocol)) {
                WxPayNotifyResponse _response = WxPayNotifyResponse.bind(_protocol);
                _params.put(requestMeta.getMethodParamNames().get(0), _response);
            }
        }
        return _params;
    }
}
