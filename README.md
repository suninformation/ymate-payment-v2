### YMP-Payment v2

基于YMP框架实现的第三方支付接入模块，目前已实现:

|名称|状态|
|---|---|
|支付宝|测试通过|
|微信支付|重构完成，待测试|
|银联支付|整理中|

#### 支付宝 (AliPay)

当前模块已完成支付宝电脑网站和手机网站最新发布的支付能力相关API的封装并测试通过。

- 电脑网站支付API列表 (更新时间: 2017/05/04)

    |接口英文名|接口中文名|API文档|
    |---|---|---|
    |alipay.trade.page.pay|统一收单下单并支付页面接口|[查看文档](https://doc.open.alipay.com/doc2/detail.htm?treeId=270&articleId=105901&docType=1)|
    |alipay.trade.refund|统一收单交易退款接口|[查看文档](https://doc.open.alipay.com/docs/api.htm?docType=4&apiId=759)|
    |alipay.trade.fastpay.refund.query|统一收单交易退款查询接口|[查看文档](https://doc.open.alipay.com/docs/api.htm?docType=4&apiId=1049)|
    |alipay.trade.query|统一收单线下交易查询接口|[查看文档](https://doc.open.alipay.com/docs/api.htm?docType=4&apiId=757)|
    |alipay.trade.close|统一收单交易关闭接口|[查看文档](https://doc.open.alipay.com/docs/api.htm?docType=4&apiId=1058)|
    |alipay.data.dataservice.bill.downloadurl.query|查询对账单下载地址|[查看文档](https://doc.open.alipay.com/docs/api.htm?spm=a219a.7386797.0.0.Q7eDyr&docType=4&apiId=1054)|

- 手机网站支付API列表 (更新时间: 2016/08/12)

    |接口英文名|接口中文名|API文档|
    |---|---|---|
    |alipay.trade.wap.pay|手机网站支付接口|[查看文档](http://doc.open.alipay.com/doc2/detail.htm?treeId=203&articleId=105463&docType=1)|
    |alipay.trade.refund|交易退款接口|[查看文档](https://doc.open.alipay.com/doc2/apiDetail.htm?apiId=759&docType=4)|
    |alipay.trade.fastpay.refund.query|交易退款查询接口|[查看文档](http://doc.open.alipay.com/doc2/apiDetail.htm?docType=4&apiId=1049)|
    |alipay.trade.query|交易查询接口|[查看文档](https://doc.open.alipay.com/doc2/apiDetail.htm?apiId=757&docType=4)|
    |alipay.trade.close|交易关闭接口|[查看文档](https://doc.open.alipay.com/doc2/apiDetail.htm?apiId=1058&docType=4)|
    |alipay.data.dataservice.bill.downloadurl.query|查询账单下载地址接口|[查看文档](https://doc.open.alipay.com/doc2/apiDetail.htm?apiId=1054&docType=4)|

##### Maven包依赖

```
<dependency>
    <groupId>net.ymate.payment</groupId>
    <artifactId>ymate-payment-alipay</artifactId>
    <version>2.0.0</version>
</dependency>
```

##### AliPay使用方法说明

- 实现支付事件处理器接口`net.ymate.payment.alipay.IAliPayEventHandler`，示例代码如下：

    ```
    public class AliPayEventHandler implements IAliPayEventHandler {
    
        private static final Log _LOG = LogFactory.getLog(AliPayEventHandler.class);
    
        /**
         * @param orderId 订单Id
         * @param attach  附加信息
         * @return 创建PC支付请求接口参数对象
         * @throws Exception 可能产生任何异常
         */
        public TradePagePayData buildTradePagePayData(String orderId, String attach) throws Exception {
            TradePagePayData _data = new TradePagePayData(UUIDUtils.UUID(), "99.99", "捐赠订单");
            _data.setPassbackParams(attach);
            //
            return _data;
        }
    
        /**
         * @param orderId 订单Id
         * @param attach  附加信息
         * @return 创建WAP支付请求接口参数对象
         * @throws Exception 可能产生任何异常
         */
        public TradeWapPayData buildTradeWapPayData(String orderId, String attach) throws Exception {
            return new TradeWapPayData(UUIDUtils.UUID(), "99.99", "捐赠订单");
        }
    
        public void onNotifyReceived(AliPayBaseNotify notifyData) throws Exception {
            _LOG.debug("接收到异步通知消息: " + JSON.toJSONString(notifyData));
        }
    
        public IView onReturnCallback(AliPayBaseReturn returnData) throws Exception {
            String _jsonStr = JSON.toJSONString(returnData);
            _LOG.debug("接收到同步通知消息: " + _jsonStr);
            //
            return View.jsonView(_jsonStr);
        }
    
        public void onExceptionCaught(Throwable cause) throws Exception {
            _LOG.error("发生了异常: ", cause);
        }
    }
    ```

- 请求支付，访问URL地址格式如下：

    PC端支付：

    ```
    http://<域名>/payment/alipay/<支付宝APP_ID>/page?state=<订单编号>&attach=<附加信息>
    ```
    
    移动端支付：
    
    ```
    http://<域名>/payment/alipay/<支付宝APP_ID>/wap?state=<订单编号>&attach=<附加信息>
    ```
    
    URL参数说明：
    
    - 支付宝APP_ID：支付宝分配给开发者的应用ID，必选参数；
    
        > 若模块中配置`默认支付宝开发者帐户ID`或使用`默认账户提供者`，则可以直接使用`default`访问;
    
    - 订单编号：业务订单唯一标识，必选参数；
    
        > 该参数将被传递至支付事件处理接口的`buildTradePagePayData`或`buildTradeWapPayData`方法;
    
    - 附加信息：该参数值将被原样返回；

- 异步通知，访问URL地址格式如下：

    ```
    http://<域名>/payment/alipay/notify
    ```

- 同步跳转通知，访问URL地址格式如下：

    ```
    http://<域名>/payment/alipay/callback
    ```


##### AliPay模块接口方法说明

- 创建支付请求：

    ```
    // PC端支付:
    AliPay.get().tradePagePay("<订单编号>", "<附加信息>").build().executeActionForm();
    
    // WAP端支付:
    AliPay.get().tradeWapPay("<订单编号>", "<附加信息>").build().executeActionForm();
    ```

- 统一收单线下交易查询：

    ```
    AliPayTradeQuery.Response _query = AliPay.get()
            .tradeQuery("<支付宝APP_ID>", "<TRADE_NO>", "<OUT_TRADE_NO>")
            .build().execute();
    ```

- 统一收单交易退款：

    ```
    AliPayTradeRefund.Response _refund = AliPay.get()
            .tradeRefund("<支付宝APP_ID>", new TradeRefundData("<OUT_TRADE_NO>", "<TRADE_NO>", "99.00"))
            .build().execute();
    ```

- 统一收单交易退款查询：

    ```
    AliPayTradeRefundQuery.Response _refundQuery = AliPay.get()
            .tradeRefundQuery("<支付宝APP_ID>", "<TRADE_NO>", "<OUT_TRADE_NO>", "<OUT_REQUEST_NO>"))
            .build().execute();
    ```

- 统一收单交易关闭：

    ```
    AliPayTradeClose.Response _close = AliPay.get()
            .tradeClose("<支付宝APP_ID>", "<TRADE_NO>", "<OUT_TRADE_NO>"))
            .build().execute();
    ```

- 查询对账单下载地址：

    ```
    AliPayBillDownloadUrlQuery.Response _download = AliPay.get()
            .billDownloadUrlQuery("<支付宝APP_ID>", "<BILL_TYPE>", "<BILL_DATE>"))
            .build().execute();
    ```


##### AliPay模块配置参数说明

```
#-------------------------------------
# payment.alipay 模块初始化参数
#-------------------------------------
    
# 支付接口网关URL地址, 默认值: https://openapi.alipay.com/gateway.do
ymp.configs.payment.alipay.gateway_url=
    
# 支付宝开发者账户提供者接口实现类, 若未提供则使用默认配置
ymp.configs.payment.alipay.account_provider_class=
    
# 支付事件处理器, 必选参数
ymp.configs.payment.alipay.event_handler_class=
    
# 默认支付宝开发者帐户ID, 默认值: 若采用账户提供者接口默认实现时取值默认应用ID, 否则为空
ymp.configs.payment.alipay.default_account_id=
    
# 禁用报文签名验证(验签), 默认值: false
ymp.configs.payment.alipay.sign_check_disabled=
    
#-------------------------------------
# 默认支付宝开发者账户配置参数
#-------------------------------------
    
# 支付宝分配给开发者的应用ID
ymp.configs.payment.alipay.app_id=
    
# 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
ymp.configs.payment.alipay.sign_type=
    
# 私钥
ymp.configs.payment.alipay.private_key=
    
# 公钥
ymp.configs.payment.alipay.public_key=
    
# 请求使用的编码格式
ymp.configs.payment.alipay.charset=
    
# 返回格式
ymp.configs.payment.alipay.format=
    
# 支付宝服务器主动通知商户服务器里指定的页面HTTP/HTTPS路径
ymp.configs.payment.alipay.notify_url=
    
# 同步返回地址，HTTP/HTTPS开头字符串
ymp.configs.payment.alipay.return_url=
```

#### 微信支付 (WxPay)

当前模块已完成微信支付最新发布的刷卡支付、公众号支付、扫码支付、H5支付、现金红包和企业付款等支付能力相关API的封装。

|接口名称|模块方法|官方文档|
|---|---|---|
|统一下单|WxPay.get().unifiedOrder(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)|
|提交刷卡支付|WxPay.get().microPay(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1)|
|查询订单|WxPay.get().orderQuery(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_2)|
|撤销订单|WxPay.get().reverse(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3)|
|关闭订单|WxPay.get().closeOrder(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3)|
|申请退款|WxPay.get().refund(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_4)|
|查询退款|WxPay.get().refundQuery(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_5)|
|下载对账单|WxPay.get().downloadBill(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_6)|
|支付结果通知|-|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7)|
|转换短链接|WxPay.get().shortUrl(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_9&index=8)|
|授权码查询openid|WxPay.get().authCodeToOpenId(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_13&index=9)|
|发放普通红包|WxPay.get().redPackSend(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3)|
|发放裂变红包|WxPay.get().redPackSendGroup(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_5&index=4)|
|查询红包记录|WxPay.get().redPackInfo(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_6&index=5)|
|企业付款|WxPay.get().mchPay(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2)|
|查询企业付款|WxPay.get().mchPayQuery(...)|[查看文档](https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_3)|

> 点击这里查看完整的[微信支付开发文档](https://pay.weixin.qq.com/wiki/doc/api/index.html)

##### Maven包依赖

```
<dependency>
    <groupId>net.ymate.payment</groupId>
    <artifactId>ymate-payment-wxpay</artifactId>
    <version>2.0.0</version>
</dependency>
```

##### WxPay使用方法说明

- 实现支付事件处理器接口`net.ymate.payment.wxpay.IWxPayEventHandler`，示例代码如下：

    ```
    public class WxPayEventHandler implements IWxPayEventHandler {

        private static final Log _LOG = LogFactory.getLog(WxPayEventHandler.class);
    
        /**
         * @param tradeType 交易类型
         * @param orderId   订单ID
         * @param attach    附加信息
         * @return 构建微信统一支付请求数据对象
         * @throws Exception 可能产生的任何异常
         */
        public WxPayUnifiedOrder buildUnifiedOrderRequest(WxPayAccountMeta accountMeta, IWxPay.TradeType tradeType, String orderId, String attach) throws Exception {
            return new WxPayUnifiedOrder(accountMeta, "捐赠订单", orderId, 9999, "192.168.199.1", "", tradeType.name()).attach(attach);
        }
    
        /**
         * 异步支付通知消息到达事件处理方法，该方法的执行过程中若无任何异常被抛出则视为执行成功并向微信通知服务返回SUCCESS字符串
         *
         * @param notifyData 异步通知对象
         * @throws Exception 可能产生的任何异常
         */
        public void onNotifyReceived(WxPayNotifyResponse notifyData) throws Exception {
            _LOG.debug("接收到异步通知消息: " + notifyData.getOriginalContent());
        }
    
        /**
         * @param orderId 订单ID
         * @return 返回是否需要发启订单状态查询
         * @throws Exception 可能产生的任何异常
         */
        public boolean onReturnCallback(String orderId) throws Exception {
            // 此处判断指定的订单是否需要向微信支付发起状态查询
            return true;
        }
    
        /**
         * 异常处理方法
         *
         * @param cause 产生的异常对象
         * @throws Exception 可能产生的任何异常
         */
        public void onExceptionCaught(Throwable cause) throws Exception {
            _LOG.error("发生了异常: ", cause);
        }
    
        /**
         * @param appId 微信公众号应用ID
         * @return 获取微信JS接口的临时票据
         * @throws Exception 可能产生的任何异常
         */
        public String getJsApiTicket(String appId) throws Exception {
            // 此处请根据需求返回正确的JS接口临时票据
            return "bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA";
        }
    }
    ```

- 请求支付，访问URL地址格式如下：

    微信JS_API模式：

    ```
    http://<域名>/payment/wxpay/<APP_ID>/jsapi?open_id=<OPEN_ID>state=<订单编号>&attach=<附加信息>&debug=<true|false>
    ```
    
    微信Native(原生)支付模式一：
    
    > 商户按固定格式生成链接二维码，用户扫码后调微信会将productid和用户openid发送到商户设置的链接上，商户收到请求生成订单，调用统一支付接口下单提交到微信，微信会返回给商户prepayid。
    
    ```
    http://<域名>/payment/wxpay/<APP_ID>/native/static?state=<订单编号>&attach=<附加信息>
    ```
    
    微信Native(原生)支付模式二：
    
    > 商户生成订单，先调用统一支付接口获取到code_url，此URL直接生成二维码，用户扫码后调起支付。
    
    ```
    http://<域名>/payment/wxpay/<APP_ID>/native/dynamic?open_id=<OPEN_ID>&state=<订单编号>&attach=<附加信息>
    ```
    
    URL参数说明：
    
    - APP_ID：公众账号ID，必选参数；
    
        > 若模块中配置`默认公众账号ID`或使用`默认账户提供者`，则可以直接使用`default`访问;
    
    - OPEN_ID：微信用户唯一标识；
    
    - 订单编号：业务订单唯一标识，必选参数；
    
    - 附加信息：该参数值将被原样返回；

- 异步通知，访问URL地址格式如下：

    ```
    http://<域名>/payment/wxpay/notify
    ```

- 同步跳转通知(订单状态主动检查)，访问URL地址格式如下：

    ```
    http://<域名>/payment/wxpay/<APP_ID>/callback?state=<订单编号>
    ```

- 动态生成支付二维码，访问URL地址格式如下：

    ```
    http://<域名>/payment/wxpay/qrcode/<DATA>?width=300&height=300
    ```

    > 参数说明：
    >
    > - DATA：二维码数据，在调用微信原生支付控制器时将会返回该数据；
    >
    > - width：图片宽度(<=300)，默认值：300；
    >
    > - height：图片高度(<=300)，默认值：300；


##### WxPay模块配置参数说明

```
#-------------------------------------
# payment.wxpay 模块初始化参数
#-------------------------------------

# 微信支付账户提供者接口实现类, 若未提供则使用默认配置
ymp.configs.payment.wxpay.account_provider_class=

# 支付事件处理器, 必选参数
ymp.configs.payment.wxpay.event_handler_class=

# 默认微信支付帐户ID, 默认值: 若采用账户提供者接口默认实现时取值默认应用ID, 否则为空
ymp.configs.payment.wxpay.default_account_id=

# 调用JS支付的JSP视图文件路径, 默认值: wxpay_jsapi
ymp.configs.payment.wxpay.jsapi_view=

# 调用扫码支付的JSP视图文件路径, 默认值: wxpay_native
ymp.configs.payment.wxpay.native_view=

# 禁用报文签名验证(验签), 默认值: false
ymp.configs.payment.wxpay.sign_check_disabled=

#-------------------------------------
# 默认微信支付账户配置参数
#-------------------------------------

# 公众帐号APP_ID
ymp.configs.payment.wxpay.app_id=

# 商户号
ymp.configs.payment.wxpay.mch_id=

# 商号密钥
ymp.configs.payment.wxpay.mch_key=

# 证书文件路径
ymp.configs.payment.wxpay.cert_file_path=

# 异步通知URL路径
ymp.configs.payment.wxpay.notify_url=
```

#### 银联支付 (UnionPay)

> 正在整理中...，请稍后!


#### One More Thing

YMP不仅提供便捷的Web及其它Java项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入 官方QQ群480374360，一起交流学习，帮助YMP成长！

了解更多有关YMP框架的内容，请访问官网：http://www.ymate.net/