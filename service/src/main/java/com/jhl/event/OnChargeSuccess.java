package com.jhl.event;

import com.jhl.pojo.biz.Account;

/**
 * Created by hallywu on 16/1/31.
 * 充值成功事件
 */
public interface OnChargeSuccess {

    /**
     * 执行充值成功回调
     * @param money
     * @param account
     * @throws Exception
     */
    public void process(Double money,Account account) throws Exception;

}
