package com.jhl.controller;


import com.jhl.util.SessionUtil;
import com.jhl.dto.InvestDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.pojos.PageInfo;
import com.jhl.service.InvestOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/2/26.
 */
@Controller
@RequestMapping("auth")
public class OrderController extends BaseController {

    @Autowired
    InvestOrderService investOrderService;
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("order/invest")
    public @ResponseBody
    JsonBack invest(InvestDto dto){
        JsonBack jsonBack = new JsonBack();
        Account account = SessionUtil.getSession();
        try{
            PageInfo pageInfo = getPageInfo(dto.getPage());
            pageInfo.setPageSize(20);
            return buildSuccJsonBack(jsonBack,investOrderService.queryInvestRecord(pageInfo,account,dto));
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "我的财富查询失败",e);
        }
        return buildErrorJsonBack(jsonBack);
    }
}
