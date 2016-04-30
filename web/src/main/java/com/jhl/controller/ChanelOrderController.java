package com.jhl.controller;

import com.jhl.util.SessionUtil;
import com.jhl.dto.ChannelOrderDto;
import com.jhl.pojo.json.JsonBack;
import com.jhl.pojos.PageInfo;
import com.jhl.service.ChannelOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hallywu on 16/1/31.
 * 提现查询API
 */
@Controller
@RequestMapping("auth/channel")
public class ChanelOrderController extends BaseController{

    @Autowired
    ChannelOrderService cashOrderService;

    private static final Logger logger = LoggerFactory.getLogger(ChanelOrderController.class);

    /**
     * 查询提现列表
     * @param dto
     * @return
     */
    @RequestMapping("list")
    public @ResponseBody
    JsonBack list(ChannelOrderDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            PageInfo info = getPageInfo(dto.getPage());
            return buildSuccJsonBack(jsonBack,cashOrderService.getCashOrders(
                    SessionUtil.getSession(),info));
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "绑定银行卡失败！",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

//    /**
//     * 查询提现详情
//     * @param orderNo
//     * @return
//     */
//    @RequestMapping("info")
//    public @ResponseBody
//    JsonBack info(String orderNo){
//        JsonBack jsonBack = new JsonBack();
//        try {
//            return buildSuccJsonBack(jsonBack,cashOrderService.queryByOrderNo(orderNo));
//        } catch (Exception e) {
//            logger.error(SessionUtil.getNo() + "查询提现详情失败！",e);
//        }
//        return buildErrorJsonBack(jsonBack);
//    }
}
