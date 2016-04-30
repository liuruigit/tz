package com.jhl.service;

import com.jhl.common.sms.BaseSmsConnector;
import com.jhl.constant.ConfigKey;

/**
 * Created by Administrator on 2016/3/17.
 * 消息拼装
 */
public class MsgFactory {

    public enum MsgType{
        INVEST(0),
        CHARGE(1),
        CHARGE_FINISH(2),
        CASH(3),
        CASH_FINISH(4),
        MODIFY_PWD(5),
        MODIFY_TRADE_PWD(6),
        FAIL_TRANSACTION(7);
        private int value;

        MsgType(int val){
            this.value = val;
        }

        public int getValue() {
            return value;
        }
    }

    public static String  buildMsg(MsgType msgType,Object... paras) throws Exception{
        String base = "";
        switch (msgType) {
            case INVEST:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_INVEST);
                break;
            case CHARGE:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_CHARGE);
                break;
            case CHARGE_FINISH:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_CHARGE_FINISH);
                break;
            case CASH:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_CASH);
                break;
            case CASH_FINISH:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_CASH_FINISH);
                break;
            case MODIFY_PWD:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_MODIFY_PWD);
                break;
            case MODIFY_TRADE_PWD:
                base = SysConfig.geteConfigByKey(ConfigKey.MSG_MODIFY_TRADE_PWD);
                break;
            case FAIL_TRANSACTION:
                base = SysConfig.geteConfigByKey(ConfigKey.FAIL_TRANSACTION);
                break;
            default:
                break;
        }
        String msg = BaseSmsConnector.buildSms(base,paras);
        return msg;
    }

}
