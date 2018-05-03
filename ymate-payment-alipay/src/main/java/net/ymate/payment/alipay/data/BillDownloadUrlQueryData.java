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
package net.ymate.payment.alipay.data;

import net.ymate.payment.alipay.IAliPayRequestData;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/12 上午2:46
 * @version 1.0
 */
public class BillDownloadUrlQueryData implements IAliPayRequestData {

    /**
     * 账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型：trade、signcustomer；
     * <p>
     * trade指商户基于支付宝交易收单的业务账单；
     * <p>
     * signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单；
     */
    private String billType;

    /**
     * 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     */
    private String billDate;

    public BillDownloadUrlQueryData(String billType, String billDate) {
        if (StringUtils.isBlank(billType)) {
            throw new NullArgumentException("billType");
        }
        if (StringUtils.isBlank(billDate)) {
            throw new NullArgumentException("billDate");
        }
        this.billType = billType;
        this.billDate = billDate;
    }

    @Override
    public Map<String, String> buildRequestParams() {
        Map<String, String> _params = new HashMap<String, String>();
        //
        if (StringUtils.isNotBlank(billType)) {
            _params.put("bill_type", billType);
        }
        if (StringUtils.isNotBlank(billDate)) {
            _params.put("bill_date", billDate);
        }
        //
        return _params;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
