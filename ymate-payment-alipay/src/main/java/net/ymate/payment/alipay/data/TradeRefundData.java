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
 * @author 刘镇 (suninformation@163.com) on 17/6/11 下午9:49
 * @version 1.0
 */
public class TradeRefundData implements IAliPayRequestData {

    /**
     * 订单支付时传入的商户订单号, 不能和trade_no同时为空
     */
    private String outTradeNo;

    /**
     * 支付宝交易号, 和商户订单号不能同时为空
     */
    private String tradeNo;

    /**
     * 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     */
    private String refundAmount;

    /**
     * 退款的原因说明
     */
    private String refundReason;

    /**
     * 标识一次退款请求, 同一笔交易多次退款需要保证唯一, 如需部分退款, 则此参数必传
     */
    private String outRequestNo;

    /**
     * 商户的操作员编号
     */
    private String operatorId;

    /**
     * 商户的门店编号
     */
    private String storeId;

    /**
     * 商户的终端编号
     */
    private String terminalId;

    public TradeRefundData(String outTradeNo, String tradeNo, String refundAmount) {
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(tradeNo)) {
            throw new NullArgumentException("outTradeNo or tradeNo");
        }
        if (StringUtils.isBlank(refundAmount)) {
            throw new NullArgumentException("refundAmount");
        }
        this.outTradeNo = outTradeNo;
        this.tradeNo = tradeNo;
        this.refundAmount = refundAmount;
    }

    public Map<String, String> buildRequestParams() {
        Map<String, String> _params = new HashMap<String, String>();
        //
        _params.put("refund_amount", refundAmount);
        //
        if (StringUtils.isNotBlank(outTradeNo)) {
            _params.put("out_trade_no", outTradeNo);
        }
        if (StringUtils.isNotBlank(tradeNo)) {
            _params.put("trade_no", tradeNo);
        }
        if (StringUtils.isNotBlank(outRequestNo)) {
            _params.put("out_request_no", outRequestNo);
        }
        if (StringUtils.isNotBlank(operatorId)) {
            _params.put("operator_id", operatorId);
        }
        if (StringUtils.isNotBlank(storeId)) {
            _params.put("store_id", storeId);
        }
        if (StringUtils.isNotBlank(terminalId)) {
            _params.put("terminal_id", terminalId);
        }
        //
        return _params;
    }
}
