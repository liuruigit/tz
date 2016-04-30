package com.jhl.controller;

import com.google.common.base.Strings;
import com.jhl.common.constant.CommonConstant;
import com.jhl.constant.ConfigKey;
import com.jhl.dao.NoticesDao;
import com.jhl.dto.AccountDto;
import com.jhl.dto.BaseDto;
import com.jhl.dto.InvestDto;
import com.jhl.dto.PayDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.Feedback;
import com.jhl.pojo.biz.Project;
import com.jhl.pojo.json.JsonBack;
import com.jhl.security.JwtHolder;
import com.jhl.service.*;
import com.jhl.util.DateUtil;
import com.jhl.util.SessionUtil;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/26.
 * 辅助API
 */
@Controller()
@RequestMapping("support")
public class SupportController extends BaseController{

    private static final String feedback = "feedback";
    private static Logger logger = LoggerFactory.getLogger(SupportController.class);
    @Autowired
    SupportService supportService;
    @Autowired
    ContractGenerateService contractGenerateService;
    @Autowired
    ProjectService projectService;
    @Autowired
    SecurityQuestionService securityQuestionService;
    @Autowired
    BankCardService bankCardService;
    @Autowired
    NoticesDao noticesDao;

    /**
     * 充值代扣协议
     */
    @RequestMapping(value = "auth/withHolding")
    public ModelAndView withHolding(PayDto dto){
        logger.info("生成充值代扣协议开始");
        ModelAndView mv = new ModelAndView();
        String amount = Strings.isNullOrEmpty(dto.getAmount()) ? "0":dto.getAmount();
        BankCard bankCard = null;
        try {
            Account account = SessionUtil.getSession();
            bankCard = bankCardService.queryDefaultBank(account.getId());
            mv.addObject("date", DateUtil.formatNow());
            mv.addObject("amount", amount);
            mv.addObject("account", account);
            mv.addObject("bankCard", bankCard);
            if(CommonConstant.CLIENT_WEB.equals(dto.getClient())){
                mv.setViewName("contact/withHolding");
            }else {
                mv.setViewName("contact/withHolding_app");
            }
            logger.info("生成充值代扣协议结束");
        } catch (Exception e) {
            logger.error("生成协议失败",e);
        }

        return mv;
    }

    /**
     * version:1.0.6
     * desc：优化404问题
     * 投资协议
     */
    @RequestMapping(value = "auth/invest")
    public ModelAndView withHolding(InvestDto dto){
        logger.info("查看投资协议开始");
        ModelAndView mv = new ModelAndView();
        String amount = Strings.isNullOrEmpty(dto.getAmount()) ? "0" : dto.getAmount();
        try {
            Project project = projectService.queryById(Integer.valueOf(dto.getProjectId()));
            String content = "";
            if (project == null){
                content = "暂无匹配的项目信息";
            }else {
                content = contractGenerateService.queryInvestContent(project.getNo());
            }
            Account account = SessionUtil.getSession();
            mv.addObject("content", contractGenerateService.replaceContractInfo(content,account,Double.parseDouble(amount)));
            if(CommonConstant.CLIENT_WEB.equals(dto.getClient())){
                mv.setViewName("contact/invest");
            }else {
                mv.setViewName("contact/invest_app");
            }
            logger.info("查看投资协议结束");
        } catch (Exception e) {
            logger.error("查看协议失败",e);
        }

        return mv;
    }



    /**
     * 配置
     * @return
     */
    @RequestMapping(value = "config")
    public @ResponseBody
    JsonBack config(BaseDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            try{
                Map<String,String> result = new HashMap<String,String>();
                result.put("wx",SysConfig.geteConfigByKey(ConfigKey.CONFIG_WX));
                result.put("contract",SysConfig.geteConfigByKey(ConfigKey.CONFIG_CONTRACT));
                result.put("invest", SysConfig.geteConfigByKey(ConfigKey.INPUT_CONFIG_INVEST));
                result.put("charge",SysConfig.geteConfigByKey(ConfigKey.INPUT_CONFIG_CHARGE));
                result.put("cash",SysConfig.geteConfigByKey(ConfigKey.INPUT_CONFIG_CASH));
                return buildSuccJsonBack(jsonBack,result);
            }catch (Exception e){
                logger.error(SessionUtil.getNo() + "查询投资券问题",e);
            }
            return buildErrorJsonBack(jsonBack);
        } catch (Exception e) {
            logger.error("心跳异常",e);
            return buildErrorJsonBack(jsonBack);
        }
    }

    /**
     * 心跳接口
     * @return
     */
    @RequestMapping(value = "heartBeat")
    public @ResponseBody
    JsonBack heartBeat(BaseDto dto){
        JsonBack jsonBack = new JsonBack();
        try {
            if (Strings.isNullOrEmpty(dto.getToken())){
                return buildSuccJsonBack(jsonBack, CommonConstant.JSON_BACK_NO_AUTH);
            }
            Account account = JwtHolder._instance().verifyToken(dto.getToken());
            if (account == null){
                return buildSuccJsonBack(jsonBack, CommonConstant.JSON_BACK_NO_AUTH);
            }
            if (Strings.isNullOrEmpty(account.getLastLoginImei())){
                logger.warn("登录用户的lastLoginImei为空:{}",account.toString());
            }
            if (!account.getLastLoginImei().equalsIgnoreCase(dto.getImei())){
                return buildSuccJsonBack(jsonBack, CommonConstant.JSON_BACK_REPEAT_LOGIN);
            }
        } catch (Exception e) {
            logger.error("心跳异常",e);
            return buildErrorJsonBack(jsonBack);
        }
        return buildSuccJsonBack(jsonBack,0);
    }

    /**
     * 获取银行卡规则
     * @return
     */
    @RequestMapping(value = "getRules")
    public @ResponseBody
    JsonBack getRules(){
        JsonBack jsonBack = new JsonBack();

        try {
            return buildSuccJsonBack(jsonBack,bankCardService.getRules());
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "获取银行卡规则: ", e);
        }
        return buildErrorJsonBack(jsonBack,"获取银行卡规则错误！");
    }

    /**
     * 获取安全保护问题
     * @return
     */
    @RequestMapping("getQues")
    public @ResponseBody
    JsonBack getQues(){
        JsonBack jsonBack = new JsonBack();
        try{
            return buildSuccJsonBack(jsonBack,securityQuestionService.getSec());
        }catch (Exception e){
            logger.error(SessionUtil.getNo() + "获取安全保护问题",e);
        }
        return buildErrorJsonBack(jsonBack);
    }

    @RequestMapping("auth/feedback"  )
    public @ResponseBody JsonBack feedback(AccountDto dto, HttpServletRequest request) throws IllegalStateException, IOException {
        JsonBack jsonBack = new JsonBack();
        try {
            if (!Strings.isNullOrEmpty(dto.getContent()) && dto.getContent().length() > 100)return buildErrorJsonBack(jsonBack);
            Account account = SessionUtil.getSession();
            List<String> filePath = fileUpload(request,feedback);
            supportService.save(buildFeedback(filePath,account,dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildSuccJsonBack(jsonBack);
    }

    @RequestMapping("auth/key" )
    public @ResponseBody JsonBack key() throws IllegalStateException, IOException {
        JsonBack jsonBack = new JsonBack();
        try {
            Account account = SessionUtil.getSession();
            String payToken = JwtHolder._instance().genPayToken(account);
            account.setFingerPwd(payToken);
            accountService.synUpdateAccount(account);
            return buildSuccJsonBack(jsonBack, payToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildErrorJsonBack(jsonBack);
    }

    private Feedback buildFeedback(List<String> filePath,Account account,AccountDto dto){
        Feedback feedback = new Feedback();
        StrBuilder buffer = new StrBuilder();
        for(String path : filePath) {
            buffer.append(path).append("@");
        }
        feedback.setAttach(buffer.toString());
        feedback.setAccId(account.getId());
        feedback.setContent(dto.getContent());
        feedback.setCreateTime(DateUtil.now());
        feedback.setUpdateTime(DateUtil.now());
        feedback.setStatus(0);
        return feedback;
    }

}
