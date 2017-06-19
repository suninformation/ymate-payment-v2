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
package net.ymate.payment.wxpay.base;

import net.ymate.framework.commons.ParamUtils;
import net.ymate.platform.core.util.UUIDUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 16/6/26 上午12:51
 * @version 1.0
 */
public abstract class WxPayBaseData {

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;

    public String mchId() {
        return mchId;
    }

    protected WxPayBaseData mchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public String nonceStr() {
        return nonceStr;
    }

    protected WxPayBaseData nonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        return this;
    }

    public String sign() {
        return sign;
    }

    protected WxPayBaseData sign(String sign) {
        this.sign = sign;
        return this;
    }

    public static String __doBuildXML(Map<String, Object> paramMap) {
        StringBuilder _xmlSB = new StringBuilder("<xml>");
        for (Map.Entry<String, Object> _entry : paramMap.entrySet()) {
            if (_entry.getValue() != null) {
                _xmlSB.append("<").append(_entry.getKey()).append(">");
                if (_entry.getValue() instanceof String) {
                    _xmlSB.append("<![CDATA[").append(_entry.getValue().toString()).append("]]>");
                } else {
                    _xmlSB.append(_entry.getValue().toString());
                }
                _xmlSB.append("</").append(_entry.getKey()).append(">");
            }
        }
        return _xmlSB.append("</xml>").toString();
    }

    /**
     * @return 产生随机字符串，长度为6到32位不等
     */
    public static String __doCreateNonceStr() {
        return UUIDUtils.randomStr(UUIDUtils.randomInt(6, 32), false).toLowerCase();
    }

    /**
     * @param paramMap 请求协议参数对象映射
     * @param mchKey   商户密钥
     * @return 返回最终生成的签名
     */
    public static String __doCreateSignature(Map<String, Object> paramMap, String mchKey) {
        String _queryParamStr = ParamUtils.buildQueryParamStr(paramMap, false, null);
        _queryParamStr += "&key=" + mchKey;
        return DigestUtils.md5Hex(_queryParamStr).toUpperCase();
    }
}
