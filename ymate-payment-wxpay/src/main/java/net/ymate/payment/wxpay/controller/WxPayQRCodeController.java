/*
 * Copyright 2007-2107 the original author or authors.
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
package net.ymate.payment.wxpay.controller;

import net.ymate.framework.commons.QRCodeHelper;
import net.ymate.framework.core.util.WebUtils;
import net.ymate.platform.validation.validate.VLength;
import net.ymate.platform.webmvc.annotation.Controller;
import net.ymate.platform.webmvc.annotation.PathVariable;
import net.ymate.platform.webmvc.annotation.RequestMapping;
import net.ymate.platform.webmvc.annotation.RequestParam;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.impl.NullView;

/**
 * @author 刘镇 (suninformation@163.com) on 15/1/4 下午6:11
 * @version 1.0
 */
@Controller
@RequestMapping("/payment/wxpay")
public class WxPayQRCodeController {

    /**
     * @param data   二维码数据
     * @param width  图片宽度(<=300)
     * @param height 图片高度(<=300)
     * @return 动态生成支付二维码
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping("/qrcode/{data}")
    public IView __showQrCode(@PathVariable String data, @VLength(max = 300) @RequestParam(defaultValue = "300") Integer width, @VLength(max = 300) @RequestParam(defaultValue = "300") Integer height) throws Exception {
        String _qrContent = WebUtils.decryptStr(WebContext.getRequest(), data);
        QRCodeHelper.create(_qrContent, width, height).writeToStream(WebContext.getResponse().getOutputStream());
        return new NullView();
    }
}
