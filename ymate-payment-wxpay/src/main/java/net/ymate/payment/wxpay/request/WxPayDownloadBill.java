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
import net.ymate.framework.commons.XPathHelper;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayBaseResponse;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 下载对账单
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/26 下午11:54
 * @version 1.0
 */
public class WxPayDownloadBill extends WxPayBaseRequest<WxPayDownloadBill.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 对账单日期
     */
    private String billData;

    /**
     * 账单类型
     */
    private IWxPay.BillType billType;

    /**
     * 压缩账单
     */
    private boolean tarType;

    public WxPayDownloadBill(WxPayAccountMeta accountMeta, String billData, IWxPay.BillType billType) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.billData = billData;
        this.billType = billType;
    }

    public String appId() {
        return appId;
    }

    public WxPayDownloadBill appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String deviceInfo() {
        return deviceInfo;
    }

    public WxPayDownloadBill deviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String billData() {
        return billData;
    }

    public WxPayDownloadBill billData(String billData) {
        this.billData = billData;
        return this;
    }

    public IWxPay.BillType billType() {
        return billType;
    }

    public WxPayDownloadBill billType(IWxPay.BillType billType) {
        this.billType = billType;
        return this;
    }

    public boolean tarType() {
        return tarType;
    }

    public WxPayDownloadBill tarType(boolean tarType) {
        this.tarType = tarType;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.billData)) {
            throw new NullArgumentException("bill_date");
        }
        if (this.billType == null) {
            throw new NullArgumentException("bill_type");
        }
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put(IWxPay.Const.DEVICE_INFO, deviceInfo);
        _params.put("bill_date", billData);
        _params.put("bill_type", billType.name());
        if (this.tarType) {
            _params.put("tar_type", "GZIP");
        }
        return _params;
    }

    protected String __doGetRequestURL() {
        return "https://api.mch.weixin.qq.com/pay/downloadbill";
    }

    protected Response __doParseResponse(IHttpResponse httpResponse) {
        try {
            XPathHelper _xpath = new XPathHelper(httpResponse.getContent());
            return new Response(null, _xpath.getStringValue("//return_code"), _xpath.getStringValue("//return_msg"));
        } catch (Exception e) {
            // Nothing..
        }
        return new Response(httpResponse.getContent(), "SUCCESS", null);
    }

    /**
     * 下载对账单响应
     */
    public static class Response extends WxPayBaseResponse {

        /**
         * 对账单内容
         */
        private String content;

        public Response(String content, String returnCode, String returnMsg) {
            super(returnCode, returnMsg);
            //
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean checkSignature(String mchKey) {
            // 该接口不需要验签
            return true;
        }
    }
}
