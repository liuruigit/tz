package com.jhl.controller;

import com.jhl.constant.ConfigKey;
import com.jhl.dto.AccountDto;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.BillingService;
import com.jhl.service.ConfigService;
import com.jhl.service.MessageService;
import com.jhl.service.SysConfig;
import com.jhl.task.month.MonthBillingTask;
import com.jhl.util.HttpClient431Util;
import com.jhl.util.MD5Util;
import com.jhl.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hally on 2016/1/24.
 * 系统接口，只供内部调用，需验证配置令牌
 */
@Controller
@RequestMapping("sys")
public class SysController extends BaseController {

    private static final String token = "JQiw8sk2";

    private Logger logger = Logger.getLogger(SysController.class);

    @Autowired
    ConfigService configService;

    @Autowired
    BillingService billingService;
    @Autowired
    MonthBillingTask monthBillingTask;

    @Autowired
    MessageService messageService;

    /**
     * 地址测试接口
     * @param request
     * @return
     */
    @RequestMapping("ipTest")
    public @ResponseBody
    JsonBack ipTest(HttpServletRequest request){
        JsonBack jsonBack = new JsonBack();
        try{
            String result = getAddress(request);
            return buildSuccJsonBack(jsonBack,result);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "地址解析",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    private Map<String, String> buildParams(String res,String key) throws Exception{
        String base = res + key;
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", MD5Util.getMD5String(base));
        params.put("res",res);
        return params;
    }

    @RequestMapping("jhTest")
    public @ResponseBody
    JsonBack msg(String  paras,String key){
        JsonBack jsonBack = new JsonBack();
        try{
            String result = HttpClient431Util.doPost(buildParams(paras,key),SysConfig.geteConfigByKey(ConfigKey.SYS_JH_TRAN_URL));
            return buildSuccJsonBack(jsonBack,result);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "查询投资券问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }


    @RequestMapping("billingTest")
    public @ResponseBody
    JsonBack billingTest(){
        JsonBack jsonBack = new JsonBack();
        try{
            monthBillingTask.doBillingForAllUser();
            return buildSuccJsonBack(jsonBack);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "查询投资券问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 消息
     * @param dto
     * @return
     */
    @RequestMapping("msg")
    public @ResponseBody
    JsonBack msg(AccountDto dto){
        JsonBack jsonBack = new JsonBack();
        try{
            Map<String,String> result = new HashMap<String,String>();

//            result.put("warmNotice","测试温馨提示");//查询消息表的第一条记录 order by create_time && delete_state = 1 && limit 1
//            result.put("notice","提示");//查询系统消息的第一条记录 order by create_time && delete_state = 1 && limit 1

            String msg = messageService.queryMessage();
            result.put("warmNotice",msg);
            String sysMsg = messageService.querySysMessage();
            result.put("notice",sysMsg);

            return buildSuccJsonBack(jsonBack,result);
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "查询投资券问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 重新加载缓存
     * @param token
     * @return
     */
    @RequestMapping("reloadConfig")
    public @ResponseBody JsonBack reloadConfig(String token){
        logger.info("重新加载系统配置！");
        JsonBack jsonBack = new JsonBack();
        int before = configService.getConfigSize();
        if (this.token.equalsIgnoreCase(token)){
            configService.reload();
        }
        int after = configService.getConfigSize();
        String msg = "重新加载系统配置成功，加载前："+before+"，加载后："+after;
        logger.info(msg);
        return buildSuccJsonBack(jsonBack,null,msg);
    }

    /**
     * 查询平台数据
     * @return
     */
    @RequestMapping("sumPlatform")
    public @ResponseBody
    JsonBack sumPlatform(){
        JsonBack jsonBack = new JsonBack();
        try{
            return buildSuccJsonBack(jsonBack,billingService.sumPlatform());
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "平台数据",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

}
