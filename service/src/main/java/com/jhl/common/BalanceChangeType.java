package com.jhl.common;

/**
 * 帐户资金的变化类型
 * Created by amoszhou on 16/1/31.
 */
public enum BalanceChangeType {

    CHARGE("充值"),

    PRE_INVEST("付款"),

    INVEST("付款"),

    TRAN("转让"),

    Settlement("收款"),

    PRE_WITHDRAW("提现"),

    WITHDRAW("提现"),

    INVEST_INCOME("收款");


    String val;

    BalanceChangeType(String val){
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public String getType(BalanceChangeType type) {
        switch (type){
            case CHARGE:
                return "0";
            case PRE_INVEST:
                return "1";
            case INVEST:
                return "1";
            case TRAN:
                return "0";
            case Settlement:
                return "0";
            case PRE_WITHDRAW:
                return "1";
            case WITHDRAW:
                return "1";
            default:
                return "";
        }
    }
}
