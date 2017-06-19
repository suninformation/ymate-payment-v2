### YMP-Payment v2

基于YMP框架实现的第三方支付接入模块，目前已实现:

|名称|状态|
|---|---|
|支付宝|测试通过|
|微信支付|重构完成, 待测试|
|银联支付|整理中|；

#### 支付宝 (AliPay)

##### Maven包依赖

```
<dependency>
    <groupId>net.ymate.payment</groupId>
    <artifactId>ymate-payment-alipay</artifactId>
    <version>2.0.0</version>
</dependency>
```

##### 使用方法说明

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


##### 模块接口方法说明

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


##### 模块配置参数说明

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

##### Maven包依赖

```
<dependency>
    <groupId>net.ymate.payment</groupId>
    <artifactId>ymate-payment-wxpay</artifactId>
    <version>2.0.0</version>
</dependency>
```

> 正在整理中...，请稍后!


#### 银联支付 (UnionPay)

> 正在整理中...，请稍后!


#### One More Thing

YMP不仅提供便捷的Web及其它Java项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入 官方QQ群480374360，一起交流学习，帮助YMP成长！

了解更多有关YMP框架的内容，请访问官网：http://www.ymate.net/