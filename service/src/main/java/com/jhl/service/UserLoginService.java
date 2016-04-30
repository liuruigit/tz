package com.jhl.service;

import com.jhl.pojo.biz.AccountLogin;
import com.jhl.pojo.biz.Account;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by hallywu on 15/9/26.
 */
@Service(value = "userLoginService")
public class UserLoginService extends BaseService<AccountLogin> {

    /** 密码登录     */
    public static final int LOGIN_TYPE_NORMAL = 0;
    /** TOKEN登录   */
    public static final int LOGIN_TYPE_TOKEN = 1;
    /** 手势密码登录 */
    public static final int LOGIN_TYPE_LINE = 2;

    /**
     * 保存登录记录
     * @param account
     * @param imei
     * @param type
     * @return
     * @throws Exception
     */
    public Integer save(Account account,String imei,int type) throws Exception {
        AccountLogin userLogin = new AccountLogin();
        userLogin.setType(type);
        userLogin.setImei(imei);
        userLogin.setToken(account.getToken());
        userLogin.setType(type);
        userLogin.setTime(new Date());
        userLogin.setUserId(account.getId());
        return super.save(userLogin);
    }
}
