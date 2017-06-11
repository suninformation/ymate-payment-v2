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
package net.ymate.payment.alipay.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.ymate.payment.alipay.AliPayException;
import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.IAliPayResponse;
import net.ymate.payment.alipay.IAliPayResponseParser;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/11 下午10:40
 * @version 1.0
 */
public class AliPayBaseResponseParser<RESPONSE extends IAliPayResponse> implements IAliPayResponseParser<RESPONSE> {

    private static final Log _LOG = LogFactory.getLog(AliPayBaseResponseParser.class);

    private String __methodName;

    private Class<? extends RESPONSE> __class;

    public AliPayBaseResponseParser(String methodName) {
        if (StringUtils.isBlank(methodName)) {
            throw new NullArgumentException("methodName");
        }
        __methodName = StringUtils.replace(methodName, ".", "_") + IAliPay.Const.RESPONSE_SUFFIX;
    }

    public RESPONSE parserResponse(String responseContent) throws Exception {
        JSONObject _result = JSON.parseObject(responseContent);
        String _sign = _result.getString(IAliPay.Const.SIGN);
        RESPONSE _data = _result.getObject(__methodName, __class);
        if (!StringUtils.equals(_data.getCode(), "10000")) {
            if (_LOG.isDebugEnabled()) {
                _LOG.debug("ResponseContent: " + responseContent);
            }
            if (StringUtils.isNotBlank(_data.getSubCode()) && AliPayException.__ERRORS.containsKey(_data.getSubCode())) {
                throw new AliPayException(_data.getSubCode(), _data.getSubMsg());
            } else {
                throw new AliPayException(_data.getCode(), _data.getMsg());
            }
        } else {
            _data.setSign(_sign);
        }
        return _data;
    }
}
