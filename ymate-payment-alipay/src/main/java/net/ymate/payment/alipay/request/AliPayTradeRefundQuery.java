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
import net.ymate.payment.alipay.data.TradeRefundQueryData;
import org.apache.commons.lang.StringUtils;

/**
 * 统一收单交易退款查询
 * <p>
 * 商户可使用该接口查询自已通过alipay.trade.refund提交的退款请求是否执行成功。
 * 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。
 * 如果该接口返回了查询数据，则代表退款成功，如果没有查询到则代表未退款成功，可以调用退款接口进行重试。
 * 重试时请务必保证退款请求号一致
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/12 上午12:31
 * @version 1.0
 */
public class AliPayTradeRefundQuery extends AliPayBaseRequest<TradeRefundQueryData, AliPayTradeRefundQuery.Response> {

    private static final String METHOD_NAME = "alipay.trade.fastpay.refund.query";

    public AliPayTradeRefundQuery(AliPayAccountMeta accountMeta, TradeRefundQueryData bizContent) {
        super(accountMeta, METHOD_NAME, "1.0", bizContent, false, false, new AliPayBaseResponseParser<Response>(Response.class, METHOD_NAME));
    }

    /**
     * 交易退款查询接口响应对象
     */
    public static class Response extends AliPayBaseResponse {

        /**
         * 支付宝交易号
         */
        @JSONField(name = "trade_no")
        private String tradeNo;

        /**
         * 创建交易传入的商户订单号
         */
        @JSONField(name = "out_trade_no")
        private String outTradeNo;

        /**
         * 本笔退款对应的退款请求号
         */
        @JSONField(name = "out_request_no")
        private String outRequestNo;

        /**
         * 发起退款时传入的退款原因
         */
        @JSONField(name = "refund_reason")
        private String refundReason;

        /**
         * 本次退款请求对应的退款金额
         */
        @JSONField(name = "refund_amount")
        private String refundAmount;

        /**
         * 该笔退款所对应的交易的订单金额
         */
        @JSONField(name = "total_amount")
        private String totalAmount;

        public boolean successful() {
            return StringUtils.isNotBlank(outRequestNo);
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

        public String getOutRequestNo() {
            return outRequestNo;
        }

        public void setOutRequestNo(String outRequestNo) {
            this.outRequestNo = outRequestNo;
        }

        public String getRefundReason() {
            return refundReason;
        }

        public void setRefundReason(String refundReason) {
            this.refundReason = refundReason;
        }

        public String getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(String refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
