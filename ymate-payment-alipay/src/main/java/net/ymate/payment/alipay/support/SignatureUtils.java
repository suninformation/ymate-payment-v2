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
package net.ymate.payment.alipay.support;

import net.ymate.framework.commons.ParamUtils;
import net.ymate.payment.alipay.IAliPay;
import net.ymate.platform.core.util.CodecUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 签名工具类
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/7 上午11:07
 * @version 1.0
 */
public class SignatureUtils {

    private static CodecUtils.RSACodecHelper __SHA1WithRSA = new CodecUtils.RSACodecHelper(1024, IAliPay.Const.SIGN_ALGORITHMS);

    private static CodecUtils.RSACodecHelper __SHA256WithRSA = new CodecUtils.RSACodecHelper(1024, IAliPay.Const.SIGN_SHA256RSA_ALGORITHMS);

    /**
     * @param content    待签名内容
     * @param privateKey 私钥
     * @param charset    字符编码
     * @param signType   签名类型
     * @return 签名字符串
     * @throws Exception 可能产生的异常
     */
    public static String sign(String content, String privateKey, String charset, IAliPay.SignType signType) throws Exception {
        String _returnValue = null;
        byte[] _data = null;
        if (StringUtils.isBlank(charset)) {
            _data = content.getBytes();
        } else {
            _data = content.getBytes(charset);
        }
        switch (signType) {
            case RSA:
                _returnValue = __SHA1WithRSA.sign(_data, privateKey);
                break;
            case RSA2:
                _returnValue = __SHA256WithRSA.sign(_data, privateKey);
                break;
        }
        return _returnValue;
    }

    /**
     * @param params     待签名参数映射
     * @param privateKey 私钥
     * @param charset    字符编码
     * @param signType   签名类型
     * @return 签名字符串
     * @throws Exception 可能产生的异常
     */
    public static String sign(Map<String, String> params, String privateKey, String charset, IAliPay.SignType signType) throws Exception {
        String _content = ParamUtils.buildQueryParamStr(params, false, charset);
        return sign(_content, privateKey, charset, signType);
    }

    /**
     * @param content   待签名内容
     * @param sign      签名字符串
     * @param publicKey 公钥
     * @param charset   字符编码
     * @param signType  签名类型
     * @return 签名是否匹配
     * @throws Exception 可能产生的异常
     */
    public static boolean verify(String content, String sign, String publicKey, String charset, IAliPay.SignType signType) throws Exception {
        boolean _returnValue = false;
        byte[] _data = null;
        if (StringUtils.isBlank(charset)) {
            _data = content.getBytes();
        } else {
            _data = content.getBytes(charset);
        }
        switch (signType) {
            case RSA:
                _returnValue = __SHA1WithRSA.verify(_data, publicKey, sign);
                break;
            case RSA2:
                _returnValue = __SHA256WithRSA.verify(_data, publicKey, sign);
                break;
        }
        return _returnValue;
    }

    /**
     * @param params    待签名参数映射
     * @param sign      签名字符串
     * @param publicKey 公钥
     * @param charset   字符编码
     * @param signType  签名类型
     * @return 签名是否匹配
     * @throws Exception 可能产生的异常
     */
    public static boolean verify(Map<String, String> params, String sign, String publicKey, String charset, IAliPay.SignType signType) throws Exception {
        String _content = ParamUtils.buildQueryParamStr(params, false, charset);
        return verify(_content, sign, publicKey, charset, signType);
    }
}
