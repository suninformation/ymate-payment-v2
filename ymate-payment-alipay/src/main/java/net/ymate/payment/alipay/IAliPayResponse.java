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

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/7 上午11:59
 * @version 1.0
 */
public interface IAliPayResponse {

    /**
     * 什么也不做的响应数据接口实现
     */
    class NOTHING implements IAliPayResponse {
        public String getCode() {
            throw new UnsupportedOperationException();
        }

        public String getMsg() {
            throw new UnsupportedOperationException();
        }

        public String getSubCode() {
            throw new UnsupportedOperationException();
        }

        public String getSubMsg() {
            throw new UnsupportedOperationException();
        }

        public String getSign() {
            throw new UnsupportedOperationException();
        }

        public void setSign(String sign) {
            throw new UnsupportedOperationException();
        }

        public boolean successful() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * @return 网关返回码
     */
    String getCode();

    /**
     * @return 网关返回码描述
     */
    String getMsg();

    /**
     * @return 业务返回码
     */
    String getSubCode();

    /**
     * @return 业务返回码描述
     */
    String getSubMsg();

    /**
     * @return 签名
     */
    String getSign();

    void setSign(String sign);

    /**
     * @return 业务处理是否成功
     */
    boolean successful();
}
