package com.jhl.controller;

import com.jhl.util.SessionUtil;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojo.biz.TransOrderRule;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.TransOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by martin on 16/2/4.
 */
@Controller
@RequestMapping("auth/trans_order")
public class TransOrderController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TransOrderController.class);

    @Autowired
    TransOrderService transOrderService;

    /**
     * 查询可以转让的订单
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public List<InvestOrder> queryValidTransOrder(){
        mockTestAcount();
        Account account = SessionUtil.getSession();
        List<InvestOrder> investOrders = transOrderService.queryValidTransOrder(account.getId() );
        return investOrders;
    }

    @ResponseBody
    @RequestMapping("rule")
    public TransOrderRule queryTransOrderRule(){
        return transOrderService.queryTransOrderRule();
    }

    @ResponseBody
    @RequestMapping("create")
    public JsonBack createTransOrder(Integer investOrderId , String discount ,double transAmount){
        mockTestAcount();
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            transOrderService.createTransOrder(investOrderId , discount , transAmount);

            buildSuccJsonBack(jsonBack);
        }catch(Exception e ){
            buildErrorJsonBack(jsonBack , "转让投资失败！");
        }
        return jsonBack;
    }

    @ResponseBody
    @RequestMapping("invest")
    public JsonBack investTransOrder(Integer transOrderId){
        mockTestAcount();
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            transOrderService.investTransOrder(account.getId() , account.getAccName() , transOrderId);
            buildSuccJsonBack(jsonBack);
        }catch(Exception e ){
            buildErrorJsonBack(jsonBack , "投资失败！");
        }
        return jsonBack;
    }

    public void mockTestAcount() {
        //@TODO , delete this test code
        Account account = new Account();
        account.setId(1);
        account.setAccName("admin");
        SessionUtil.setSession(account);
    }
}
