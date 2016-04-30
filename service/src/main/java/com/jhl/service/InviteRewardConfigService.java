package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.impl.rmb.InviteConfigDao;
import com.jhl.pojo.biz.AccCoupon;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.InviteRewardConfig;
import com.jhl.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/29.
 */
@Service("inviteRewardConfigService")
public class InviteRewardConfigService extends BaseService<InviteRewardConfig> {
    private static final Logger logger = LoggerFactory.getLogger(InviteRewardConfigService.class);
    @Autowired
    AccountService accountService;
    @Autowired
    AccCouponService accCouponService;
    @Autowired
    InviteConfigDao inviteConfigDao;

    public void reWardRecommendAcc(Account account,double investMoney) throws Exception {
//        String cId = "4";
        String cId = SysConfig.geteConfigByKey(ConfigKey.INVITE_REWARD_COUPON_ID);
        if (Strings.isNullOrEmpty(cId)){
            logger.error("未配置奖励couponID，无法进行邀请投资奖励!");
            return;
        }
        int couponId = Integer.parseInt(cId);
        if (account == null || account.getDeleteState() == 0 || account.getRecommendId() == 0)return;
        InviteRewardConfig config = inviteConfigDao.queryConfig(investMoney);
        if (config == null)return;
        logger.info("邀请奖励选取配置成功：{},{}",config.toString(),account.toString());
        if (config.getLimit() <= (account.getInvestMoney() / 100)) return;
        double perc = Double.parseDouble(config.getPerc());
        Date active = DateUtil.addDay(account.getCreateTime(),Integer.parseInt(config.getDays()));
        if (DateUtil.getDayEnd(active) < DateUtil.getDayEnd(new Date())) return;
        Account recommendAcc = accountService.queryById(account.getRecommendId());
        if(recommendAcc == null || recommendAcc.getDeleteState() == 0)return;
        double rewardMoney = investMoney * perc;
        AccCoupon accCoupon = accCouponService.addAccCoupon(rewardMoney,recommendAcc,couponId);
        logger.info("发送投资奖励成功：{}",accCoupon.toString());
    }


}
