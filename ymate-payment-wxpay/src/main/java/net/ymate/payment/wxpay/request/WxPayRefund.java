/*
 * Copyright 2007-2016 the original author or authors.
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
package net.ymate.payment.wxpay.request;

import net.ymate.framework.commons.IHttpResponse;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.base.*;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 申请退款
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/26 下午10:12
 * @version 1.0
 */
public class WxPayRefund extends WxPayBaseRequest<WxPayRefund.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 总金额
     */
    private Integer totalFee;

    /**
     * 退款金额
     */
    private Integer refundFee;

    /**
     * 货币种类
     */
    private String refundFeeType;

    /**
     * 退款资金来源
     */
    private String refundAccount;

    public WxPayRefund(WxPayAccountMeta accountMeta, String transactionId, String outTradeNo, String outRefundNo, Integer totalFee, Integer refundFee) {
        super(accountMeta);
        this.transactionId = transactionId;
        this.outTradeNo = outTradeNo;
        this.outRefundNo = outRefundNo;
        this.totalFee = totalFee;
        this.refundFee = refundFee;
    }

    public String appId() {
        return appId;
    }

    public WxPayRefund appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String deviceInfo() {
        return deviceInfo;
    }

    public WxPayRefund deviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String transactionId() {
        return transactionId;
    }

    public WxPayRefund transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayRefund outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String outRefundNo() {
        return outRefundNo;
    }

    public WxPayRefund outRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
        return this;
    }

    public Integer totalFee() {
        return totalFee;
    }

    public WxPayRefund totalFee(Integer totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public Integer refundFee() {
        return refundFee;
    }

    public WxPayRefund refundFee(Integer refundFee) {
        this.refundFee = refundFee;
        return this;
    }

    public String refundFeeType() {
        return refundFeeType;
    }

    public WxPayRefund refundFeeType(String refundFeeType) {
        this.refundFeeType = refundFeeType;
        return this;
    }

    public String refundAccount() {
        return refundAccount;
    }

    public WxPayRefund refundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.transactionId)
                && StringUtils.isBlank(this.outTradeNo)) {
            throw new NullArgumentException("transaction_id or out_trade_no");
        }
        if (StringUtils.isBlank(this.outRefundNo)) {
            throw new NullArgumentException("out_refund_no");
        }
        if (this.totalFee == null || this.totalFee <= 0) {
            throw new NullArgumentException("total_fee");
        }
        if (this.refundFee == null || this.refundFee <= 0) {
            throw new NullArgumentException("refund_fee");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put(IWxPay.Const.DEVICE_INFO, deviceInfo);
        _params.put("transaction_id", transactionId);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        _params.put("out_refund_no", outRefundNo);
        _params.put("total_fee", totalFee);
        _params.put("refund_fee", refundFee);
        _params.put("refund_fee_type", refundFeeType);
        _params.put("refund_account", refundAccount);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "https://api.mch.weixin.qq.com/secapi/pay/refund";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 申请退款响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 公众账号ID
         */
        private String appId;

        /**
         * 设备号
         */
        private String deviceInfo;

        /**
         * 微信订单号
         */
        private String transactionId;

        /**
         * 商户订单号
         */
        private String outTradeNo;

        /**
         * 订单金额
         */
        private Integer totalFee;

        /**
         * 应结订单金额
         */
        private Integer settlementTotalFee;

        /**
         * 订单金额货币种类
         */
        private String feeType;

        /**
         * 现金支付金额
         */
        private Integer cashFee;

        /**
         * 现金退款金额
         */
        private Integer cashRefundFee;

        private WxPayRefundData refundData;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
            this.deviceInfo = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.DEVICE_INFO)).toStringValue();
            this.transactionId = BlurObject.bind(this.getResponseParams().get("transaction_id")).toStringValue();
            this.outTradeNo = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.OUT_TRADE_NO)).toStringValue();
            this.totalFee = BlurObject.bind(this.getResponseParams().get("total_fee")).toIntValue();
            this.settlementTotalFee = BlurObject.bind(this.getResponseParams().get("settlement_total_fee")).toIntValue();
            this.feeType = BlurObject.bind(this.getResponseParams().get("fee_type")).toStringValue();
            this.cashFee = BlurObject.bind(this.getResponseParams().get("cash_type")).toIntValue();
            this.cashRefundFee = BlurObject.bind(this.getResponseParams().get("cash_refund_fee")).toIntValue();
            //
            int i = 0;
            this.refundData = new WxPayRefundData();
            this.refundData.setOutRefundNo(BlurObject.bind(this.getResponseParams().get("out_refund_no_" + i)).toStringValue());
            this.refundData.setRefundId(BlurObject.bind(this.getResponseParams().get("refund_id_" + i)).toStringValue());
            this.refundData.setRefundChannel(BlurObject.bind(this.getResponseParams().get("refund_channel_" + i)).toStringValue());
            this.refundData.setRefundFee(BlurObject.bind(this.getResponseParams().get("refund_fee_" + i)).toIntValue());
            this.refundData.setSettlementRefundFee(BlurObject.bind(this.getResponseParams().get("settlement_refund_fee_" + i)).toIntValue());
            this.refundData.setCouponType(BlurObject.bind(this.getResponseParams().get("coupon_type_" + i)).toIntValue());
            this.refundData.setCouponRefundCount(BlurObject.bind(this.getResponseParams().get("coupon_refund_count_" + i)).toIntValue());
            this.refundData.setCouponRefundFee(BlurObject.bind(this.getResponseParams().get("coupon_refund_fee_" + i)).toIntValue());
            this.refundData.setRefundStatus(BlurObject.bind(this.getResponseParams().get("refund_status_" + i)).toStringValue());
            this.refundData.setRefundRecvAccount(BlurObject.bind(this.getResponseParams().get("refund_recv_accout_" + i)).toStringValue());
            //
            for (int y = 0; y < this.refundData.getCouponRefundCount(); y++) {
                WxPayCouponData _couponData = new WxPayCouponData();
                _couponData.setType(this.refundData.getCouponType());
                _couponData.setCouponId(BlurObject.bind(this.getResponseParams().get("coupon_refund_id_" + i + "_" + y)).toStringValue());
                _couponData.setCouponFee(BlurObject.bind(this.getResponseParams().get("coupon_refund_fee_" + i + "_" + y)).toIntValue());
                this.refundData.getCouponDatas().add(_couponData);
            }
        }

        public String appId() {
            return appId;
        }

        public String deviceInfo() {
            return deviceInfo;
        }

        public String transactionId() {
            return transactionId;
        }

        public String outTradeNo() {
            return outTradeNo;
        }

        public Integer totalFee() {
            return totalFee;
        }

        public Integer settlementTotalFee() {
            return settlementTotalFee;
        }

        public String feeType() {
            return feeType;
        }

        public Integer cashFee() {
            return cashFee;
        }

        public Integer cashRefundFee() {
            return cashRefundFee;
        }

        public WxPayRefundData refundData() {
            return refundData;
        }
    }
}
