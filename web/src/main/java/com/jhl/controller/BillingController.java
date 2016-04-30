package com.jhl.controller;

import com.jhl.dto.BillingDto;
import com.jhl.dto.ChannelOrderDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.pojos.PageInfo;
import com.jhl.service.BillingService;
import com.jhl.util.DateUtil;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/24.
 * 对账controller
 */
@Controller()
@RequestMapping("auth/billing")
public class BillingController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    BillingService billingService;

    /**
     * 葫芦币明细
     * @return
     */
    @RequestMapping("hlbDetail")
    public @ResponseBody
    JsonBack hlbDetail(BillingDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            PageInfo pageInfo = getPageInfo(dto.getPage());
            return buildSuccJsonBack(jsonBack,billingService.queryHlb(pageInfo,account));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "葫芦币明细",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 葫芦币
     * @return
     */
    @RequestMapping("hlb")
    public @ResponseBody
    JsonBack hlb(BillingDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            Map<String,Object> hlb = new HashMap<String,Object>();
            hlb.put("hlb",account.getPoint());
            hlb.put("frozenHlb",account.getFrozenPoint());
            hlb.put("count",40);
            hlb.put("date", DateUtil.formatNow());
            return buildSuccJsonBack(jsonBack,hlb);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "葫芦币明细",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 资产明细
     * @return
     */
    @RequestMapping("propertyDetail")
    public @ResponseBody
    JsonBack propertyDetail(){
         JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            return buildSuccJsonBack(jsonBack,billingService.propertyDetail(account));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "资产明细查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 交易记录 - APP
     * @return
     */
    @RequestMapping("changeRecord")
    public @ResponseBody
    JsonBack changeRecord(BillingDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            PageInfo pageInfo = getPageInfo(dto.getPage());
            pageInfo.setPageSize(20);
            return buildSuccJsonBack(jsonBack,billingService.queryAccChange(pageInfo,account,dto));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "资产明细查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 交易记录 - web
     * @return
     */
    @RequestMapping("web/{type}")
    public @ResponseBody
    JsonBack changeRecord_web(ChannelOrderDto dto, @PathVariable("type") String  type){
        JsonBack jsonBack = new JsonBack();
        try{
            Account account = SessionUtil.getSession();
            PageInfo pageInfo = getPageInfo(dto.getPage());
            if ("changeRecordMonth".equals(type)){//交易记录
                Map<String,Object> result = new HashMap<String,Object>();
                result.put("page",billingService.queryByMonth(pageInfo,account,dto));
                result.put("charge",billingService.sumChargeAmountByMonth(account,dto));
                result.put("cash",billingService.sumCashAmountByMonth(account,dto));
                return buildSuccJsonBack(jsonBack,result);
            }
            if ("changeRecordRange".equals(type)){
                Map<String,Object> result = new HashMap<String,Object>();
                result.put("page",billingService.queryByRange(pageInfo,account,dto));
                result.put("charge",billingService.sumChargeAmountByRange(account,dto));
                result.put("cash",billingService.sumCashAmountByRange(account,dto));
                return buildSuccJsonBack(jsonBack,result);
            }
            if ("channelMonth".equals(type)){
                return buildSuccJsonBack(jsonBack,billingService.queryByMonth_channel(pageInfo,account,dto));
            }
            if ("channelRange".equals(type)){
                return buildSuccJsonBack(jsonBack,billingService.queryByRang_channel(pageInfo,account,dto));
            }
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "资产明细查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 对账单
     * @return
     */
    @RequestMapping("info")
    public @ResponseBody
    JsonBack bill(BillingDto dto){
        JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            return buildSuccJsonBack(jsonBack,billingService.queryBill(account,dto.getMouth()));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "对账单查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }
}
