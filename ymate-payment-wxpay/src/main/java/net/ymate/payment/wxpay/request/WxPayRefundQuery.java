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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询退款
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/26 下午11:26
 * @version 1.0
 */
public class WxPayRefundQuery extends WxPayBaseRequest<WxPayRefundQuery.Response> {

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
     * 微信退款单号
     */
    private String refundId;

    public WxPayRefundQuery(WxPayAccountMeta accountMeta) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
    }

    public WxPayRefundQuery(WxPayAccountMeta accountMeta, String transactionId, String outTradeNo, String outRefundNo, String refundId) {
        super(accountMeta);
        this.transactionId = transactionId;
        this.outTradeNo = outTradeNo;
        this.outRefundNo = outRefundNo;
        this.refundId = refundId;
    }

    public String appId() {
        return appId;
    }

    public WxPayRefundQuery appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String deviceInfo() {
        return deviceInfo;
    }

    public WxPayRefundQuery deviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String transactionId() {
        return transactionId;
    }

    public WxPayRefundQuery transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayRefundQuery outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String outRefundNo() {
        return outRefundNo;
    }

    public WxPayRefundQuery outRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
        return this;
    }

    public String refundId() {
        return refundId;
    }

    public WxPayRefundQuery refundId(String refundId) {
        this.refundId = refundId;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.transactionId)
                && StringUtils.isBlank(this.outTradeNo)
                && StringUtils.isBlank(this.outRefundNo)
                && StringUtils.isBlank(this.refundId)) {
            throw new NullArgumentException("");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put(IWxPay.Const.DEVICE_INFO, deviceInfo);
        _params.put("transaction_id", transactionId);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        _params.put("out_refund_no", outRefundNo);
        _params.put("refund_id", refundId);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "pay/refundquery";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 查询退款响应
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
         * 货币种类
         */
        private String feeType;

        /**
         * 现金支付金额
         */
        private Integer cashFee;

        /**
         * 退款笔数
         */
        private Integer refundCount;

        private List<WxPayRefundData> refundDatas;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.refundDatas = new ArrayList<WxPayRefundData>();
            this.appId = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.APP_ID)).toStringValue();
            this.deviceInfo = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.DEVICE_INFO)).toStringValue();
            this.transactionId = BlurObject.bind(this.getResponseParams().get("transaction_id")).toStringValue();
            this.outTradeNo = BlurObject.bind(this.getResponseParams().get(IWxPay.Const.OUT_TRADE_NO)).toStringValue();
            this.totalFee = BlurObject.bind(this.getResponseParams().get("total_fee")).toIntValue();
            this.settlementTotalFee = BlurObject.bind(this.getResponseParams().get("settlement_total_fee")).toIntValue();
            this.feeType = BlurObject.bind(this.getResponseParams().get("fee_type")).toStringValue();
            this.cashFee = BlurObject.bind(this.getResponseParams().get("cash_type")).toIntValue();
            this.refundCount = BlurObject.bind(this.getResponseParams().get("refund_count")).toIntValue();
            //
            for (int i = 0; i < this.refundCount; i++) {
                WxPayRefundData _data = new WxPayRefundData();
                _data.setOutRefundNo(BlurObject.bind(this.getResponseParams().get("out_refund_no_" + i)).toStringValue());
                _data.setRefundId(BlurObject.bind(this.getResponseParams().get("refund_id_" + i)).toStringValue());
                _data.setRefundChannel(BlurObject.bind(this.getResponseParams().get("refund_channel_" + i)).toStringValue());
                _data.setRefundFee(BlurObject.bind(this.getResponseParams().get("refund_fee_" + i)).toIntValue());
                _data.setSettlementRefundFee(BlurObject.bind(this.getResponseParams().get("settlement_refund_fee_" + i)).toIntValue());
                _data.setCouponType(BlurObject.bind(this.getResponseParams().get("coupon_type_" + i)).toIntValue());
                _data.setCouponRefundCount(BlurObject.bind(this.getResponseParams().get("coupon_refund_count_" + i)).toIntValue());
                _data.setCouponRefundFee(BlurObject.bind(this.getResponseParams().get("coupon_refund_fee_" + i)).toIntValue());
                _data.setRefundStatus(BlurObject.bind(this.getResponseParams().get("refund_status_" + i)).toStringValue());
                _data.setRefundAccount(BlurObject.bind(this.getResponseParams().get("refund_account_" + i)).toStringValue());
                _data.setRefundRecvAccount(BlurObject.bind(this.getResponseParams().get("refund_recv_account_" + i)).toStringValue());
                _data.setRefundSuccessTime(BlurObject.bind(this.getResponseParams().get("refund_success_time_" + i)).toStringValue());
                //
                for (int y = 0; y < _data.getCouponRefundCount(); y++) {
                    WxPayCouponData _couponData = new WxPayCouponData();
                    _couponData.setType(_data.getCouponType());
                    _couponData.setCouponId(BlurObject.bind(this.getResponseParams().get("coupon_refund_id_" + i + "_" + y)).toStringValue());
                    _couponData.setCouponFee(BlurObject.bind(this.getResponseParams().get("coupon_refund_fee_" + i + "_" + y)).toIntValue());
                    _data.getCouponDatas().add(_couponData);
                }
                refundDatas.add(_data);
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

        public Integer refundCount() {
            return refundCount;
        }

        public List<WxPayRefundData> refundDatas() {
            return refundDatas;
        }
    }
}
