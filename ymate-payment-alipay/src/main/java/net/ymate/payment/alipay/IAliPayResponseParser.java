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

import net.ymate.framework.commons.IHttpResponse;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/8 下午4:44
 * @version 1.0
 */
public interface IAliPayResponseParser<RESPONSE extends IAliPayResponse> {

    /**
     * 什么也不做的响应数据分析器
     */
    class NOTHINE implements IAliPayResponseParser<IAliPayResponse.NOTHINE> {
        public IAliPayResponse.NOTHINE parserResponse(IHttpResponse httpResponse) throws Exception {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * @param httpResponse HTTP响应结果对象
     * @return 分析响应报文并返回接口回应结果对象
     * @throws Exception 可能产生的异常
     */
    RESPONSE parserResponse(IHttpResponse httpResponse) throws Exception;
}
