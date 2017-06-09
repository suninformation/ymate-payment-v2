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

import com.alibaba.fastjson.annotation.JSONField;
import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * 支付宝异步通知对象
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/9 上午9:59
 * @version 1.0
 */
public class AliPayBaseNotify {

    // ----- 异步通知公共参数 -----

    /**
     * 通知的发送时间。格式为yyyy-MM-dd HH:mm:ss
     */
    @RequestParam("notify_time")
    @JSONField(name = "notify_time")
    private String notifyTime;

    /**
     * 通知的类型
     */
    @RequestParam("notify_type")
    @JSONField(name = "notify_type")
    private String notifyType;

    /**
     * 通知校验ID
     */
    @RequestParam("notify_id")
    @JSONField(name = "notify_id")
    private String notifyId;

    /**
     * 编码格式
     */
    private String charset;

    /**
     * 调用的接口版本，固定为：1.0
     */
    private String version;

    /**
     * 签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    @RequestParam("sign_type")
    @JSONField(name = "sign_type")
    private String signType;

    /**
     * 签名
     */
    private String sign;

    /**
     * 授权方的appid，由于本接口暂不开放第三方应用授权，因此auth_app_id=app_id
     */
    @RequestParam("auth_app_id")
    @JSONField(name = "auth_app_id")
    private String authAppId;

    // ----- 业务参数 -----

    /**
     * 支付宝分配给开发者的应用Id
     */
    @RequestParam("app_id")
    @JSONField(name = "app_id")
    private String appId;

    /**
     * 支付宝交易凭证号
     */
    @RequestParam("trade_no")
    @JSONField(name = "trade_no")
    private String tradeNo;

    /**
     * 原支付请求的商户订单号
     */
    @RequestParam("out_trade_no")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商户业务ID，主要是退款通知中返回退款申请的流水号
     */
    @RequestParam("out_biz_no")
    @JSONField(name = "out_biz_no")
    private String outBizNo;

    /**
     * 买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
     */
    @RequestParam("buyer_id")
    @JSONField(name = "buyer_id")
    private String buyerId;

    /**
     * 卖家支付宝用户号
     */
    @RequestParam("seller_id")
    @JSONField(name = "seller_id")
    private String sellerId;

    /**
     * 交易目前所处的状态
     */
    @RequestParam("trade_status")
    @JSONField(name = "trade_status")
    private String tradeStatus;

    /**
     * 本次交易支付的订单金额，单位为人民币（元），精确到小数点后2位
     */
    @RequestParam("total_amount")
    @JSONField(name = "total_amount")
    private String totalAmount;

    /**
     * 商家在交易中实际收到的款项，单位为元，精确到小数点后2位
     */
    @RequestParam("receipt_amount")
    @JSONField(name = "receipt_amount")
    private String receiptAmount;

    /**
     * 用户在交易中支付的可开发票的金额，单位为元，精确到小数点后2位
     */
    @RequestParam("invoice_amount")
    @JSONField(name = "invoice_amount")
    private String invoiceAmount;

    /**
     * 用户在交易中支付的金额，单位为元，精确到小数点后2位
     */
    @RequestParam("buyer_pay_amount")
    @JSONField(name = "buyer_pay_amount")
    private String buyerPayAmount;

    /**
     * 使用集分宝支付的金额，单位为元，精确到小数点后2位
     */
    @RequestParam("point_amount")
    @JSONField(name = "point_amount")
    private String pointAmount;

    /**
     * 退款通知中，返回总退款金额，单位为元，精确到小数点后2位
     */
    @RequestParam("refund_fee")
    @JSONField(name = "refund_fee")
    private String refundFee;

    /**
     * 商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来
     */
    private String subject;

    /**
     * 该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来
     */
    private String body;

    /**
     * 该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss
     */
    @RequestParam("gmt_create")
    @JSONField(name = "gmt_create")
    private String gmtCreate;

    /**
     * 该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss
     */
    @RequestParam("gmt_payment")
    @JSONField(name = "gmt_payment")
    private String gmtPayment;

    /**
     * 该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S
     */
    @RequestParam("gmt_refund")
    @JSONField(name = "gmt_refund")
    private String gmtRefund;

    /**
     * 该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss
     */
    @RequestParam("gmt_close")
    @JSONField(name = "gmt_close")
    private String gmtClose;

    /**
     * 支付成功的各个渠道金额信息
     */
    @RequestParam("fund_bill_list")
    @JSONField(name = "fund_bill_list")
    private String fundBillList;

    /**
     * 本交易支付时所使用的所有优惠券信息
     */
    @RequestParam("voucher_detail_list")
    @JSONField(name = "voucher_detail_list")
    private String voucherDetailList;

    /**
     * 公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
     */
    @RequestParam("passback_params")
    @JSONField(name = "passback_params")
    private String passbackParams;

    // ----- 对于手机网站支付产生的交易通知会存在以下参数:

    /**
     * 买家支付宝账号
     */
    @RequestParam("buyer_logon_id")
    @JSONField(name = "buyer_logon_id")
    private String buyerLogonId;

    /**
     * 卖家支付宝账号
     */
    @RequestParam("seller_email")
    @JSONField(name = "seller_email")
    private String sellerEmail;


    // ----------


    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAuthAppId() {
        return authAppId;
    }

    public void setAuthAppId(String authAppId) {
        this.authAppId = authAppId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(String gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public String getGmtRefund() {
        return gmtRefund;
    }

    public void setGmtRefund(String gmtRefund) {
        this.gmtRefund = gmtRefund;
    }

    public String getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(String gmtClose) {
        this.gmtClose = gmtClose;
    }

    public String getFundBillList() {
        return fundBillList;
    }

    public void setFundBillList(String fundBillList) {
        this.fundBillList = fundBillList;
    }

    public String getVoucherDetailList() {
        return voucherDetailList;
    }

    public void setVoucherDetailList(String voucherDetailList) {
        this.voucherDetailList = voucherDetailList;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }
}
