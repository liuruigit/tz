package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.dao.BankDao;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.BankRule;
import com.jhl.util.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 15/9/26.
 */
@Service(value = "bankCardService")
public class BankCardService extends BaseService<BankCard> {

    @Autowired
    AccountService accountService;
    @Autowired
    BillingService billingService;
    @Autowired
    BankDao bankDao;

    private List<Map<String, Object>> bankRule = new ArrayList<Map<String, Object>>();

    /**
     * 缓存银行卡规则
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getRules() throws Exception{
        if (bankRule == null) {
            initBankRule();
            return bankRule;
        }
        return bankRule;
    }

    public void initBankRule() throws Exception {
        bankRule = bankDao.initBankRule();
    }

    public void unbinding(BankCard bankCard) throws Exception {
        bankDao.unbinding(bankCard);
    }

    public BankCard queryCardByNo(String no,int userId) throws Exception {
        return bankDao.queryUserByName(no,userId);
    }

    public BankCard queryCardByNo(String no) throws Exception {
        BankCard bankCard = bankDao.queryUserByName(DESUtil.getEncString(no));
//        BankCard bankCard = bankDao.queryUserByName(no);
        if (bankCard == null) return null;
        bankCard.parseBank();
        return bankCard;
    }

    public BankCard queryDefaultBank(int userId) throws Exception {
        BankCard bankCard = bankDao.queryDefaultBank(userId);
        if (bankCard == null) return null;
        bankCard.parseBank();
        return bankCard;
    }

    /**
     * 充值通道限额检查
     * @param amount
     * @param bankCard
     * @return
     * @throws Exception
     */
    public boolean checkBankRule(double amount,BankCard bankCard) throws Exception{
        if (bankCard == null || Strings.isNullOrEmpty(bankCard.getBankName()) || amount <= 0)return false;
        BankRule bankRule = queryBankRule(bankCard.getBankName());
        if (bankRule == null) return false;
        Double todayChargeAmount = billingService.sumCharge(bankCard.getUserId());
        return amount <= bankRule.getLimit() && (amount + todayChargeAmount / 100) <= bankRule.getDayLimit();
    }

    public BankRule queryBankRule(String bankName) throws Exception {
        return bankDao.queryBankRuleByBankName(bankName);
    }

    public Map<String, Object> queryDefaultBankMap(int id) throws Exception {
        return bankDao.queryDefaultBankMap(id);
    }

    public String subBankNoTail(BankCard bankCard) throws Exception{
        return bankCard == null ? "" : bankCard.getBankName() +"尾号"+ bankCard.
                getBankCardNo().substring(bankCard.
                getBankCardNo().length() -4);
    }

    public String subBankNoHide(BankCard bankCard) throws Exception{
        return bankCard == null ? "" : bankCard.getBankName() +"**** **** **** "+ bankCard.
                getBankCardNo().substring(bankCard.
                getBankCardNo().length() -4);
    }

    /**
     * 绑定银行卡
     * @param bankCardNo
     * @param mobile
     * @param bankName
     * @throws Exception
     */
    @Transactional
    public BankCard bindBankCard(String bankCardNo, String mobile, String bankName, Account account) throws Exception{
        BankCard bankCard = new BankCard();
        bankCard.setUserId(account.getId());
        bankCard.setBankCardNo(bankCardNo);
        bankCard.setBankName(bankName);
        bankCard.setName(account.getRealName());
//        bankCard.setPrcptcd(tranFlow);
        bankCard.setMobile(mobile);
        bankCard.setIsDefault(BankCard.DEFAULT.YES.value());
        bankCard.encrptBank();
        int id = super.save(bankCard);
        bankCard.setId(id);
        account.setPrepareStep(Account.PrepareInvestStep.BIND_CARD.getValue());
        accountService.synUpdateAccount(account);
        return bankCard;
    }



}
