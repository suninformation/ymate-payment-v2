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
import net.ymate.framework.commons.ParamUtils;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayBaseResponse;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送普通红包
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/27 上午12:34
 * @version 1.0
 */
public class WxPayRedPackSend extends WxPayBaseRequest<WxPayRedPackSend.Response> {

    private static final Log _LOG = LogFactory.getLog(WxPayRedPackSend.class);

    /**
     * 公众账号ID
     */
    private String wxAppId;

    /**
     * 商户订单号
     */
    private String mchBillNo;

    /**
     * 商户名称
     */
    private String sendName;

    /**
     * 用户openid
     */
    private String reOpenId;

    /**
     * 总金额
     */
    private Integer totalAmount;

    /**
     * 红包发放总人数
     */
    private Integer totalNum;

    /**
     * 红包祝福语
     */
    private String wishing;

    /**
     * Ip地址
     */
    private String clientIp;

    /**
     * 活动名称
     */
    private String actName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 场景ID
     */
    private String seceneId;

    /**
     * 活动信息
     */
    private RiskInfo riskInfo;

    /**
     * 资金授权商户号
     */
    private String consumeMchId;

    public WxPayRedPackSend(WxPayAccountMeta accountMeta, String mchBillNo, String sendName, String reOpenId, Integer totalAmount, Integer totalNum, String wishing, String clientIp, String actName, String remark) {
        super(accountMeta);
        this.wxAppId = accountMeta.getAppId();
        this.mchBillNo = mchBillNo;
        this.sendName = sendName;
        this.reOpenId = reOpenId;
        this.totalAmount = totalAmount;
        this.totalNum = totalNum;
        this.wishing = wishing;
        this.clientIp = clientIp;
        this.actName = actName;
        this.remark = remark;
    }

    public String wxAppId() {
        return wxAppId;
    }

    public WxPayRedPackSend wxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
        return this;
    }

    public String mchBillNo() {
        return mchBillNo;
    }

    public WxPayRedPackSend mchBillNo(String mchBillNo) {
        this.mchBillNo = mchBillNo;
        return this;
    }

    public String sendName() {
        return sendName;
    }

    public WxPayRedPackSend sendName(String sendName) {
        this.sendName = sendName;
        return this;
    }

    public String reOpenId() {
        return reOpenId;
    }

    public WxPayRedPackSend reOpenId(String reOpenId) {
        this.reOpenId = reOpenId;
        return this;
    }

    public Integer totalAmount() {
        return totalAmount;
    }

    public WxPayRedPackSend totalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Integer totalNum() {
        return totalNum;
    }

    public WxPayRedPackSend totalNum(Integer totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public String wishing() {
        return wishing;
    }

    public WxPayRedPackSend wishing(String wishing) {
        this.wishing = wishing;
        return this;
    }

    public String clientIp() {
        return clientIp;
    }

    public WxPayRedPackSend clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public String actName() {
        return actName;
    }

    public WxPayRedPackSend actName(String actName) {
        this.actName = actName;
        return this;
    }

    public String remark() {
        return remark;
    }

    public WxPayRedPackSend remark(String remark) {
        this.remark = remark;
        return this;
    }

    public String seceneId() {
        return seceneId;
    }

    public WxPayRedPackSend seceneId(String seceneId) {
        this.seceneId = seceneId;
        return this;
    }

    public RiskInfo getRiskInfo() {
        return riskInfo;
    }

    public WxPayRedPackSend riskInfo(RiskInfo riskInfo) {
        this.riskInfo = riskInfo;
        return this;
    }

    public String consumeMchId() {
        return consumeMchId;
    }

    public WxPayRedPackSend consumeMchId(String consumeMchId) {
        this.consumeMchId = consumeMchId;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.wxAppId)) {
            throw new NullArgumentException("wxappid");
        }
        if (StringUtils.isBlank(this.mchBillNo)) {
            throw new NullArgumentException("mch_billno");
        }
        if (StringUtils.isBlank(this.sendName)) {
            throw new NullArgumentException("send_name");
        }
        if (StringUtils.isBlank(this.reOpenId)) {
            throw new NullArgumentException("re_openid");
        }
        if (this.totalAmount == null || this.totalAmount <= 0) {
            throw new NullArgumentException("total_amount");
        }
        if (this.totalNum == null || this.totalNum <= 0) {
            throw new NullArgumentException("total_num");
        }
        if (StringUtils.isBlank(this.wishing)) {
            throw new NullArgumentException("wishing");
        }
        if (StringUtils.isBlank(this.clientIp)) {
            throw new NullArgumentException("client_ip");
        }
        if (StringUtils.isBlank(this.actName)) {
            throw new NullArgumentException("act_name");
        }
        if (StringUtils.isBlank(this.remark)) {
            throw new NullArgumentException("remark");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put("wxappid", wxAppId);
        _params.put("mch_billno", mchBillNo);
        _params.put("send_name", sendName);
        _params.put("re_openid", reOpenId);
        _params.put("total_amount", totalAmount);
        _params.put("total_num", totalNum);
        _params.put("wishing", wishing);
        _params.put("client_ip", clientIp);
        _params.put("act_name", actName);
        _params.put("remark", remark);
        _params.put("scene_id", seceneId);
        _params.put("risk_info", riskInfo);
        _params.put("consume_mch_id", consumeMchId);
        //
        return _params;
    }

    protected String __doGetRequestURL() {
        return "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
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
         * 公众账号appid
         */
        private String wxAppId;

        /**
         * 接受收红包的用户openid
         */
        private String reOpenId;

        /**
         * 总金额
         */
        private Integer totalAmount;

        /**
         * 发放成功时间
         */
        private String sendTime;

        /**
         * 微信红包订单号
         */
        private String sendListId;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.mchBillNo = BlurObject.bind(this.getResponseParams().get("mch_billno")).toStringValue();
            this.wxAppId = BlurObject.bind(this.getResponseParams().get("wxappid")).toStringValue();
            this.reOpenId = BlurObject.bind(this.getResponseParams().get("re_openid")).toStringValue();
            this.totalAmount = BlurObject.bind(this.getResponseParams().get("total_amount")).toIntValue();
            this.sendTime = BlurObject.bind(this.getResponseParams().get("send_time")).toStringValue();
            this.sendListId = BlurObject.bind(this.getResponseParams().get("send_listid")).toStringValue();
        }

        public String mchBillNo() {
            return mchBillNo;
        }

        public String wxAppId() {
            return wxAppId;
        }

        public String reOpenId() {
            return reOpenId;
        }

        public Integer totalAmount() {
            return totalAmount;
        }

        public String sendTime() {
            return sendTime;
        }

        public String sendListId() {
            return sendListId;
        }
    }

    public static class RiskInfo {

        private String postTime;

        private String mobile;

        private String deviceId;

        private String clientVersion;

        @Override
        public String toString() {
            Map<String, String> _params = new HashMap<String, String>();
            _params.put("posttime", postTime);
            _params.put("mobile", mobile);
            _params.put("deviceid", deviceId);
            _params.put("clientversion", clientVersion);
            //
            try {
                return URLEncoder.encode(ParamUtils.buildQueryParamStr(_params, false, null), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                _LOG.warn("", e);
            }
            return null;
        }

        public String postTime() {
            return postTime;
        }

        public RiskInfo postTime(String postTime) {
            this.postTime = postTime;
            return this;
        }

        public String mobile() {
            return mobile;
        }

        public RiskInfo mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String deviceId() {
            return deviceId;
        }

        public RiskInfo deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public String clientVersion() {
            return clientVersion;
        }

        public RiskInfo clientVersion(String clientVersion) {
            this.clientVersion = clientVersion;
            return this;
        }
    }
}
