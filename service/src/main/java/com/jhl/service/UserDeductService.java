package com.jhl.service;

import com.jhl.pojo.biz.Account;
import org.springframework.stereotype.Service;

/**
 * Created by vic wu on 2015/8/19.
 */
@Service
public class UserDeductService extends BaseService<Account> {

//    @Autowired
//    private UserDeductDao deductDao;
//
//    public UserDeduct queryUserPactByTradeNo(String tradeNo) throws Exception {
//        return deductDao.queryUserPactByOutBizNo(tradeNo);
//    }
//
//    public UserDeduct addUserDeduct(Account user, String money, String bankCode,
//                                    String bankAccountNo, String bankProvName,
//                                    String bankCityName) throws Exception{
//        UserDeduct userDeduct = new UserDeduct();
//        userDeduct.setUserId(user.getId());
//        userDeduct.setBankAccountNo(bankAccountNo);
//        userDeduct.setBankCode(bankCode);
//        userDeduct.setBankProvName(bankProvName);
//        userDeduct.setBankCityName(bankCityName);
//        userDeduct.setMoney(money);
//        userDeduct.setPayMode("P");
//        userDeduct.setStatus(0);
//        userDeduct.setPartUserId(user.getParterUserId1());
//        int id = this.add(userDeduct);
//        userDeduct.setId(id);
//        return userDeduct;
//    }
}
