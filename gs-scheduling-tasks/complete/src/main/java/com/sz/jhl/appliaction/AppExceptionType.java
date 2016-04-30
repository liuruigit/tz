package com.sz.jhl.appliaction;

/**
 * Created by amoszhou on 16/1/31.
 */
public enum AppExceptionType {

    INVEST_ERROR_STATUS("INVEST_ERROR_STATUS", "投资状态错误"),
    INVEST_LESS_THAN_LIMIT("LESS_THAN_LIMIT", "投资金额小于项目要求的最小投资金额"),
    INVEST_INVALID_INPUT("INVEST_INVALID_INPUT", "投资金额不是步长的整数倍"),
    INVEST_MORE_THAN_LIMIT("LESS_THAN_LIMIT", "投资金额大于项目限制的最大投资金额"),
    INVEST_PROJECT_NOT_EXISTS("NOT_EXISTS_PROJECT", "不存在的项目"),
    INVEST_PROJECT_NOT_INVESTING("NOT_INVESTING", "项目不在融资阶段"),
    INVEST_PROJECT_BALANCE_NOT_ENOUGH("PROJECT_BALANCE_NOT_ENOUGH", "项目可购余额不足"),
    INVEST_BALANCE_NOT_ENOUGH("BALANCE_NOT_ENOUGH", "帐户资金不足"),
    INVEST_ACCOUNT_EXCEPTION("ACCOUNT_EXCEPTION", "帐户异常,请联系客服人员"),
    INVEST_VIP_LIMIT_EXCEPTION("INVEST_VIP_LIMIT_EXCEPTION", "帐户异常,请联系客服人员"),
    TRANSFER_DISCOUNT_NOT_EXISTS("TRANSFER_DISCOUNT_NOT_EXISTS", "转让折扣不存在"),
    TRANSFER_AMOUNT_ERROR("TRANSFER_AMOUNT_ERROR", "转让金额正确");


    private String code;
    private String message;

    AppExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
