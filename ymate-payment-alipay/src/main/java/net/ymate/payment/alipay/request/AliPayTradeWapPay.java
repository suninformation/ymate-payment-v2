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
package net.ymate.payment.alipay.request;

import net.ymate.payment.alipay.IAliPayResponse;
import net.ymate.payment.alipay.IAliPayResponseParser;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.payment.alipay.base.AliPayBaseRequest;
import net.ymate.payment.alipay.data.TradeWapPayData;

/**
 * WAP场景下单并支付
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/8 下午4:57
 * @version 1.0
 */
public class AliPayTradeWapPay extends AliPayBaseRequest<TradeWapPayData, IAliPayResponse.NOTHING> {

    public AliPayTradeWapPay(AliPayAccountMeta accountMeta, TradeWapPayData bizContent) {
        super(accountMeta, "alipay.trade.wap.pay", "1.0", bizContent, true, true, new IAliPayResponseParser.NOTHING());
    }
}
