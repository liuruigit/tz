package com.jhl.service;

import com.jhl.dao.IChannelOrderDao;
import com.jhl.dao.InvestOrderDao;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.UnbindingApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/27.
 */
@Service
public class UnBindingService extends BaseService<UnbindingApply> {

    @Autowired
    IChannelOrderDao channelOrderDao;
    @Autowired
    InvestOrderDao investOrderDao;

    public static final int CHECK_ERROR_INVEST = 0;
    public static final int CHECK_ERROR_CASH = 1;
    public static final int CHECK_ERROR_CHARGE= 2;
    public static final int CHECK_AGREE= 3;

    /**
     * 检查用户是否可以解除银行卡绑定
     * @param account
     * @return
     * @throws Exception
     */
    public Integer unBindingCheck(Account account) throws Exception{
        if (channelOrderDao.countUnfinishedChargeOrder(account.getId()) > 0) {
            return CHECK_ERROR_CHARGE;
        }
        if (channelOrderDao.countUnfinishedCashOrder(account.getId()) > 0) {
            return CHECK_ERROR_CASH;
        }
        if (investOrderDao.countInvestOrder(account.getId()) > 0) {
            return CHECK_ERROR_INVEST;
        }
        return CHECK_AGREE;
    }

}
