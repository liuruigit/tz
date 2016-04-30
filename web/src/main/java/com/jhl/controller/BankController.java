package com.jhl.controller;

import com.jhl.cache.VerifyCodeCache;
import com.jhl.util.SessionUtil;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.BankCardService;
import com.jhl.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by V5inter on 2016/1/27.
 * 新增银行卡
 */
@Controller
@RequestMapping("auth/bank")
public class BankController extends BaseController{

    @Autowired
    VerifyCodeCache verifyCodeCache;
    @Autowired
    BankCardService bankCardService;
    @Autowired
    BillingService billingService;
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

    /**
     * 查询绑定银行卡
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody JsonBack get(){
        JsonBack jsonBack = new JsonBack();
        try {
            Map<String,Object> bankCard = bankCardService.queryDefaultBankMap(SessionUtil.getSession().getId());
            Double todayChargeAmount = billingService.sumCharge(SessionUtil.getSession().getId());
            bankCard.put("todayChargeAmount",todayChargeAmount == null ? 0 : todayChargeAmount / 100);
            return buildSuccJsonBack(jsonBack,bankCard);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "查询绑定银行卡失败！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }
}
