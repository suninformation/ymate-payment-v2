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
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayBaseResponse;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询红包记录
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/27 上午12:57
 * @version 1.0
 */
public class WxPayRedPackInfo extends WxPayBaseRequest<WxPayRedPackInfo.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 商户订单号
     */
    private String mchBillNo;

    /**
     * 订单类型
     */
    private String billType;

    public WxPayRedPackInfo(WxPayAccountMeta accountMeta, String mchBillNo) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.mchBillNo = mchBillNo;
    }

    public String appId() {
        return appId;
    }

    public WxPayRedPackInfo appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String mchBillNo() {
        return mchBillNo;
    }

    public WxPayRedPackInfo mchBillNo(String mchBillNo) {
        this.mchBillNo = mchBillNo;
        return this;
    }

    public String billType() {
        return billType;
    }

    public WxPayRedPackInfo billType(String billType) {
        this.billType = billType;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.mchBillNo)) {
            throw new NullArgumentException("mch_billno");
        }
        if (StringUtils.isBlank(this.billType)) {
            this.billType = "MCHT";
        }
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put("mch_billno", mchBillNo);
        _params.put("bill_type", billType);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "mmpaymkttransfers/gethbinfo";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    public static class Response extends WxPayBaseResponse {

        /**
         * 商户订单号
         */
        private String mchBillNo;

        /**
         * 红包单号
         */
        private String detailId;

        /**
         * 红包状态
         */
        private String status;

        /**
         * 发放类型
         */
        private String sendType;

        /**
         * 红包类型
         */
        private String hbType;

        /**
         * 红包个数
         */
        private Integer totalNum;

        /**
         * 红包金额
         */
        private Integer totalAmount;

        /**
         * 失败原因
         */
        private String reason;

        /**
         * 红包发送时间
         */
        private String sendTime;

        /**
         * 红包退款时间
         */
        private String refundTime;

        /**
         * 红包退款金额
         */
        private Integer refundAmount;

        /**
         * 祝福语
         */
        private String wishing;

        /**
         * 活动描述
         */
        private String remark;

        /**
         * 活动名称
         */
        private String actName;

        /**
         * 裂变红包领取列表
         */
        private List<HbInfo> hbList;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.hbList = new ArrayList<HbInfo>();
            this.mchBillNo = BlurObject.bind(this.getResponseParams().get("mch_billno")).toStringValue();
            this.detailId = BlurObject.bind(this.getResponseParams().get("detail_id")).toStringValue();
            this.status = BlurObject.bind(this.getResponseParams().get("status")).toStringValue();
            this.sendType = BlurObject.bind(this.getResponseParams().get("send_type")).toStringValue();
            this.hbType = BlurObject.bind(this.getResponseParams().get("hb_type")).toStringValue();
            this.totalNum = BlurObject.bind(this.getResponseParams().get("total_num")).toIntValue();
            this.totalAmount = BlurObject.bind(this.getResponseParams().get("total_amount")).toIntValue();
            this.reason = BlurObject.bind(this.getResponseParams().get("reason")).toStringValue();
            this.sendTime = BlurObject.bind(this.getResponseParams().get("send_time")).toStringValue();
            this.refundTime = BlurObject.bind(this.getResponseParams().get("refund_time")).toStringValue();
            this.refundAmount = BlurObject.bind(this.getResponseParams().get("refund_amount")).toIntValue();
            this.wishing = BlurObject.bind(this.getResponseParams().get("wishing")).toStringValue();
            this.remark = BlurObject.bind(this.getResponseParams().get("remark")).toStringValue();
            this.actName = BlurObject.bind(this.getResponseParams().get("act_name")).toStringValue();
            //
            int _hbCount = this.getXPathHelper().getNumberValue("count(//hblist/hbinfo)").intValue();
            for (int i = 1; i <= _hbCount; i++) {
                Map<String, Object> _values = this.getXPathHelper().toMap(this.getXPathHelper().getNode("//hblist/hbinfo[" + i + "]"));
                if (!_values.isEmpty() && _values.size() == 4) {
                    hbList.add(new HbInfo(BlurObject.bind(_values.get("openid")).toStringValue(),
                            BlurObject.bind(_values.get("amount")).toIntValue(),
                            BlurObject.bind(_values.get("rcv_time")).toStringValue()));
                }
            }
        }

        public String mchBillNo() {
            return mchBillNo;
        }

        public String detailId() {
            return detailId;
        }

        public String status() {
            return status;
        }

        public String sendType() {
            return sendType;
        }

        public String hbType() {
            return hbType;
        }

        public Integer totalNum() {
            return totalNum;
        }

        public Integer totalAmount() {
            return totalAmount;
        }

        public String reason() {
            return reason;
        }

        public String sendTime() {
            return sendTime;
        }

        public String refundTime() {
            return refundTime;
        }

        public Integer refundAmount() {
            return refundAmount;
        }

        public String wishing() {
            return wishing;
        }

        public String remark() {
            return remark;
        }

        public String actName() {
            return actName;
        }

        public List<HbInfo> hbList() {
            return hbList;
        }
    }

    public static class HbInfo {

        private String openId;

        private Integer amount;

        private String revTime;

        public HbInfo(String openId, Integer amount, String revTime) {
            this.openId = openId;
            this.amount = amount;
            this.revTime = revTime;
        }

        public String openId() {
            return openId;
        }

        public Integer amount() {
            return amount;
        }

        public String revTime() {
            return revTime;
        }
    }
}
