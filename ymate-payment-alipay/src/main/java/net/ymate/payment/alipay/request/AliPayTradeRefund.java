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
import net.ymate.payment.alipay.data.FundItemData;
import net.ymate.payment.alipay.data.TradeRefundData;
import org.apache.commons.lang.StringUtils;

/**
 * 统一收单交易退款
 * <p>
 * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，
 * 支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
 * 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款
 * 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
 * 一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/11 下午9:40
 * @version 1.0
 */
public class AliPayTradeRefund extends AliPayBaseRequest<TradeRefundData, AliPayTradeRefund.Response> {

    private static final String METHOD_NAME = "alipay.trade.refund";

    public AliPayTradeRefund(AliPayAccountMeta accountMeta, TradeRefundData bizContent) {
        super(accountMeta, METHOD_NAME, "1.0", bizContent, false, new AliPayBaseResponseParser<Response>(METHOD_NAME));
    }

    /**
     * 交易退款接口响应对象
     */
    public static class Response extends AliPayBaseResponse {

        @JSONField(name = "trade_no")
        private String tradeNo;

        @JSONField(name = "out_trade_no")
        private String outTradeNo;

        @JSONField(name = "open_id")
        private String openId;

        @JSONField(name = "buyer_logon_id")
        private String buyerLogonId;

        @JSONField(name = "fund_change")
        private String fundChange;

        @JSONField(name = "refund_fee")
        private String refundFee;

        @JSONField(name = "gmt_refund_pay")
        private String gmtRefundPay;

        @JSONField(name = "store_name")
        private String storeName;

        @JSONField(name = "buyer_user_id")
        private String buyerUserId;

        @JSONField(name = "refund_detail_item_list")
        private FundItemData[] detailItems;

        public boolean successful() {
            return StringUtils.equalsIgnoreCase(fundChange, "Y");
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

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getBuyerLogonId() {
            return buyerLogonId;
        }

        public void setBuyerLogonId(String buyerLogonId) {
            this.buyerLogonId = buyerLogonId;
        }

        public String getFundChange() {
            return fundChange;
        }

        public void setFundChange(String fundChange) {
            this.fundChange = fundChange;
        }

        public String getRefundFee() {
            return refundFee;
        }

        public void setRefundFee(String refundFee) {
            this.refundFee = refundFee;
        }

        public String getGmtRefundPay() {
            return gmtRefundPay;
        }

        public void setGmtRefundPay(String gmtRefundPay) {
            this.gmtRefundPay = gmtRefundPay;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getBuyerUserId() {
            return buyerUserId;
        }

        public void setBuyerUserId(String buyerUserId) {
            this.buyerUserId = buyerUserId;
        }

        public FundItemData[] getDetailItems() {
            return detailItems;
        }

        public void setDetailItems(FundItemData[] detailItems) {
            this.detailItems = detailItems;
        }
    }
}
