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

import com.alibaba.fastjson.annotation.JSONField;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.payment.alipay.base.AliPayBaseRequest;
import net.ymate.payment.alipay.base.AliPayBaseResponse;
import net.ymate.payment.alipay.base.AliPayBaseResponseParser;
import net.ymate.payment.alipay.data.TradeCloseData;

/**
 * 统一收单交易关闭
 * <p>
 * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/12 上午2:14
 * @version 1.0
 */
public class AliPayTradeClose extends AliPayBaseRequest<TradeCloseData, AliPayTradeClose.Response> {

    private static final String METHOD_NAME = "alipay.trade.close";

    public AliPayTradeClose(AliPayAccountMeta accountMeta, TradeCloseData bizContent) {
        super(accountMeta, METHOD_NAME, "1.0", bizContent, false, new AliPayBaseResponseParser<AliPayTradeClose.Response>(METHOD_NAME));
    }

    /**
     * 交易关闭接口响应对象
     */
    public static class Response extends AliPayBaseResponse {

        @JSONField(name = "trade_no")
        private String tradeNo;

        @JSONField(name = "out_trade_no")
        private String outTradeNo;

        public boolean successful() {
            return true;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }
    }
}
