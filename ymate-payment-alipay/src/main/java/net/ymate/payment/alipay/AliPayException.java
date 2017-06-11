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
package net.ymate.payment.alipay;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/11 下午5:34
 * @version 1.0
 */
public class AliPayException extends Exception {

    public static Map<String, String> __ERRORS = new HashMap<String, String>();

    static {
        __ERRORS.put("20000", "服务不可用");
        __ERRORS.put("20001", "授权权限不足");
        __ERRORS.put("40001", "缺少必选参数");
        __ERRORS.put("40002", "非法的参数");
        __ERRORS.put("40004", "业务处理失败");
        __ERRORS.put("40006", "权限不足");
        //
        __ERRORS.put("isp.unknow-error", "服务暂不可用（业务系统不可用）");
        __ERRORS.put("aop.unknow-error", "服务暂不可用（网关自身的未知错误）");
        __ERRORS.put("aop.invalid-auth-token", "无效的访问令牌");
        __ERRORS.put("aop.auth-token-time-out", "访问令牌已过期");
        __ERRORS.put("aop.invalid-app-auth-token", "无效的应用授权令牌");
        __ERRORS.put("aop.invalid-app-auth-token-no-api", "商户未授权当前接口");
        __ERRORS.put("aop.app-auth-token-time-out", "应用授权令牌已过期");
        __ERRORS.put("aop.no-product-reg-by-partner", "商户未签约任何产品");
        __ERRORS.put("isv.missing-method", "缺少方法名参数");
        __ERRORS.put("isv.missing-signature", "缺少签名参数");
        __ERRORS.put("isv.missing-signature-type", "缺少签名类型参数");
        __ERRORS.put("isv.missing-signature-key", "缺少签名配置");
        __ERRORS.put("isv.missing-app-id", "缺少appId参数");
        __ERRORS.put("isv.missing-timestamp", "缺少时间戳参数");
        __ERRORS.put("isv.missing-version", "缺少版本参数");
        __ERRORS.put("isv.decryption-error-missing-encrypt-type", "解密出错, 未指定加密算法");
        __ERRORS.put("isv.invalid-parameter", "参数无效");
        __ERRORS.put("isv.upload-fail", "文件上传失败");
        __ERRORS.put("isv.invalid-file-extension", "文件扩展名无效");
        __ERRORS.put("isv.invalid-file-size", "文件大小无效");
        __ERRORS.put("isv.invalid-method", "不存在的方法名");
        __ERRORS.put("isv.invalid-format", "无效的数据格式");
        __ERRORS.put("isv.invalid-signature-type", "无效的签名类型");
        __ERRORS.put("isv.invalid-signature", "无效签名");
        __ERRORS.put("isv.invalid-encrypt-type", "无效的加密类型");
        __ERRORS.put("isv.invalid-encrypt", "解密异常");
        __ERRORS.put("isv.invalid-app-id", "无效的appId参数");
        __ERRORS.put("isv.invalid-timestamp", "非法的时间戳参数");
        __ERRORS.put("isv.invalid-charset", "字符集错误");
        __ERRORS.put("isv.invalid-digest", "摘要错误");
        __ERRORS.put("isv.decryption-error-not-valid-encrypt-type", "解密出错，不支持的加密算法");
        __ERRORS.put("isv.decryption-error-not-valid-encrypt-key", "解密出错, 未配置加密密钥或加密密钥格式错误");
        __ERRORS.put("isv.decryption-error-unknown", "解密出错，未知异常");
        __ERRORS.put("isv.missing-signature-config", "验签出错, 未配置对应签名算法的公钥或者证书");
        __ERRORS.put("isv.not-support-app-auth", "本接口不支持第三方代理调用");
        __ERRORS.put("isv.insufficient-isv-permissions", "ISV权限不足");
        __ERRORS.put("isv.insufficient-user-permissions", "用户权限不足");
        //
        __ERRORS.put("ACQ.SYSTEM_ERROR", "系统错误");
        __ERRORS.put("ACQ.INVALID_PARAMETER", "参数无效");
        __ERRORS.put("ACQ.SELLER_BALANCE_NOT_ENOUGH", "卖家余额不足");
        __ERRORS.put("ACQ.REFUND_AMT_NOT_EQUAL_TOTAL", "退款金额超限");
        __ERRORS.put("ACQ.REASON_TRADE_BEEN_FREEZEN", "请求退款的交易被冻结");
        __ERRORS.put("ACQ.TRADE_NOT_EXIST", "交易不存在");
        __ERRORS.put("ACQ.TRADE_HAS_FINISHED", "交易已完结");
        __ERRORS.put("ACQ.TRADE_STATUS_ERROR", "交易状态非法");
        __ERRORS.put("ACQ.DISCORDANT_REPEAT_REQUEST", "不一致的请求");
        __ERRORS.put("ACQ.REASON_TRADE_REFUND_FEE_ERR", "退款金额无效");
        __ERRORS.put("ACQ.TRADE_NOT_ALLOW_REFUND", "当前交易不允许退款");
        //
        __ERRORS.put("ACQ.TRADE_NOT_EXIST", "交易不存在");
        __ERRORS.put("ACQ.TRADE_STATUS_ERROR", "交易状态不合法");
        __ERRORS.put("ACQ.INVALID_PARAMETER", "参数无效");
        __ERRORS.put("TRADE_NOT_EXIST", "查询退款的交易不存在");
        __ERRORS.put("INVAILID_ARGUMENTS", "入参不合法");
        __ERRORS.put("BILL_NOT_EXIST", "账单不存在");
        __ERRORS.put("UNKNOWN_ERROR", "未知错误");
    }

    private String errCode;

    private String errMsg;

    public AliPayException() {
        super();
    }

    public AliPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public AliPayException(Throwable cause) {
        super(cause);
    }

    public AliPayException(String errCode) {
        this.errCode = StringUtils.defaultIfBlank(errCode, "UNKNOWN_ERROR");
        this.errMsg = __ERRORS.get(this.errCode);
    }

    public AliPayException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
