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
 * PC场景下单并支付请求参数类
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/8 下午5:05
 * @version 1.0
 */
public class TradePagePayData implements IAliPayRequestData {

    /**
     * 商户订单号，64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     */
    private String outTradeNo;

    /**
     * 销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY
     */
    private String productCode;

    /**
     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     */
    private String totalAmount;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 订单描述
     */
    private String body;

    /**
     * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
     */
    private String passbackParams;

    /**
     * 商品主类型：0—虚拟类商品，1—实物类商品（默认）
     * 注：虚拟类商品不支持使用花呗渠道
     */
    private String goodsType;

    /**
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
     */
    private String timeoutExpress;

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
     * 针对用户授权接口，获取用户相关数据时，用于标识用户授权关系
     */
    private String authToken;

    /**
     * PC扫码支付的方式，支持前置模式和跳转模式。
     * 前置模式是将二维码前置到商户的订单确认页的模式。需要商户在自己的页面中以 iframe 方式请求支付宝页面。具体分为以下几种：
     * 0：订单码-简约前置模式，对应 iframe 宽度不能小于600px，高度不能小于300px；
     * 1：订单码-前置模式，对应iframe 宽度不能小于 300px，高度不能小于600px；
     * 3：订单码-迷你前置模式，对应 iframe 宽度不能小于 75px，高度不能小于75px；
     * 4：订单码-可定义宽度的嵌入式二维码，商户可根据需要设定二维码的大小。
     * <p>
     * 跳转模式下，用户的扫码界面是由支付宝生成的，不在商户的域名下。
     * 2：订单码-跳转模式
     */
    private String qrPayMode;

    /**
     * 商户自定义二维码宽度
     * <p>
     * 注：qr_pay_mode=4时该参数生效
     */
    private String qrcodeWidth;

    // ----- 以下为业务扩展参数 -----

    /**
     * 系统商编号，该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
     */
    private String sysServiceProviderId;

    // ----- 以下为商品明细参数 -----

    /**
     * 商品的展示地址
     */
    private String showUrl;

    public TradePagePayData(String outTradeNo, String totalAmount, String subject) {
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
        this.productCode = IAliPay.Const.PROD_CODE_PAGE;
        this.totalAmount = totalAmount;
        this.subject = subject;
    }

    @Override
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
        if (StringUtils.isNotBlank(qrPayMode)) {
            _params.put("qr_pay_mode", qrPayMode);
        }
        if (StringUtils.isNotBlank(qrcodeWidth)) {
            _params.put("qrcode_width", qrcodeWidth);
        }
        //
        Map<String, String> _extendParams = new HashMap<String, String>();
        if (StringUtils.isNotBlank(sysServiceProviderId)) {
            _extendParams.put("sys_service_provider_id", sysServiceProviderId);
        }
        if (!_extendParams.isEmpty()) {
            _params.put("extend_params", JSON.toJSONString(_extendParams, SerializerFeature.QuoteFieldNames));
        }
        //
        Map<String, String> _goodsDetail = new HashMap<String, String>();
        if (StringUtils.isNotBlank(showUrl)) {
            _goodsDetail.put("show_url", showUrl);
        }
        if (!_goodsDetail.isEmpty()) {
            _params.put("goods_detail", JSON.toJSONString(_goodsDetail, SerializerFeature.QuoteFieldNames));
        }
        //
        return _params;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getQrPayMode() {
        return qrPayMode;
    }

    public void setQrPayMode(String qrPayMode) {
        this.qrPayMode = qrPayMode;
    }

    public String getQrcodeWidth() {
        return qrcodeWidth;
    }

    public void setQrcodeWidth(String qrcodeWidth) {
        this.qrcodeWidth = qrcodeWidth;
    }

    public String getSysServiceProviderId() {
        return sysServiceProviderId;
    }

    public void setSysServiceProviderId(String sysServiceProviderId) {
        this.sysServiceProviderId = sysServiceProviderId;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }
}
