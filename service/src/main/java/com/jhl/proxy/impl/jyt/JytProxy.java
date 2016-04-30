package com.jhl.proxy.impl.jyt;

import com.jhl.dto.AccountDto;
import com.jhl.event.OnChargeSuccess;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.proxy.IProxy;
import com.jhl.service.ChannelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
@Service("jytProxy")
public class JytProxy implements IProxy {

    @Autowired
    ChannelOrderService channelOrderService;
    @Autowired
    JytBinding jytBinding;
    @Autowired
    JytPay jytPay;
    @Autowired
    JytCash jytCash;


    @Override
    public String validateAccIdNo(AccountDto dto) throws Exception {
        return JytValidate.validate(dto);
    }

    @Override
    public Map<String,String> bindingBankCard(BankCard bankCard, Account account, String type) throws Exception {
        return  jytBinding.binding(bankCard.getBankCardNo(),bankCard.getMobile(),type,account);
    }

    @Override
    public Map<String,String> charge(BankCard bankCard, Double amount, Account account, OnChargeSuccess onChargeSuccess) throws Exception {
        return jytPay.charge(bankCard,amount,account,onChargeSuccess);
    }

    @Override
    public ChannelOrder cash(ChannelOrder channelOrder) throws Exception {
        return jytCash.cash(channelOrder);
    }
}
