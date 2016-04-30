package com.jhl.controller.biz;

import com.google.common.base.Strings;
import com.jhl.common.sms.BaseSmsConnector;
import com.jhl.common.sms.JLSmsConnector;
import com.jhl.constant.ConfigKey;
import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Message;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.AccountService;
import com.jhl.service.MessageService;
import com.jhl.service.SmsService;
import com.jhl.service.biz.ConfigService;
import com.jhl.service.biz.MsAccountService;
import com.jhl.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * 类名称：MsAccountController
 * 创建人：Hally
 * 创建时间：2016-03-01
 */
@Controller
@RequestMapping(value="/msaccount")
public class MsAccountController extends BaseController {
	
	String menuUrl = "msaccount/list.do"; //菜单地址(权限用)
	@Resource(name="msaccountService")
	private MsAccountService msaccountService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SmsService smsService;
	@Resource(name = "messageService")
	private MessageService messageService;
	@Resource(name = "msConfigService")
	private ConfigService configService;

	BaseSmsConnector smsConnector = new JLSmsConnector();

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增MsAccount");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", "否");	//id
		pd.put("LINE_PWD", "否");	//手势密码
		pd.put("TOKEN", "否");	//token
		pd.put("UPDATE_TIME", "否");	//更新时间
		pd.put("CREATE_TIME", "否");	//创建时间
		pd.put("DELETE_STATUS", "否");	//逻辑删除标志位：1正常0删除
		pd.put("RECOMMEND_ID", "否");	//推荐人
		pd.put("PREPARE_STEP", "否");	//投资准备
		pd.put("LV", "否");	//等级
		pd.put("POINT", "否");	//积分
		pd.put("MONEY", "否");	//余额
		pd.put("FROZEN_MONEY", "否");	//冻结金额
		pd.put("INVEST_MONEY", "否");	//投资金额
		pd.put("DIGEST", "否");	//摘要
		pd.put("VERSION", "否");	//版本号
		pd.put("ACC_INCOME", "否");	//累计收益
		pd.put("VIP", "否");	//vip等级
		msaccountService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除MsAccount");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			msaccountService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改MsAccount");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		DESUtil.setKey(configService.findValueByKey(ConfigKey.SECRET_KEY));
		String name = pd.getString("ACC_NAME");
		String mobile = pd.getString("MOBILE");
		String card = pd.getString("CERT_NO");
		String realName = pd.getString("REAL_NAME");
		pd.put("ACC_NAME", DESUtil.getEncString(name));
		pd.put("MOBILE", DESUtil.getEncString(mobile));
		pd.put("CERT_NO", DESUtil.getEncString(card));
		pd.put("REAL_NAME", DESUtil.getEncString(realName));
		msaccountService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表MsAccount");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			DESUtil.setKey(configService.findValueByKey(ConfigKey.SECRET_KEY));
			if(pd != null && pd.getString("NAME") != null) {
				String name = DESUtil.getEncString(pd.getString("NAME"));
				pd.put("NAME", name);
			}
			page.setPd(pd);
			List<PageData>	varList = msaccountService.list(page);	//列出MsAccount列表\
			for(PageData pageData : varList) {
				String name = pageData.getString("ACC_NAME");
				String mobile = pageData.getString("MOBILE");
				String card = pageData.getString("CERT_NO");
				String realName = pageData.getString("REAL_NAME");
				pageData.put("ACC_NAME", DESUtil.getDesString(name));
				pageData.put("MOBILE", DESUtil.getDesString(mobile));
				pageData.put("REAL_NAME", DESUtil.getDesString(realName));
				pageData.put("CERT_NO", DESUtil.getDesString(card));
			}
			mv.setViewName("msaccount/msaccount_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增MsAccount页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("msaccount/msaccount_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改MsAccount页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {

			DESUtil.setKey(configService.findValueByKey(ConfigKey.SECRET_KEY));
			PageData pageData = msaccountService.findById(pd);	//根据ID读取
			String name = pageData.getString("ACC_NAME");
			String mobile = pageData.getString("MOBILE");
			String card = pageData.getString("CERT_NO");
			String realName = pageData.getString("REAL_NAME");
			pageData.put("ACC_NAME", DESUtil.getDesString(name));
			pageData.put("MOBILE", DESUtil.getDesString(mobile));
			pageData.put("REAL_NAME", DESUtil.getDesString(realName));
			pageData.put("CERT_NO", DESUtil.getDesString(card));
			mv.setViewName("msaccount/msaccount_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pageData);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	/**
	 * 去短信发送页面
	 */
	@RequestMapping(value="/goSendMsg")
	public ModelAndView goSendMsg() {
		logBefore(logger, "去短信发送页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("msaccount/msaccount_msg");
			mv.addObject("msg", "sendMsg");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 发送
	 * @return
     */
	@RequestMapping(value="/sendMsg.do")
	public @ResponseBody
	JsonBack sendMsg() {
		JsonBack jsonBack = new JsonBack();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ids = pd.getString("ID");
		try{
			Integer id = Integer.parseInt(ids);
			Account account = accountService.queryById(id);
			if (account == null){
				jsonBack.setMessage("信息发送失败，用户不存在!");
				return jsonBack;
			}
			account.parseAccout();
			String content = pd.getString("CONTENT");
			String msgType = pd.getString("MSGTYPE");
			if (!Strings.isNullOrEmpty(msgType) ) {
				if (msgType.contains("0")){
					smsConnector.sendSysMsg(account.getMobile(),content);
				}
				if (msgType.contains("1")){
					Message message = new Message();
					message.setDeleteState(1);
					message.setCreateTime(new Date());
					message.setAccId(account.getId());
					message.setContent(content);
					message.setTitle("系统消息");
					message.setSystemMsg(0);
					messageService.save(message);
				}
			}
		}catch (Exception e){
			logger.error(e);
		}
		jsonBack.setMessage("信息发送成功!");
		return jsonBack;
	}


	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除MsAccount");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				msaccountService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出MsAccount到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("id");	//1
			titles.add("用户名");	//2
			titles.add("密码");	//3
			titles.add("交易密码");	//4
			titles.add("手势密码");	//5
			titles.add("token");	//6
			titles.add("更新时间");	//7
			titles.add("创建时间");	//8
			titles.add("账号状态");	//9
			titles.add("逻辑删除标志位：1正常0删除");	//10
			titles.add("推荐人");	//11
			titles.add("电话");	//12
			titles.add("邮箱");	//13
			titles.add("身份证号");	//14
			titles.add("真实姓名");	//15
			titles.add("投资准备");	//16
			titles.add("等级");	//17
			titles.add("积分");	//18
			titles.add("余额");	//19
			titles.add("冻结金额");	//20
			titles.add("投资金额");	//21
			titles.add("摘要");	//22
			titles.add("版本号");	//23
			titles.add("累计收益");	//24
			titles.add("vip等级");	//25
			dataMap.put("titles", titles);
			List<PageData> varOList = msaccountService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ID"));	//1
				vpd.put("var2", varOList.get(i).getString("ACC_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("PWD"));	//3
				vpd.put("var4", varOList.get(i).getString("TRADE_PWD"));	//4
				vpd.put("var5", varOList.get(i).getString("LINE_PWD"));	//5
				vpd.put("var6", varOList.get(i).getString("TOKEN"));	//6
				vpd.put("var7", varOList.get(i).getString("UPDATE_TIME"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATE_TIME"));	//8
				vpd.put("var9", varOList.get(i).getString("STATUS"));	//9
				vpd.put("var10", varOList.get(i).getString("DELETE_STATUS"));	//10
				vpd.put("var11", varOList.get(i).getString("RECOMMEND_ID"));	//11
				vpd.put("var12", varOList.get(i).getString("MOBILE"));	//12
				vpd.put("var13", varOList.get(i).getString("EMAIL"));	//13
				vpd.put("var14", varOList.get(i).getString("CERT_NO"));	//14
				vpd.put("var15", varOList.get(i).getString("REAL_NAME"));	//15
				vpd.put("var16", varOList.get(i).getString("PREPARE_STEP"));	//16
				vpd.put("var17", varOList.get(i).getString("LV"));	//17
				vpd.put("var18", varOList.get(i).getString("POINT"));	//18
				vpd.put("var19", varOList.get(i).getString("MONEY"));	//19
				vpd.put("var20", varOList.get(i).getString("FROZEN_MONEY"));	//20
				vpd.put("var21", varOList.get(i).getString("INVEST_MONEY"));	//21
				vpd.put("var22", varOList.get(i).getString("DIGEST"));	//22
				vpd.put("var23", varOList.get(i).getString("VERSION"));	//23
				vpd.put("var24", varOList.get(i).getString("ACC_INCOME"));	//24
				vpd.put("var25", varOList.get(i).getString("VIP"));	//25
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
