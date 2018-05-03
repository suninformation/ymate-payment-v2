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
import net.ymate.payment.alipay.data.BillDownloadUrlQueryData;

/**
 * 查询对账单下载地址
 * <p>
 * 为方便商户快速查账，支持商户通过本接口获取商户离线账单下载地址
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/12 上午2:38
 * @version 1.0
 */
public class AliPayBillDownloadUrlQuery extends AliPayBaseRequest<BillDownloadUrlQueryData, AliPayBillDownloadUrlQuery.Response> {

    private static final String METHOD_NAME = "alipay.data.dataservice.bill.downloadurl.query";

    public AliPayBillDownloadUrlQuery(AliPayAccountMeta accountMeta, BillDownloadUrlQueryData bizContent) {
        super(accountMeta, METHOD_NAME, "1.0", bizContent, false, false, new AliPayBaseResponseParser<Response>(Response.class, METHOD_NAME));
    }

    /**
     * 查询对账单下载地址接口响应对象
     */
    public static class Response extends AliPayBaseResponse {

        /**
         * 账单下载地址链接，获取连接后30秒后未下载，链接地址失效
         */
        @JSONField(name = "bill_download_url")
        private String billDownloadUrl;

        @Override
        public boolean successful() {
            return true;
        }

        public String getBillDownloadUrl() {
            return billDownloadUrl;
        }

        public void setBillDownloadUrl(String billDownloadUrl) {
            this.billDownloadUrl = billDownloadUrl;
        }
    }
}
