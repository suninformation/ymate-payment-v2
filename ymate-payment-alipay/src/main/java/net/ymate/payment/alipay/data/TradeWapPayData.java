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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.ymate.payment.alipay.IAliPay;
import net.ymate.payment.alipay.IAliPayRequestData;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * WAP场景下单并支付请求参数类
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/8 下午5:05
 * @version 1.0
 */
public class TradeWapPayData implements IAliPayRequestData {

    /**
     * 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     */
    private String body;

    /**
     * 商品的标题/交易标题/订单标题/订单关键字等
     */
    private String subject;

    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;

    /**
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
     */
    private String timeoutExpress;

    /**
     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     */
    private String totalAmount;

    /**
     * 收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     */
    private String sellerId;

    /**
     * 针对用户授权接口，获取用户相关数据时，用于标识用户授权关系
     */
    private String authToken;

    /**
     * 销售产品码，商家和支付宝签约的产品码: QUICK_WAP_PAY
     */
    private String productCode;

    /**
     * 商品主类型：0—虚拟类商品，1—实物类商品
     * 注：虚拟类商品不支持使用花呗渠道
     */
    private String goodsType;

    /**
     * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
     */
    private String passbackParams;

    /**
     * 优惠参数
     * 注：仅与支付宝协商后可用
     */
    private String promoParams;

    /**
     * 可用渠道，用户只能在指定渠道范围内支付
     * 当有多个渠道时用“,”分隔
     * 注：与disable_pay_channels互斥
     */
    private String enablePayChannels;

    /**
     * 禁用渠道，用户不可用指定渠道支付
     * 当有多个渠道时用“,”分隔
     * 注：与enable_pay_channels互斥
     */
    private String disablePayChannels;

    /**
     * 商户门店编号。该参数用于请求参数中以区分各门店，非必传项
     */
    private String storeId;

    // ----- 以下为业务扩展参数 -----

    /**
     * 系统商编号，该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
     */
    private String sysServiceProviderId;

    /**
     * 是否发起实名校验
     * T：发起
     * F：不发起
     */
    private String needBuyerRealnamed;

    /**
     * 账务备注
     * 注：该字段显示在离线账单的账务备注中
     */
    private String transMemo;

    public TradeWapPayData(String outTradeNo, String totalAmount, String subject) {
        if (StringUtils.isBlank(outTradeNo)) {
            throw new NullArgumentException("outTradeNo");
        }
        if (StringUtils.isBlank(totalAmount)) {
            throw new NullArgumentException("totalAmount");
        }
        if (StringUtils.isBlank(subject)) {
            throw new NullArgumentException("subject");
        }
        this.outTradeNo = outTradeNo;
        this.productCode = IAliPay.Const.PROD_CODE_WAP;
        this.totalAmount = totalAmount;
        this.subject = subject;
    }

    public Map<String, String> buildRequestParams() {
        Map<String, String> _params = new HashMap<String, String>();
        //
        _params.put("out_trade_no", outTradeNo);
        _params.put("product_code", productCode);
        _params.put("total_amount", totalAmount);
        _params.put("subject", subject);
        if (StringUtils.isNotBlank(body)) {
            _params.put("body", body);
        }
        if (StringUtils.isNotBlank(passbackParams)) {
            _params.put("passback_params", passbackParams);
        }
        //
        if (StringUtils.isNotBlank(goodsType)) {
            _params.put("goods_type", goodsType);
        }
        if (StringUtils.isNotBlank(timeoutExpress)) {
            _params.put("timeout_express", timeoutExpress);
        }
        if (StringUtils.isNotBlank(enablePayChannels)) {
            _params.put("enable_pay_channels", enablePayChannels);
        }
        if (StringUtils.isNotBlank(disablePayChannels)) {
            _params.put("disable_pay_channels", disablePayChannels);
        }
        if (StringUtils.isNotBlank(authToken)) {
            _params.put("auth_token", authToken);
        }
        //
        Map<String, String> _extendParams = new HashMap<String, String>();
        if (StringUtils.isNotBlank(sysServiceProviderId)) {
            _extendParams.put("sys_service_provider_id", sysServiceProviderId);
        }
        if (StringUtils.isNotBlank(needBuyerRealnamed)) {
            _extendParams.put("needBuyerRealnamed", needBuyerRealnamed);
        }
        if (StringUtils.isNotBlank(transMemo)) {
            _extendParams.put("TRANS_MEMO", transMemo);
        }
        if (!_extendParams.isEmpty()) {
            _params.put("extend_params", JSON.toJSONString(_extendParams, SerializerFeature.QuoteFieldNames));
        }
        //
        return _params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }

    public String getPromoParams() {
        return promoParams;
    }

    public void setPromoParams(String promoParams) {
        this.promoParams = promoParams;
    }

    public String getEnablePayChannels() {
        return enablePayChannels;
    }

    public void setEnablePayChannels(String enablePayChannels) {
        this.enablePayChannels = enablePayChannels;
    }

    public String getDisablePayChannels() {
        return disablePayChannels;
    }

    public void setDisablePayChannels(String disablePayChannels) {
        this.disablePayChannels = disablePayChannels;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSysServiceProviderId() {
        return sysServiceProviderId;
    }

    public void setSysServiceProviderId(String sysServiceProviderId) {
        this.sysServiceProviderId = sysServiceProviderId;
    }

    public String getNeedBuyerRealnamed() {
        return needBuyerRealnamed;
    }

    public void setNeedBuyerRealnamed(String needBuyerRealnamed) {
        this.needBuyerRealnamed = needBuyerRealnamed;
    }

    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }
}
