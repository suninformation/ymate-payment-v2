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
package net.ymate.payment.wxpay;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/19 上午10:46
 * @version 1.0
 */
public class WxPayException extends Exception {

    private String errCode;

    private String errMsg;

    public WxPayException() {
        super();
    }

    public WxPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxPayException(Throwable cause) {
        super(cause);
    }

    public WxPayException(String errCode) {
        IWxPay.ErrCode _code = IWxPay.ErrCode.valueOf(errCode.toUpperCase());
        this.errCode = _code.name();
        this.errMsg = _code.desc();
    }

    public WxPayException(String errCode, String errMsg) {
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
