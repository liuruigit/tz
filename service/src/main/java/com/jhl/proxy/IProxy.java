package com.jhl.proxy;

import com.jhl.dto.AccountDto;
import com.jhl.event.OnChargeSuccess;
import com.jhl.exception.AppException;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.ChannelOrder;

import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 * 实名，充值，提现，绑卡代理接口
 */
public interface IProxy {

    public static final String JYT_RESP_CODE_SUCCESS = "S0000000";

    /**
     * 实名认证
     * @param account
     * @return
     * @throws AppException
     */
    public String validateAccIdNo(AccountDto account) throws Exception;

    /**
     * 绑定银行卡
     * @param bankCard
     * @param account
     * @return
     * @throws AppException
     */
    public Map<String,String> bindingBankCard(BankCard bankCard, Account account, String type) throws Exception;

    /**
     * 充值
     * @param bankCard
     * @param amount
     * @param account
     * @throws Exception
     */
    public Map<String,String> charge(BankCard bankCard,Double amount,Account account,OnChargeSuccess onChargeSuccess) throws Exception;

    /**
     * 用户申请提现
     * @param channelOrder
     * @return
     * @throws Exception
     */
    public ChannelOrder cash(ChannelOrder channelOrder) throws Exception;
}
