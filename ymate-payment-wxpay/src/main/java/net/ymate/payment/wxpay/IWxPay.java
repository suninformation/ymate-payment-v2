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

import net.ymate.payment.wxpay.request.*;
import net.ymate.platform.core.YMP;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/06/15 下午 13:12
 * @version 1.0
 */
public interface IWxPay {

    String MODULE_NAME = "payment.wxpay";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    IWxPayModuleCfg getModuleCfg();

    /**
     * @return 返回模块是否已初始化
     */
    boolean isInited();

    /**
     * @param appId          公众账号ID
     * @param body           商品描述
     * @param outTradeNo     商户订单号
     * @param totalFee       总金额
     * @param spbillCreateIp 终端IP
     * @param notifyUrl      通知地址
     * @param tradeType      交易类型
     * @return 统一下单
     * @throws Exception 可能产生的任何异常
     */
    WxPayUnifiedOrder unifiedOrder(String appId, String body, String outTradeNo, Integer totalFee, String spbillCreateIp, String notifyUrl, TradeType tradeType) throws Exception;

    /**
     * @param appId      公众账号ID
     * @param outTradeNo 商户订单号
     * @return 关闭订单
     * @throws Exception 可能产生的任何异常
     */
    WxPayCloseOrder closeOrder(String appId, String outTradeNo) throws Exception;

    /**
     * @param appId    公众账号ID
     * @param billData 对账单日期
     * @param billType 账单类型
     * @return 下载对账单
     * @throws Exception 可能产生的任何异常
     */
    WxPayDownloadBill downloadBill(String appId, String billData, IWxPay.BillType billType) throws Exception;

    /**
     * @param appId          公众账号ID
     * @param partnerTradeNo 商户订单号
     * @param openId         微信用户唯一标识
     * @param checkName      校验用户姓名选项
     * @param amount         金额
     * @param desc           企业付款描述信息
     * @param spbillCreateIp Ip地址
     * @return 企业付款
     * @throws Exception 可能产生的任何异常
     */
    WxPayMchPay mchPay(String appId, String partnerTradeNo, String openId, boolean checkName, Integer amount, String desc, String spbillCreateIp) throws Exception;

    /**
     * @param appId          公众账号ID
     * @param partnerTradeNo 商户订单号
     * @return 查询企业付款
     * @throws Exception 可能产生的任何异常
     */
    WxPayMchPayQuery mchPayQuery(String appId, String partnerTradeNo) throws Exception;

    /**
     * @param appId         公众账号ID
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @return 查询订单
     * @throws Exception 可能产生的任何异常
     */
    WxPayOrderQuery orderQuery(String appId, String transactionId, String outTradeNo) throws Exception;

    /**
     * @param appId       公众账号ID
     * @param mchBillNo   商户订单号
     * @param sendName    商户名称
     * @param reOpenId    微信用户唯一标识
     * @param totalAmount 总金额
     * @param totalNum    红包发放总人数
     * @param wishing     红包祝福语
     * @param clientIp    Ip地址
     * @param actName     活动名称
     * @param remark      备注
     * @return 发送普通红包
     * @throws Exception 可能产生的任何异常
     */
    WxPayRedPackSend redPackSend(String appId, String mchBillNo, String sendName, String reOpenId, Integer totalAmount, Integer totalNum, String wishing, String clientIp, String actName, String remark) throws Exception;

    /**
     * @param appId       公众账号ID
     * @param mchBillNo   商户订单号
     * @param sendName    商户名称
     * @param reOpenId    微信用户唯一标识
     * @param totalAmount 总金额
     * @param totalNum    红包发放总人数
     * @param wishing     红包祝福语
     * @param clientIp    Ip地址
     * @param actName     活动名称
     * @param remark      备注
     * @return 发放裂变红包
     * @throws Exception 可能产生的任何异常
     */
    WxPayRedPackSendGroup redPackSendGroup(String appId, String mchBillNo, String sendName, String reOpenId, Integer totalAmount, Integer totalNum, String wishing, String clientIp, String actName, String remark) throws Exception;

    /**
     * @param appId     公众账号ID
     * @param mchBillNo 商户订单号
     * @return 查询红包记录
     * @throws Exception 可能产生的任何异常
     */
    WxPayRedPackInfo redPackInfo(String appId, String mchBillNo) throws Exception;

    /**
     * @param appId         公众账号ID
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param totalFee      总金额
     * @param refundFee     退款金额
     * @return 申请退款
     * @throws Exception 可能产生的任何异常
     */
    WxPayRefund refund(String appId, String transactionId, String outTradeNo, String outRefundNo, Integer totalFee, Integer refundFee) throws Exception;

    /**
     * @param appId         公众账号ID
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param refundId      微信退款单号
     * @return 查询退款
     * @throws Exception 可能产生的任何异常
     */
    WxPayRefundQuery refundQuery(String appId, String transactionId, String outTradeNo, String outRefundNo, String refundId) throws Exception;

    /**
     * @param appId   公众账号ID
     * @param longUrl URL链接
     * @return 转换短链接
     * @throws Exception 可能产生的任何异常
     */
    WxPayShortUrl shortUrl(String appId, String longUrl) throws Exception;

    /**
     * 账单类型
     */
    enum BillType {
        ALL, SUCCESS, REFUND, RECHARGE_REFUND;
    }

    /**
     * 返回状态码
     */
    enum ReturnCode {
        SUCCESS, FAIL;
    }

    /**
     * 业务结果
     */
    enum ResultCode {
        SUCCESS, FAIL;
    }

    /**
     * 业务异常状态码
     */
    enum ErrCode {

        NOAUTH("商户无此接口权限"),
        NOTENOUGH("余额不足"),
        ORDERPAID("商户订单已支付"),
        ORDERCLOSED("订单已关闭"),
        SYSTEMERROR("系统错误"),
        APPID_NOT_EXIST("APPID不存在"),
        MCHID_NOT_EXIST("MCH_ID不存在"),
        APPID_MCHID_NOT_MATCH("APPID和MCH_ID不匹配"),
        LACK_PARAMS("缺少参数"),
        OUT_TRADE_NO_USED("商户订单号重复"),
        SIGNERROR("签名错误"),
        XML_FORMAT_ERROR("XML格式错误"),
        REQUIRE_POST_METHOD("请使用POST方法"),
        POST_DATA_EMPTY("POST数据为空"),
        NOT_UTF8("编码格式错误"),
        //
        ORDERNOTEXIST("此交易订单号不存在"),
        //
        REFUNDNOTEXIST("退款订单查询失败"),
        INVALID_TRANSACTIONID("无效TRANSACTION_ID"),
        PARAM_ERROR("参数错误"),
        //
        CA_ERROR("请求未携带证书，或请求携带的证书出错"),
        SIGN_ERROR("商户签名错误"),
        NO_AUTH("没有权限"),
        FREQ_LIMIT("受频率限制"),
        NOT_FOUND("指定单号数据不存在"),
        //
        AMOUNT_LIMIT("付款金额不能小于最低限额"),
        OPENID_ERROR("OPENID错误"),
        NAME_MISMATCH("姓名校验出错"),
        FATAL_ERROR("两次请求参数不一致"),
        V2_ACCOUNT_SIMPLE_BAN("无法给非实名用户付款"),
        //
        BIZERR_NEED_RETRY("退款业务流程错误，需要商户触发重试来解决"),
        TRADE_OVERDUE("订单已经超过退款期限"),
        USER_ACCOUNT_ABNORMAL("退款请求失败"),
        INVALID_REQ_TOO_MUCH("无效请求过多"),
        FREQUENCY_LIMITED("频率限制");

        private String desc;

        ErrCode(String desc) {
            this.desc = desc;
        }

        public String desc() {
            return this.desc;
        }
    }

    /**
     * 银行类型
     */
    enum BlankType {
        ICBC_DEBIT("工商银行(借记卡)"),
        ICBC_CREDIT("工商银行(信用卡)"),
        ABC_DEBIT("农业银行(借记卡)"),
        ABC_CREDIT("农业银行(信用卡)"),
        PSBC_DEBIT("邮政储蓄银行(借记卡)"),
        PSBC_CREDIT("邮政储蓄银行(信用卡)"),
        CCB_DEBIT("建设银行(借记卡)"),
        CCB_CREDIT("建设银行(信用卡)"),
        CMB_DEBIT("招商银行(借记卡)"),
        CMB_CREDIT("招商银行(信用卡)"),
        BOC_DEBIT("中国银行(借记卡)"),
        BOC_CREDIT("中国银行(信用卡)"),
        COMM_DEBIT("交通银行(借记卡)"),
        COMM_CREDIT("交通银行(信用卡)"),
        SPDB_DEBIT("浦发银行(借记卡)"),
        SPDB_CREDIT("浦发银行(信用卡)"),
        GDB_DEBIT("广发银行(借记卡)"),
        GDB_CREDIT("广发银行(信用卡)"),
        CMBC_DEBIT("民生银行(借记卡)"),
        CMBC_CREDIT("民生银行(信用卡)"),
        PAB_DEBIT("平安银行(借记卡)"),
        PAB_CREDIT("平安银行(信用卡)"),
        CEB_DEBIT("光大银行(借记卡)"),
        CEB_CREDIT("光大银行(信用卡)"),
        CIB_DEBIT("兴业银行(借记卡)"),
        CIB_CREDIT("兴业银行(信用卡)"),
        CITIC_DEBIT("中信银行(借记卡)"),
        CITIC_CREDIT("中信银行(信用卡)"),
        BOSH_DEBIT("上海银行(借记卡)"),
        BOSH_CREDIT("上海银行(信用卡)"),
        CRB_DEBIT("华润银行(借记卡)"),
        HZB_DEBIT("杭州银行(借记卡)"),
        HZB_CREDIT("杭州银行(信用卡)"),
        BSB_DEBIT("包商银行(借记卡)"),
        BSB_CREDIT("包商银行(信用卡)"),
        CQB_DEBIT("重庆银行(借记卡)"),
        SDEB_DEBIT("顺德农商行(借记卡)"),
        SZRCB_DEBIT("深圳农商银行(借记卡)"),
        SZRCB_CREDIT("深圳农商银行(信用卡)"),
        HRBB_DEBIT("哈尔滨银行(借记卡)"),
        BOCD_DEBIT("成都银行(借记卡)"),
        GDNYB_DEBIT("南粤银行(借记卡)"),
        GDNYB_CREDIT("南粤银行(信用卡)"),
        GZCB_DEBIT("广州银行(借记卡)"),
        GZCB_CREDIT("广州银行(信用卡)"),
        JSB_DEBIT("江苏银行(借记卡)"),
        JSB_CREDIT("江苏银行(信用卡)"),
        NBCB_DEBIT("宁波银行(借记卡)"),
        NBCB_CREDIT("宁波银行(信用卡)"),
        NJCB_DEBIT("南京银行(借记卡)"),
        QHNX_DEBIT("青海农信(借记卡)"),
        ORDOSB_CREDIT("鄂尔多斯银行(信用卡)"),
        ORDOSB_DEBIT("鄂尔多斯银行(借记卡)"),
        BJRCB_CREDIT("北京农商(信用卡)"),
        BHB_DEBIT("河北银行(借记卡)"),
        BGZB_DEBIT("贵州银行(借记卡)"),
        BEEB_DEBIT("鄞州银行(借记卡)"),
        PZHCCB_DEBIT("攀枝花银行(借记卡)"),
        QDCCB_CREDIT("青岛银行(信用卡)"),
        QDCCB_DEBIT("青岛银行(借记卡)"),
        SHINHAN_DEBIT("新韩银行(借记卡)"),
        QLB_DEBIT("齐鲁银行(借记卡)"),
        QSB_DEBIT("齐商银行(借记卡)"),
        ZZB_DEBIT("郑州银行(借记卡)"),
        CCAB_DEBIT("长安银行(借记卡)"),
        RZB_DEBIT("日照银行(借记卡)"),
        SCNX_DEBIT("四川农信(借记卡)"),
        BEEB_CREDIT("鄞州银行(信用卡)"),
        SDRCU_DEBIT("山东农信(借记卡)"),
        BCZ_DEBIT("沧州银行(借记卡)"),
        SJB_DEBIT("盛京银行(借记卡)"),
        LNNX_DEBIT("辽宁农信(借记卡)"),
        JUFENGB_DEBIT("临朐聚丰村镇银行(借记卡)"),
        ZZB_CREDIT("郑州银行(信用卡)"),
        JXNXB_DEBIT("江西农信(借记卡)"),
        JZB_DEBIT("晋中银行(借记卡)"),
        JZCB_CREDIT("锦州银行(信用卡)"),
        JZCB_DEBIT("锦州银行(借记卡)"),
        KLB_DEBIT("昆仑银行(借记卡)"),
        KRCB_DEBIT("昆山农商(借记卡)"),
        KUERLECB_DEBIT("库尔勒市商业银行(借记卡)"),
        LJB_DEBIT("龙江银行(借记卡)"),
        NYCCB_DEBIT("南阳村镇银行(借记卡)"),
        LSCCB_DEBIT("乐山市商业银行(借记卡)"),
        LUZB_DEBIT("柳州银行(借记卡)"),
        LWB_DEBIT("莱商银行(借记卡)"),
        LYYHB_DEBIT("辽阳银行(借记卡)"),
        LZB_DEBIT("兰州银行(借记卡)"),
        MINTAIB_CREDIT("民泰银行(信用卡)"),
        MINTAIB_DEBIT("民泰银行(借记卡)"),
        NCB_DEBIT("宁波通商银行(借记卡)"),
        NMGNX_DEBIT("内蒙古农信(借记卡)"),
        XAB_DEBIT("西安银行(借记卡)"),
        WFB_CREDIT("潍坊银行(信用卡)"),
        WFB_DEBIT("潍坊银行(借记卡)"),
        WHB_CREDIT("威海商业银行(信用卡)"),
        WHB_DEBIT("威海市商业银行(借记卡)"),
        WHRC_CREDIT("武汉农商(信用卡)"),
        WHRC_DEBIT("武汉农商行(借记卡)"),
        WJRCB_DEBIT("吴江农商行(借记卡)"),
        WLMQB_DEBIT("乌鲁木齐银行(借记卡)"),
        WRCB_DEBIT("无锡农商(借记卡)"),
        WZB_DEBIT("温州银行(借记卡)"),
        XAB_CREDIT("西安银行(信用卡)"),
        WEB_DEBIT("微众银行(借记卡)"),
        XIB_DEBIT("厦门国际银行(借记卡)"),
        XJRCCB_DEBIT("新疆农信银行(借记卡)"),
        XMCCB_DEBIT("厦门银行(借记卡)"),
        YNRCCB_DEBIT("云南农信(借记卡)"),
        YRRCB_CREDIT("黄河农商银行(信用卡)"),
        YRRCB_DEBIT("黄河农商银行(借记卡)"),
        YTB_DEBIT("烟台银行(借记卡)"),
        ZJB_DEBIT("紫金农商银行(借记卡)"),
        ZJLXRB_DEBIT("兰溪越商银行(借记卡)"),
        ZJRCUB_CREDIT("浙江农信(信用卡)"),
        AHRCUB_DEBIT("安徽省农村信用社联合社(借记卡)"),
        BCZ_CREDIT("沧州银行(信用卡)"),
        SRB_DEBIT("上饶银行(借记卡)"),
        ZYB_DEBIT("中原银行(借记卡)"),
        ZRCB_DEBIT("张家港农商行(借记卡)"),
        SRCB_CREDIT("上海农商银行(信用卡)"),
        SRCB_DEBIT("上海农商银行(借记卡)"),
        ZJTLCB_DEBIT("浙江泰隆银行(借记卡)"),
        SUZB_DEBIT("苏州银行(借记卡)"),
        SXNX_DEBIT("山西农信(借记卡)"),
        SXXH_DEBIT("陕西信合(借记卡)"),
        ZJRCUB_DEBIT("浙江农信(借记卡)"),
        AE_CREDIT("AE(信用卡)"),
        TACCB_CREDIT("泰安银行(信用卡)"),
        TACCB_DEBIT("泰安银行(借记卡)"),
        TCRCB_DEBIT("太仓农商行(借记卡)"),
        TJBHB_CREDIT("天津滨海农商行(信用卡)"),
        TJBHB_DEBIT("天津滨海农商行(借记卡)"),
        TJB_DEBIT("天津银行(借记卡)"),
        TRCB_DEBIT("天津农商(借记卡)"),
        TZB_DEBIT("台州银行(借记卡)"),
        URB_DEBIT("联合村镇银行(借记卡)"),
        DYB_CREDIT("东营银行(信用卡)"),
        CSRCB_DEBIT("常熟农商银行(借记卡)"),
        CZB_CREDIT("浙商银行(信用卡)"),
        CZB_DEBIT("浙商银行(借记卡)"),
        CZCB_CREDIT("稠州银行(信用卡)"),
        CZCB_DEBIT("稠州银行(借记卡)"),
        DANDONGB_CREDIT("丹东银行(信用卡)"),
        DANDONGB_DEBIT("丹东银行(借记卡)"),
        DLB_CREDIT("大连银行(信用卡)"),
        DLB_DEBIT("大连银行(借记卡)"),
        DRCB_CREDIT("东莞农商银行(信用卡)"),
        DRCB_DEBIT("东莞农商银行(借记卡)"),
        CSRCB_CREDIT("常熟农商银行(信用卡)"),
        DYB_DEBIT("东营银行(借记卡)"),
        DYCCB_DEBIT("德阳银行(借记卡)"),
        FBB_DEBIT("富邦华一银行(借记卡)"),
        FDB_DEBIT("富滇银行(借记卡)"),
        FJHXB_CREDIT("福建海峡银行(信用卡)"),
        FJHXB_DEBIT("福建海峡银行(借记卡)"),
        FJNX_DEBIT("福建农信银行(借记卡)"),
        FUXINB_DEBIT("阜新银行(借记卡)"),
        BOCDB_DEBIT("承德银行(借记卡)"),
        JSNX_DEBIT("江苏农商行(借记卡)"),
        BOLFB_DEBIT("廊坊银行(借记卡)"),
        CCAB_CREDIT("长安银行(信用卡)"),
        CBHB_DEBIT("渤海银行(借记卡)"),
        CDRCB_DEBIT("成都农商银行(借记卡)"),
        BYK_DEBIT("营口银行(借记卡)"),
        BOZ_DEBIT("张家口市商业银行(借记卡)"),
        CFT("零钱"),
        BOTSB_DEBIT("唐山银行(借记卡)"),
        BOSZS_DEBIT("石嘴山银行(借记卡)"),
        BOSXB_DEBIT("绍兴银行(借记卡)"),
        BONX_DEBIT("宁夏银行(借记卡)"),
        BONX_CREDIT("宁夏银行(信用卡)"),
        GDHX_DEBIT("广东华兴银行(借记卡)"),
        BOLB_DEBIT("洛阳银行(借记卡)"),
        BOJX_DEBIT("嘉兴银行(借记卡)"),
        BOIMCB_DEBIT("内蒙古银行(借记卡)"),
        BOHN_DEBIT("海南银行(借记卡)"),
        BOD_DEBIT("东莞银行(借记卡)"),
        CQRCB_CREDIT("重庆农商银行(信用卡)"),
        CQRCB_DEBIT("重庆农商银行(借记卡)"),
        CQTGB_DEBIT("重庆三峡银行(借记卡)"),
        BOD_CREDIT("东莞银行(信用卡)"),
        CSCB_DEBIT("长沙银行(借记卡)"),
        BOB_CREDIT("北京银行(信用卡)"),
        GDRCU_DEBIT("广东农信银行(借记卡)"),
        BOB_DEBIT("北京银行(借记卡)"),
        HRXJB_DEBIT("华融湘江银行(借记卡)"),
        HSBC_DEBIT("恒生银行(借记卡)"),
        HSB_CREDIT("徽商银行(信用卡)"),
        HSB_DEBIT("徽商银行(借记卡)"),
        HUNNX_DEBIT("湖南农信(借记卡)"),
        HUSRB_DEBIT("湖商村镇银行(借记卡)"),
        HXB_CREDIT("华夏银行(信用卡)"),
        HXB_DEBIT("华夏银行(借记卡)"),
        HNNX_DEBIT("河南农信(借记卡)"),
        BNC_DEBIT("江西银行(借记卡)"),
        BNC_CREDIT("江西银行(信用卡)"),
        BJRCB_DEBIT("北京农商行(借记卡)"),
        JCB_DEBIT("晋城银行(借记卡)"),
        JJCCB_DEBIT("九江银行(借记卡)"),
        JLB_DEBIT("吉林银行(借记卡)"),
        JLNX_DEBIT("吉林农信(借记卡)"),
        JNRCB_DEBIT("江南农商(借记卡)"),
        JRCB_DEBIT("江阴农商行(借记卡)"),
        JSHB_DEBIT("晋商银行(借记卡)"),
        HAINNX_DEBIT("海南农信(借记卡)"),
        GLB_DEBIT("桂林银行(借记卡)"),
        GRCB_CREDIT("广州农商银行(信用卡)"),
        GRCB_DEBIT("广州农商银行(借记卡)"),
        GSB_DEBIT("甘肃银行(借记卡)"),
        GSNX_DEBIT("甘肃农信(借记卡)"),
        GXNX_DEBIT("广西农信(借记卡)"),
        GYCB_CREDIT("贵阳银行(信用卡)"),
        GYCB_DEBIT("贵阳银行(借记卡)"),
        GZNX_DEBIT("贵州农信(借记卡)"),
        HAINNX_CREDIT("海南农信(信用卡)"),
        HKB_DEBIT("汉口银行(借记卡)"),
        HANAB_DEBIT("韩亚银行(借记卡)"),
        HBCB_CREDIT("湖北银行(信用卡)"),
        HBCB_DEBIT("湖北银行(借记卡)"),
        HBNX_CREDIT("湖北农信(信用卡)"),
        HBNX_DEBIT("湖北农信(借记卡)"),
        HDCB_DEBIT("邯郸银行(借记卡)"),
        HEBNX_DEBIT("河北农信(借记卡)"),
        HFB_DEBIT("恒丰银行(借记卡)"),
        HKBEA_DEBIT("东亚银行(借记卡)"),
        JCB_CREDIT("JCB(信用卡)"),
        MASTERCARD_CREDIT("MASTERCARD(信用卡)"),
        VISA_CREDIT("VISA(信用卡)");

        private String desc;

        BlankType(String desc) {
            this.desc = desc;
        }

        public String desc() {
            return this.desc;
        }
    }

    /**
     * 货币类型
     */
    enum FeeType {
        CNY
    }

    /**
     * 交易类型
     */
    enum TradeType {
        JSAPI, NATIVE, APP, MICROPAY;
    }

    /**
     * 交易状态
     */
    enum TradeState {
        SUCCESS, REFUND, NOTPAY, CLOSED, REVOKED, USERPAYING, PAYERROR;
    }

    /**
     * 转账状态
     */
    enum Status {
        SUCCESS, FAILED, PROCESSING;
    }

    /**
     * 代金券类型
     */
    enum CouponType {
        CASH, NO_CASH;
    }

    /**
     * 指定支付方式
     */
    enum LimitPay {
        NO_CREDIT
    }

    /**
     * 常量
     */
    interface Const {

        /**
         * 公众账号ID
         */
        String APP_ID = "appid";

        String MCH_ID = "mch_id";

        String MCH_KEY = "mch_key";

        String NONCE_STR = "nonce_str";

        String DEVICE_INFO = "device_info";

        String SIGN = "sign";

        String SIGN_TYPE = "sign_type";

        String NOTIFY_URL = "notify_url";

        String SIGN_TYPE_MD5 = "MD5";

        String RETURN_CODE = "return_code";

        String RETURN_MSG = "return_msg";

        String RESULT_CODE = "result_code";

        String ERR_CODE = "err_code";

        String ERR_CODE_DES = "err_code_des";

        /**
         * 商户订单号
         */
        String OUT_TRADE_NO = "out_trade_no";
    }
}