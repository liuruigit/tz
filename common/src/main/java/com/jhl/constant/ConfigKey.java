package com.jhl.constant;

/**
 * Created by Administrator on 2016/1/24.
 * 配置KEY
 */
public interface ConfigKey {

    /** 域名 */
    public static String STATIC_APP_DOMAIN = "static.app.domain";
    public static String STATIC_WEB_GEN = "static.web.gen";
    public static String STATIC_APP_PAGE = "static.domain.page";
    public static String SECRET_KEY = "secretKey";
    public static String WEB_GATEWAY = "service.gateway";
    public static String WEE_HOME = "service.home";
    public static String WEB_TOKEN = "service.token";
    public static String CASH_SERVICE_FEE = "cash.fee";
    public static String GEN_IP = "ms.ip";

    /** 短信 */
    public static String SMS_REGIST = "sms.regist";
    public static String SMS_INVEST = "sms.invest";
    public static String SMS_CHARGE = "sms.charge";
    public static String SMS_CASH = "sms.cash";
    public static String SMS_DEFAULT = "sms.default";
    public static String SMS_PWD = "sms.pwd";
    public static String SMS_TRADE_PWD = "sms.trade.pwd";
    public static String SMS_SUPPLIER_MWKJ = "sms.mwkj.url";
    public static String MWKJ_USERID = "mwkj.userid";
    public static String MWKJ_PWD = "mwkj.pwd";

    public static String SMS_SUPPLIER_JL = "sms.jl.url";
    public static String JL_USERID = "jl.userid";
    public static String JL_PWD = "jl.pwd";
    public static String JL_EXTEND = "jl.extend";

    /** 资金托管平台 */
    public static String JYT_AUTH_URL = "jyt.auth.url";
    public static String JYT_TRAN_URL = "jyt.tran.url";
    public static String JYT_ACC = "jyt.acc";
    public static String JYT_BILLING_HOST = "jyt.billing.host";
    public static String JYT_BILLING_PORT = "jyt.billing.port";
    public static String JYT_BILLING_USERNAME = "jyt.billing.username";
    public static String JYT_BILLING_PWD = "jyt.billing.pwd";
    public static String JYT_BILLING_DIR = "jyt.billing.dir";
    public static String JH_TRANS_COMMENT = "jh.tans.comment";


    /** 提现当日限额 */
    public static String JYT_CASH_LIMIT = "jyt.cash.limit";

    /** 消息提示 */
    public static String MSG_INVEST = "invest.sys.msg";
    public static String MSG_CHARGE = "charge.sys.msg";
    public static String MSG_CHARGE_FINISH = "charge.finish.msg";
    public static String MSG_CASH = "cash.sys.msg";
    public static String MSG_CASH_FINISH = "cash.finish.sys.msg";
    public static String MSG_MODIFY_PWD = "pwd.sys.msg";
    public static String MSG_MODIFY_TRADE_PWD = "trade.pwd.sys.msg";
    public static String FAIL_TRANSACTION = "fail.tran";
    /** 消息提示标题 */
    public static String MSG_TITLE_INVEST = "invest.sys.msg";
    public static String MSG_TITLE_CHARGE = "charge.sys.msg";
    public static String MSG_TITLE_CHARGE_FINISH = "charge.finish.sys.msg";
    public static String MSG_TITLE_CASH = "cash.sys.msg";
    public static String MSG_TITLE_CASH_FINISH = "cash.finish.sys.msg";
    public static String MSG_TITLE_MODIFY_PWD = "pwd.sys.msg";
    public static String MSG_TITLE_MODIFY_TRADE_PWD = "trade.pwd.sys.msg";

    /** 安全问题配置 */
    public static String SAFE_QEST_1 = "sq.1";
    public static String SAFE_QEST_2 = "sq.2";
    public static String SAFE_QEST_3 = "sq.3";
    public static String SAFE_QEST_4 = "sq.4";
    public static String SAFE_QEST_5 = "sq.5";
    public static String SAFE_QEST_6 = "sq.6";

    /** 网页资源配置*/
    public static String INVITE_REGIST_PAGE = "invite.regist.page";
    /** 最大交易密码失败次数*/
    public static String MAX_TRADE_COUNT = "max.trade.count";
    public static String JH_CONNECT_URL = "jh.connect.url";//"http://58.0.99.176:8888"
    public static String SYS_JH_TRAN_URL = "sys.jh.tran";//"http://58.0.99.176:8888;

    /** 投资券奖励ID*/
    public static String INVITE_REWARD_COUPON_ID = "invite.coupon.id";

    /**业务配置*/
    public static String INPUT_CONFIG_INVEST = "input.config.invest";
    public static String INPUT_CONFIG_CASH = "input.config.cash";
    public static String INPUT_CONFIG_CHARGE = "input.config.charge";
    public static String CONFIG_WX = "config.wx";
    public static String CONFIG_CONTRACT = "config.contract";

}
