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
package net.ymate.payment.wxpay.request;

import com.alibaba.fastjson.annotation.JSONField;
import net.ymate.framework.commons.IHttpResponse;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseRequest;
import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 提交刷卡支付
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/20 下午11:33
 * @version 1.0
 */
public class WxPayMicroPay extends WxPayBaseRequest<WxPayMicroPay.Response> {

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 货币类型
     */
    private String feeType;

    /**
     * 总金额
     */
    private Integer totalFee;

    /**
     * 终端IP
     */
    private String spbillCreateIp;

    /**
     * 商品标记
     */
    private String goodsTag;

    /**
     * 指定支付方式
     */
    private String limitPay;

    /**
     * 授权码
     */
    private String authCode;

    /**
     * 场景信息
     */
    private String sceneInfo;

    public WxPayMicroPay(WxPayAccountMeta accountMeta, String body, String outTradeNo, Integer totalFee, String spbillCreateIp, String authCode) {
        super(accountMeta);
        this.appId = accountMeta.getAppId();
        this.body = body;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.authCode = authCode;
    }

    public String appId() {
        return appId;
    }

    public WxPayMicroPay appId(String appId) {
        this.appId = appId;
        return this;
    }

    public String deviceInfo() {
        return deviceInfo;
    }

    public WxPayMicroPay deviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public String body() {
        return body;
    }

    public WxPayMicroPay body(String body) {
        this.body = body;
        return this;
    }

    public String detail() {
        return detail;
    }

    public WxPayMicroPay detail(String detail) {
        this.detail = detail;
        return this;
    }

    public String attach() {
        return attach;
    }

    public WxPayMicroPay attach(String attach) {
        this.attach = attach;
        return this;
    }

    public String outTradeNo() {
        return outTradeNo;
    }

    public WxPayMicroPay outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public String feeType() {
        return feeType;
    }

    public WxPayMicroPay feeType(String feeType) {
        this.feeType = feeType;
        return this;
    }

    public Integer totalFee() {
        return totalFee;
    }

    public WxPayMicroPay totalFee(Integer totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public String spbillCreateIp() {
        return spbillCreateIp;
    }

    public WxPayMicroPay spbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
        return this;
    }

    public String goodsTag() {
        return goodsTag;
    }

    public WxPayMicroPay goodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
        return this;
    }

    public String limitPay() {
        return limitPay;
    }

    public WxPayMicroPay limitPay(String limitPay) {
        this.limitPay = limitPay;
        return this;
    }

    public String authCode() {
        return authCode;
    }

    public WxPayMicroPay authCode(String authCode) {
        this.authCode = authCode;
        return this;
    }

    public String sceneInfo() {
        return sceneInfo;
    }

    public WxPayMicroPay sceneInfo(String sceneInfo) {
        this.sceneInfo = sceneInfo;
        return this;
    }

    @Override
    public Map<String, Object> buildSignatureParams() {
        if (StringUtils.isBlank(this.appId)) {
            throw new NullArgumentException(IWxPay.Const.APP_ID);
        }
        if (StringUtils.isBlank(this.body)) {
            throw new NullArgumentException("body");
        }
        if (StringUtils.isBlank(this.outTradeNo)) {
            throw new NullArgumentException(IWxPay.Const.OUT_TRADE_NO);
        }
        if (this.totalFee == null || this.totalFee <= 0) {
            throw new NullArgumentException("total_fee");
        }
        if (StringUtils.isBlank(this.spbillCreateIp)) {
            throw new NullArgumentException("spbill_create_ip");
        }
        if (StringUtils.isBlank(this.authCode)) {
            throw new NullArgumentException("auth_code");
        }
        //
        Map<String, Object> _params = super.buildSignatureParams();
        _params.put(IWxPay.Const.APP_ID, appId);
        _params.put(IWxPay.Const.DEVICE_INFO, deviceInfo);
        _params.put("body", body);
        _params.put("detail", detail);
        _params.put("attach", attach);
        _params.put(IWxPay.Const.OUT_TRADE_NO, outTradeNo);
        _params.put("fee_type", feeType);
        _params.put("total_fee", totalFee);
        _params.put("spbill_create_ip", spbillCreateIp);
        _params.put("goods_tag", goodsTag);
        _params.put("limit_pay", limitPay);
        _params.put("auth_code", authCode);
        _params.put("scene_info", sceneInfo);
        return _params;
    }

    @Override
    protected String __doGetRequestURL() {
        return "pay/micropay";
    }

    @Override
    protected Response __doParseResponse(IHttpResponse httpResponse) throws Exception {
        return new Response(httpResponse.getContent());
    }

    /**
     * 提交刷卡支付响应
     */
    public static class Response extends WxPayNotifyResponse {

        /**
         * 营销详情
         */
        private String promotionDetail;

        public Response(String protocol) throws Exception {
            super(protocol);
            this.promotionDetail = BlurObject.bind(this.getResponseParams().get("promotion_detail")).toStringValue();
        }

        public String promotionDetail() {
            return promotionDetail;
        }
    }

    /**
     * 单品优惠
     */
    public static class Detail {

        /**
         * 订单原价
         */
        @JSONField(name = "cost_price")
        private Integer costPrice;

        /**
         * 商品小票ID
         */
        @JSONField(name = "receipt_id")
        private String receiptId;

        /**
         * 单品列表
         */
        @JSONField(name = "goods_detail")
        private List<GoodsDetail> goodsDetail;
    }

    /**
     * 营销详情
     */
    public static class PromotionDetail {

        @JSONField(name = "cost_price")
        private Integer costPrice;

        @JSONField(name = "promotion_id")
        private String promotionId;

        private String name;

        private String scope;

        private String type;

        private Integer amount;

        @JSONField(name = "activity_id")
        private String activityId;

        @JSONField(name = "wxpay_contribute")
        private String wxpayContribute;

        @JSONField(name = "merchant_contribute")
        private String merchantContribute;

        @JSONField(name = "other_contribute")
        private String otherContribute;

        @JSONField(name = "goods_detail")
        private List<GoodsDetail> goodsDetail;

        public Integer getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Integer costPrice) {
            this.costPrice = costPrice;
        }

        public String getPromotionId() {
            return promotionId;
        }

        public void setPromotionId(String promotionId) {
            this.promotionId = promotionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getWxpayContribute() {
            return wxpayContribute;
        }

        public void setWxpayContribute(String wxpayContribute) {
            this.wxpayContribute = wxpayContribute;
        }

        public String getMerchantContribute() {
            return merchantContribute;
        }

        public void setMerchantContribute(String merchantContribute) {
            this.merchantContribute = merchantContribute;
        }

        public String getOtherContribute() {
            return otherContribute;
        }

        public void setOtherContribute(String otherContribute) {
            this.otherContribute = otherContribute;
        }

        public List<GoodsDetail> getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(List<GoodsDetail> goodsDetail) {
            this.goodsDetail = goodsDetail;
        }
    }

    /**
     * 单品优惠活动
     */
    public static class GoodsDetail {

        /**
         * 商品编码
         */
        @JSONField(name = "goods_id")
        private String goodsId;

        /**
         * 商品备注
         */
        @JSONField(name = "goods_remark")
        private String goodsRemark;

        /**
         * 商品优惠金额
         */
        @JSONField(name = "discount_amount")
        private Integer discountAmount;

        /**
         * 微信侧商品编码
         */
        @JSONField(name = "wxpay_goods_id")
        private String wxpayGoodsId;

        /**
         * 商品名称
         */
        @JSONField(name = "goods_name")
        private String goodsName;

        /**
         * 商品数量
         */
        private Integer quantity;

        /**
         * 商品单价
         */
        private Integer price;

        public GoodsDetail() {
        }

        public GoodsDetail(String goodsId, Integer quantity, Integer price) {
            this.goodsId = goodsId;
            this.quantity = quantity;
            this.price = price;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getWxpayGoodsId() {
            return wxpayGoodsId;
        }

        public void setWxpayGoodsId(String wxpayGoodsId) {
            this.wxpayGoodsId = wxpayGoodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getGoodsRemark() {
            return goodsRemark;
        }

        public void setGoodsRemark(String goodsRemark) {
            this.goodsRemark = goodsRemark;
        }

        public Integer getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Integer discountAmount) {
            this.discountAmount = discountAmount;
        }
    }
}
