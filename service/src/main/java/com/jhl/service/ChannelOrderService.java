package com.jhl.service;

import com.jhl.common.BalanceChangeType;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.IChannelOrderDao;
import com.jhl.db.SQLOperator;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.Money;
import com.jhl.util.SeqNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hallywu on 15/9/26.
 * 提现Service
 */
@Service(value = "cashOrderService")
public class ChannelOrderService extends BaseService<ChannelOrder>{

    @Autowired
    AccountService accountService;
    @Autowired
    BankCardService bankCardService;
    @Autowired
    IChannelOrderDao dao;

    /**
     * 新建订单,单位元
     * @param userId
     * @param amount
     * @throws Exception
     */
    public ChannelOrder save(int userId, double amount, ChannelOrder.Type type, String tranFlow) throws Exception{
        return save( userId,  amount, type, tranFlow,0);
    }

    public ChannelOrder save(int userId, double amount, ChannelOrder.Type type, String tranFlow,int channel) throws Exception{
        ChannelOrder channelOrder = new ChannelOrder();
        channelOrder.setOrderNo(SeqNoUtil.nextSeqNo(SeqNoUtil.PREFIX_FARM_CHANNEL));

        if (type == ChannelOrder.Type.CASH){
            BankCard bankCard = bankCardService.queryDefaultBank(userId);
            double fee = Double.parseDouble(SysConfig.geteConfigByKey(ConfigKey.CASH_SERVICE_FEE));
            double mon1 = amount - new Money(fee).getCent();//提现金额减去手续费
            channelOrder.setAmount(new Money(mon1).getCent());
            channelOrder.setType(ChannelOrder.Type.CASH.getValue());
            accountService.updateUserBalance(
                    userId,
                    new Money(amount),
                    BalanceChangeType.PRE_WITHDRAW,
                    channelOrder.getOrderNo(),
                    bankCardService.subBankNoTail(bankCard),
                    bankCardService.subBankNoHide(bankCard),
                    bankCard.getBankName(),
                    "申请提现"
            );
        }else {
            channelOrder.setAmount(new Money(amount).getCent());
        }
        channelOrder.setType(type.getValue());
        channelOrder.setUserId(userId);
        channelOrder.setBankId(0);//使用默认银行卡，有提现、充值、投资中的订单，系统不允许换卡
        channelOrder.setStatus(ChannelOrder.STATUS_CREATE);
        channelOrder.setTranFlow(tranFlow);
        channelOrder.setChannel(channel);
        int id = save(channelOrder);
        channelOrder.setId(id);
        return channelOrder;
    }

    public ChannelOrder save(int userId,ChannelOrder.Type type, String tranFlow) throws Exception{
        return save(userId,0,type,tranFlow);
    }

    /**
     * 资管渠道回调
     * @param channelOrder
     * @throws Exception
     */
    @Transactional
    public void callback(ChannelOrder channelOrder) throws Exception{
        update(channelOrder);
        BalanceChangeType type = null;
        String extraMsg = "";
        if(channelOrder.getType() == ChannelOrder.Type.CHARGE.getValue()) {
            type = BalanceChangeType.CHARGE;
            channelOrder.setStatus(ChannelOrder.STATUS_SUCCESS);
            extraMsg = "充值成功";
        }else if(channelOrder.getType() == ChannelOrder.Type.CASH.getValue()) {
            type = BalanceChangeType.WITHDRAW;
            channelOrder.setStatus(ChannelOrder.STATUS_TRANSACTION_SUCCESS);
            extraMsg = "到账成功";
        }
        BankCard bankCard = bankCardService.queryDefaultBank(channelOrder.getUserId());
        accountService.updateUserBalance(
                channelOrder.getUserId(),
                Money.centToYuan(channelOrder.getAmount()),
                type,
                channelOrder.getOrderNo(),
                bankCardService.subBankNoTail(bankCard),
                bankCardService.subBankNoHide(bankCard),
                bankCard.getBankName(),
                extraMsg
        );
    }

    @Transactional
    public void callback_ccb(ChannelOrder channelOrder) throws Exception{
        save(channelOrder);
        BalanceChangeType type = null;
        String extraMsg = "";
        if(channelOrder.getType() == ChannelOrder.Type.CHARGE.getValue()) {
            type = BalanceChangeType.CHARGE;
            channelOrder.setStatus(ChannelOrder.STATUS_SUCCESS);
            extraMsg = "充值成功";
        }else if(channelOrder.getType() == ChannelOrder.Type.CASH.getValue()) {
            type = BalanceChangeType.WITHDRAW;
            channelOrder.setStatus(ChannelOrder.STATUS_TRANSACTION_SUCCESS);
            extraMsg = "到账成功";
        }
        BankCard bankCard = bankCardService.queryDefaultBank(channelOrder.getUserId());
        accountService.updateUserBalance(
                channelOrder.getUserId(),
                Money.centToYuan(channelOrder.getAmount()),
                type,
                channelOrder.getOrderNo(),
                bankCardService.subBankNoTail(bankCard),
                bankCardService.subBankNoHide(bankCard),
                bankCard.getBankName(),
                extraMsg
        );
    }

    /**
     * 查询订单
     * @param tranFlow
     * @return
     * @throws Exception
     */
    public ChannelOrder queryChannelOrderByTransflow(String tranFlow) throws Exception{
        return dao.queryChannelOrderByTransflow(tranFlow);
    }

    /**
     * 查询充值订单的数量
     * @param transflow
     * @return
     * @throws Exception
     */
    public Integer queryChannelOrder(String transflow) throws Exception{
        return dao.queryChannelOrder(transflow);
    }

    /**
     * 查询用户的提现记录列表
     * @param account
     * @return
     * @throws Exception
     */
    public PaginationResult<ChannelOrder> getCashOrders(Account account,PageInfo pageInfo) throws Exception{
//        SQLCondition sqlCondition1 = new SQLCondition("refundTime", SQLOperator.lessEqualThan,DateUtil.addDay(new Date(),30));
//        SQLCondition sqlCondition2 = new SQLCondition("status", SQLOperator.equal,RefundOrder.STATUS_CREATE);
        SQLCondition sqlCondition3 = new SQLCondition("userId", SQLOperator.equal,account.getId());
        List<SQLCondition> conditions = new ArrayList<SQLCondition>();
//        conditions.add(sqlCondition1);
//        conditions.add(sqlCondition2);
        conditions.add(sqlCondition3);
        return query(conditions, pageInfo,"order by create_time desc");
    }


}
