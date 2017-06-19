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

import net.ymate.framework.commons.XPathHelper;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.WxPayException;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 16/6/26 上午12:45
 * @version 1.0
 */
public abstract class WxPayBaseResponse extends WxPayBaseData {

    private static final Log _LOG = LogFactory.getLog(WxPayBaseResponse.class);

    /**
     * 原始报文
     */
    private String __originalContent;

    private XPathHelper __xpathHelper;

    private Map<String, Object> __responseParams;

    /**
     * 返回状态码
     */
    private String returnCode;

    /**
     * 返回信息
     */
    private String returnMsg;

    /**
     * 业务结果
     */
    private String resultCode;

    /**
     * 错误代码
     */
    private String errCode;

    /**
     * 错误代码描述
     */
    private String errCodeDes;

    public WxPayBaseResponse(String returnCode, String returnMsg) {
        __responseParams = new HashMap<String, Object>();
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public WxPayBaseResponse(String protocol) throws Exception {
        __originalContent = protocol;
        __xpathHelper = new XPathHelper(protocol);
        __responseParams = __xpathHelper.toMap();
        //
        this.mchId(BlurObject.bind(__responseParams.get(IWxPay.Const.MCH_ID)).toStringValue());
        this.nonceStr(BlurObject.bind(__responseParams.get(IWxPay.Const.NONCE_STR)).toStringValue());
        this.sign(BlurObject.bind(__responseParams.get(IWxPay.Const.SIGN)).toStringValue());
        //
        this.returnCode = BlurObject.bind(__responseParams.get(IWxPay.Const.RETURN_CODE)).toStringValue();
        this.returnMsg = BlurObject.bind(__responseParams.get(IWxPay.Const.RETURN_MSG)).toStringValue();
        this.resultCode = BlurObject.bind(__responseParams.get(IWxPay.Const.RESULT_CODE)).toStringValue();
        this.errCode = BlurObject.bind(__responseParams.get(IWxPay.Const.ERR_CODE)).toStringValue();
        this.errCodeDes = BlurObject.bind(__responseParams.get(IWxPay.Const.ERR_CODE_DES)).toStringValue();
    }

    public String getOriginalContent() {
        return __originalContent;
    }

    public XPathHelper getXPathHelper() {
        return __xpathHelper;
    }

    public Map<String, Object> getResponseParams() {
        return __responseParams;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    /**
     * @param mchKey 商户密钥
     * @return 检查签名并返回匹配结果
     */
    public boolean checkSignature(String mchKey) {
        Map<String, Object> _params = new HashMap<String, Object>(__responseParams);
        String _oSign = (String) _params.remove(IWxPay.Const.SIGN);
        String _nSign = __doCreateSignature(_params, mchKey);
        return _oSign != null && _oSign.equals(_nSign);
    }

    public boolean checkReturnCode() throws WxPayException {
        if (StringUtils.equalsIgnoreCase(IWxPay.ReturnCode.FAIL.name(), this.getReturnCode())) {
            if (_LOG.isDebugEnabled()) {
                _LOG.debug("ResponseContent: " + this.getOriginalContent());
            }
            throw new WxPayException(this.getReturnCode(), this.getReturnMsg());
        }
        return true;
    }

    public boolean checkResultCode() throws WxPayException {
        if (StringUtils.equalsIgnoreCase(IWxPay.ResultCode.FAIL.name(), this.getResultCode())) {
            if (_LOG.isDebugEnabled()) {
                _LOG.debug("ResponseContent: " + this.getOriginalContent());
            }
            throw new WxPayException(this.getErrCode(), this.getErrCodeDes());
        }
        return true;
    }
}
