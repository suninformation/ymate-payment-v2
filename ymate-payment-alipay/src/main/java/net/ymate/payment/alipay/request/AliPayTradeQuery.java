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
import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.base.AliPayAccountMeta;
import net.ymate.payment.alipay.base.AliPayBaseRequest;
import net.ymate.payment.alipay.base.AliPayBaseResponse;
import net.ymate.payment.alipay.base.AliPayBaseResponseParser;
import net.ymate.payment.alipay.data.FundItemData;
import net.ymate.payment.alipay.data.TradeQueryData;
import net.ymate.payment.alipay.data.VoucherItemData;
import org.apache.commons.lang.StringUtils;

/**
 * 统一收单线下交易查询
 * <p>
 * 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
 * <p>
 * 需要调用查询接口的情况：
 * <p>
 * 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知;
 * 调用支付接口后，返回系统错误或未知交易状态情况;
 * 调用alipay.trade.pay返回INPROCESS的状态;
 * 调用alipay.trade.cancel之前需确认支付状态;
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/12 上午1:40
 * @version 1.0
 */
public class AliPayTradeQuery extends AliPayBaseRequest<TradeQueryData, AliPayTradeQuery.Response> {

    private static final String METHOD_NAME = "alipay.trade.query";

    public AliPayTradeQuery(AliPayAccountMeta accountMeta, TradeQueryData bizContent) {
        super(accountMeta, METHOD_NAME, "1.0", bizContent, false, false, new AliPayBaseResponseParser<Response>(Response.class, METHOD_NAME));
    }

    /**
     * 线下交易查询接口响应对象
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

        @JSONField(name = "trade_status")
        private String tradeStatus;

        @JSONField(name = "total_amount")
        private String totalAmount;

        @JSONField(name = "receipt_amount")
        private String receiptAmount;

        @JSONField(name = "buyer_pay_amount")
        private String buyerPayAmount;

        @JSONField(name = "point_amount")
        private String pointAmount;

        @JSONField(name = "invoice_amount")
        private String invoiceAmount;

        @JSONField(name = "send_pay_date")
        private String sendPayDate;

        @JSONField(name = "alipay_store_id")
        private String alipayStoreId;

        @JSONField(name = "store_id")
        private String storeId;

        @JSONField(name = "store_name")
        private String storeName;

        @JSONField(name = "terminal_id")
        private String terminalId;

        @JSONField(name = "buyer_user_id")
        private String buyerUserId;

        @JSONField(name = "fund_bill_list")
        private FundItemData[] fundBills;

        @JSONField(name = "discount_goods_detail")
        private String discountGoodsDetail;

        @JSONField(name = "industry_sepc_detail")
        private String industrySepcDetail;

        @JSONField(name = "voucher_detail_list")
        private VoucherItemData[] voucherDatails;

        @Override
        public boolean successful() {
            return StringUtils.equalsIgnoreCase(IAliPay.TradeStatus.TRADE_SUCCESS.name(), tradeStatus);
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

        public String getTradeStatus() {
            return tradeStatus;
        }

        public void setTradeStatus(String tradeStatus) {
            this.tradeStatus = tradeStatus;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getReceiptAmount() {
            return receiptAmount;
        }

        public void setReceiptAmount(String receiptAmount) {
            this.receiptAmount = receiptAmount;
        }

        public String getBuyerPayAmount() {
            return buyerPayAmount;
        }

        public void setBuyerPayAmount(String buyerPayAmount) {
            this.buyerPayAmount = buyerPayAmount;
        }

        public String getPointAmount() {
            return pointAmount;
        }

        public void setPointAmount(String pointAmount) {
            this.pointAmount = pointAmount;
        }

        public String getInvoiceAmount() {
            return invoiceAmount;
        }

        public void setInvoiceAmount(String invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public String getSendPayDate() {
            return sendPayDate;
        }

        public void setSendPayDate(String sendPayDate) {
            this.sendPayDate = sendPayDate;
        }

        public String getAlipayStoreId() {
            return alipayStoreId;
        }

        public void setAlipayStoreId(String alipayStoreId) {
            this.alipayStoreId = alipayStoreId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public String getBuyerUserId() {
            return buyerUserId;
        }

        public void setBuyerUserId(String buyerUserId) {
            this.buyerUserId = buyerUserId;
        }

        public FundItemData[] getFundBills() {
            return fundBills;
        }

        public void setFundBills(FundItemData[] fundBills) {
            this.fundBills = fundBills;
        }

        public String getDiscountGoodsDetail() {
            return discountGoodsDetail;
        }

        public void setDiscountGoodsDetail(String discountGoodsDetail) {
            this.discountGoodsDetail = discountGoodsDetail;
        }

        public String getIndustrySepcDetail() {
            return industrySepcDetail;
        }

        public void setIndustrySepcDetail(String industrySepcDetail) {
            this.industrySepcDetail = industrySepcDetail;
        }

        public VoucherItemData[] getVoucherDatails() {
            return voucherDatails;
        }

        public void setVoucherDatails(VoucherItemData[] voucherDatails) {
            this.voucherDatails = voucherDatails;
        }
    }
}
