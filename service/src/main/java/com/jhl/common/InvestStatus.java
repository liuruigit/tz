package com.jhl.common;

/**
 * 投资状态
 * Created by amoszhou on 16/1/29.
 */
public enum InvestStatus {

    /**
     * 转让中
     */
    TRANSFER(3),
    /**
     * 转让成功
     */
    TRANSFER_FINISH(4),

    /**
     *申请中
     */
    APPLYING(5),
    /**
     *持有中
     */
    HOLDING(6),
    /**
     *结算中
     */
    SETTLE(7),
    /**
     *已还款
     */
    PAYBACK(8),
    ;
    int value;

    InvestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
