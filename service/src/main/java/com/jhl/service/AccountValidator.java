package com.jhl.service;

import com.jhl.util.ValidateUtil;

/**
 * Created by Administrator on 2016/1/17.
 */
public class AccountValidator {

    public boolean accNameCheck(String name) throws Exception{
        return ValidateUtil.checkMobile(name);
    }

}
