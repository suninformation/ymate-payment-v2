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

import net.ymate.payment.wxpay.base.WxPayAccountMeta;

import java.util.Map;

/**
 * 发放裂变红包
 *
 * @author 刘镇 (suninformation@163.com) on 16/6/27 上午12:39
 * @version 1.0
 */
public class WxPayRedPackSendGroup extends WxPayRedPackSend {

    /**
     * 红包金额设置方式
     */
    private String amtType;

    public WxPayRedPackSendGroup(WxPayAccountMeta accountMeta, String mchBillNo, String sendName, String reOpenId, Integer totalAmount, Integer totalNum, String wishing, String clientIp, String actName, String remark) {
        super(accountMeta, mchBillNo, sendName, reOpenId, totalAmount, totalNum, wishing, clientIp, actName, remark);
        this.amtType = "ALL_RAND";
    }

    public String amtType() {
        return amtType;
    }

    public WxPayRedPackSendGroup amtType(String amtType) {
        this.amtType = amtType;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put("amt_type", amtType);
        return _params;
    }

    protected String __doGetRequestURL() {
        return "mmpaymkttransfers/sendgroupredpack";
    }
}
