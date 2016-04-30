package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.common.sms.BaseSmsConnector;
import com.jhl.common.sms.JLSmsConnector;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.IMsgDao;
import com.jhl.dto.ModelInfoDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Message;
import com.jhl.util.DateUtil;
import com.jhl.util.SessionUtil;
import com.jhl.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/2/26.
 * 消息service
 */
@Service("messageService")
public class MessageService extends BaseService<Message>{

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    AchieveService achieveService;
    @Autowired
    AccountService accountService;
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    /**
     * 投资申请通知
     * 您于{0}投资的{1}：{2}申请投资成功，申请金额：{3}
     * @param no
     * @param type
     * @param amount
     * @param account
     */
    public void sendInvest(String no,String type,double amount,Account account) throws Exception {
        if (account == null)return;
        account.parseAccout();
        String date = DateUtil.format(new Date(),DateUtil.DATE_TIME_FORMAT2);
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+account.getRealName()+"，您好，您的投资申请信息：");
            modelInfoDto.setKeyword1(no);
            modelInfoDto.setKeyword2(type);
            modelInfoDto.setKeyword3(amount+"元");
            modelInfoDto.setKeyword4(date);
            achieveService.sendInvestApply(modelInfoDto,account.getOpenId(),null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.INVEST,date,type,no,amount);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_TITLE_INVEST),content,account);
    }

    /**
     * 提现申请通知
     * @param amount
     * @param status
     * @param account
     */
    public void sendCashApply(double amount, String status, Account account) throws Exception {
        if (account == null)return;
        //account.parseAccout();
        String date = DateUtil.format(new Date(),DateUtil.DATE_TIME_FORMAT2);
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+account.getRealName()+"，您好，您的提现申请信息：");
            modelInfoDto.setKeyword1(amount+"元");
            modelInfoDto.setKeyword2(date);
            modelInfoDto.setKeyword3(status);
            achieveService.sendCashApply(modelInfoDto, account.getOpenId(), null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.CASH, date, status, amount);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_CASH),content,account);
    }

    /**
     * 提现成功通知
     * @param amount
     * @param account
     * @throws Exception
     */
    public void sendCashSuccess(double amount, Account account) throws Exception {
        if (account == null)return;
        account.parseAccout();
        String date = DateUtil.format(new Date(),DateUtil.DATE_TIME_FORMAT2);
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+ account.getRealName()+"，您已经成功提现");
            modelInfoDto.setKeyword1(amount+"元");
            modelInfoDto.setKeyword2(date);
            achieveService.sendCashSuccess(modelInfoDto, account.getOpenId(), null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.CASH_FINISH, account.getRealName(), amount);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_CASH_FINISH),content,account);
    }

    public void sendCashSuccess(double amount, int accId) throws Exception {
        Account account = accountService.queryById(accId);
        if (account == null) {
            logger.warn("通知用户不存在{},{}",amount,accId);
            return;
        }
        sendCashSuccess(amount,account);
    }

    /**
     * 失败通知
     * @param amount
     * @param account
     * @throws Exception
     */
    public void sendFail(double amount,String type, Account account,String reason,String orderNo) throws Exception {
        if (account == null)return;
        account.parseAccout();
        String date = DateUtil.format(new Date(),DateUtil.DATE_TIME_FORMAT2);
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+account.getRealName()+"，您好，您的"+type+"操作失败");
            modelInfoDto.setKeyword1(amount+"元");
            modelInfoDto.setKeyword2(date);
            achieveService.sendCashFail(modelInfoDto, account.getOpenId(), null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.FAIL_TRANSACTION,account.getRealName(),amount,reason,orderNo);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_CASH),content,account);
    }

    /**
     * 充值通知
     * @param amount 单位元
     * @param status
     * @param account
     * @throws Exception
     */
    public void sendRechargeNotice(double amount, String status, Account account) throws Exception {
        if (account == null)return;
        account.parseAccout();
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+account.getRealName()+"，您好");
            modelInfoDto.setKeyword1("账号");
            modelInfoDto.setKeyword2(account.getAccName());
            modelInfoDto.setKeyword3(amount+"元");
            modelInfoDto.setKeyword4(status);
            achieveService.sendRechargeNotice(modelInfoDto, account.getOpenId(), null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.CHARGE, account.getRealName(),amount);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_CHARGE),content,account);
    }

    /**
     * 充值成功通知
     * @param amount
     * @param account
     * @throws Exception
     */
    public void sendRechargeSuccess(double amount, String type, Account account) throws Exception {
        if (account == null)return;
        account.parseAccout();
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+account.getRealName()+"，您好，您充值的金额已成功到账，请查看：");
            modelInfoDto.setKeyword1(amount+"元");
            modelInfoDto.setKeyword2(type);
            achieveService.sendRechargeSuccess(modelInfoDto, account.getOpenId(), null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.CHARGE_FINISH, account.getRealName(),amount);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_CHARGE_FINISH),content,account);
    }

    public void sendRechargeSuccess(String amount, String type, Account account) throws Exception {
        if (account == null)return;
        account.parseAccout();
        if (!Strings.isNullOrEmpty(account.getOpenId())){
            ModelInfoDto modelInfoDto = new ModelInfoDto();
            modelInfoDto.setFirst("尊敬的:"+account.getRealName()+"，您好，您充值的金额已成功到账，请查看：");
            modelInfoDto.setKeyword1(amount+"元");
            modelInfoDto.setKeyword2(type);
            achieveService.sendRechargeSuccess(modelInfoDto, account.getOpenId(), null);
        }
        String content = MsgFactory.buildMsg(MsgFactory.MsgType.CHARGE_FINISH, account.getRealName(),amount);
        asySendSmsAndNotice(SysConfig.geteConfigByKey(ConfigKey.MSG_CHARGE_FINISH),content,account);
    }


    @Autowired
    private IMsgDao iMsgDao;
    BaseSmsConnector smsConnector = new JLSmsConnector();

    public void asySendSmsAndNotice(String title,String smsContent,String sysContent,Account account) throws Exception{
        MsgWorker msgWorker = new MsgWorker(title,smsContent,sysContent,account);
        executorService.execute(msgWorker);
    }

    public void asySendSmsAndNotice( String title, String content, Account account) throws Exception{
        MsgWorker msgWorker = new MsgWorker(title,content,account);
        executorService.execute(msgWorker);
    }

    /**
     * 发送系统消息和短信，内容需自己组装
     * @param title
     * @param smsContent
     * @param sysContent
     * @param account
     * @throws Exception
     */
    public void sendSmsAndNotice(String title,String smsContent,String sysContent,Account account) throws Exception{
        smsConnector.sendSysMsg(account.getMobile(),smsContent);
        Message msg = buildSysMessgae(title,sysContent,account);
        save(msg);
    }

    public String queryMessage() {
        String content = null;
        try {
            List<Map<String, Object>> list =  iMsgDao.queryMessage();
            if(list.size() > 0) {
                for(Map<String, Object> map : list) {
                    if(map != null && map.get("CONTENT") != null) {
                        content = map.get("CONTENT").toString();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("消息查询异常...", e);
            e.printStackTrace();
            content = "暂无提示消息";
        }
        return content;
    }

    public String querySysMessage() {
        String content = null;
        try {
            List<Map<String, Object>> list = iMsgDao.querySysMessage();
            if(list.size() > 0) {
                for(Map<String, Object> map : list) {
                    if(map != null && map.get("BODY") != null) {
                        content = map.get("BODY").toString();
                    }
                }
            }

        } catch (Exception e) {
            logger.error("系统消息查询异常...", e);
            e.printStackTrace();
            content = "暂无提示消息";
        }
        return content;
    }

    private Message buildSysMessgae(String title,String content,Account account){
        Message message = new Message();
        message.setDeleteState(1);
        message.setAccId(account.getId());
        message.setContent(content);
        message.setIsRead(Message.IS_READ_NO);
        message.setSystemMsg(Message.SYS);
        message.setCreateTime(new Date());
        message.setTitle(title);
        return message;
    }

}


class MsgWorker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MsgWorker.class);

    String title;
    String smsContent;
    String sysContent;
    Account account;

    MsgWorker (String title,String smsContent,String sysContent,Account account){
        this.title = title;
        this.smsContent = smsContent;
        this.sysContent = sysContent;
        this.account = account;
    }

    MsgWorker (String title,String content,Account account){
        this.title = title;
        this.sysContent = content;
        this.smsContent = content;
        this.account = account;
    }

    @Override
    public void run() {
        MessageService messageService = SpringContextHolder.getBean("messageService");
        try {
            messageService.sendSmsAndNotice(title,smsContent,sysContent,account);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "发送消息异常",e);
        }
    }
}