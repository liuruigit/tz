package com.jhl.task.month;

import com.jhl.dao.AccountDao;
import com.jhl.pojo.biz.Account;
import com.jhl.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * Created by martin on 16/2/21.
 */
@Component
@Lazy(false)
public class MonthBillingTask implements InitializingBean{

    @Autowired
    BillingService billingService;

    @Autowired
    AccountDao accountDao ;

    @Override
    public void afterPropertiesSet() throws Exception {
        //doBillingForAllUser();
    }

    /**
     * 每个月的1日凌晨3点执行billing任务
     * 0秒 0分 3时 1日 *任意月
     * @throws Exception
     */
    //@Scheduled(cron = "0 0 3 1 * ?")


    @Scheduled(cron = "0 0/1 23 21 * ?")
    public void doBillingForAllUser(){
        log.info("开始统计对账单Billing功能");

        int month = getBillingMonth();

        boolean notFinish = true ;
        int row = 0 ;
        while( notFinish ){
            List<Account> accounts = accountDao.queryAccountByPage( row );
            if(accounts != null && accounts.size() > 0 ){
                for(Account acc : accounts){
                    try {
                        doBilling( month , acc.getId());
                    }catch (Exception e ){
                        log.error("对账单生成失败,用户ID {} " , acc.getId() , e );
                    }
                }
                row = row + accounts.size();
            }else{
                notFinish = false;
            }
        }
    }

    /**
     * 获取上个月的月份值。
     * month获取当前月份, Java JANUARY 从0开始,数据库JANUARY 从1 开始,这里获取的值就是数据库上个月的数据
     * @return
     */
    private int getBillingMonth(){
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if( month ==0 ){
            month = 12 ;    //如果month=0,JANUARY,则上个月需要设置为12月份
        }
        return month;
    }

    private int getBillingYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public void doBilling(int month , int user_id) throws Exception {
        log.info("开始统计用户 {} 账单" , user_id );
        billingService.doBilling(month , user_id );
    }


    Logger log = LoggerFactory.getLogger(MonthBillingTask.class);

    public void test() throws Exception {
        int month = 1 ;
        int user_id = 1 ;
        Long aLong = billingService.queryLastAccMoney( month , user_id);
        Long aLong1 = billingService.sumCashMoney(month, user_id);
        Long aLong2 = billingService.sumFrozenMoney(month, user_id);
        Long aLong3 = billingService.sumIncomeMoney(month, user_id);
        Long aLong4 = billingService.sumInvestMoney(month, user_id);
        Long aLong5 = billingService.sumRechargeMoney(month, user_id);

        billingService.doBilling(month , user_id );

        log.info("month billing month {} acc_money = {} " , month , aLong);
        log.info("month billing month {} acc_money = {} " , month , aLong1);
        log.info("month billing month {} acc_money = {} " , month , aLong2);
        log.info("month billing month {} acc_money = {} " , month , aLong3);
        log.info("month billing month {} acc_money = {} " , month , aLong4);
        log.info("month billing month {} acc_money = {} " , month , aLong5);

        Calendar.getInstance().set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR));
    }
}
