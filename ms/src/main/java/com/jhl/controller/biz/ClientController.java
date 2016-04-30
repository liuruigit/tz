package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.service.biz.ClientService;
import com.jhl.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
 * 类名称：ClientController
 * 创建人：Hally
 * 创建时间：2016-01-15
 */
@Controller
@RequestMapping(value="/client")
public class ClientController extends BaseController {
	
	String menuUrl = "client/list.do"; //菜单地址(权限用)
	@Resource(name="clientService")
	private ClientService clientService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Client");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("LINE_PWD", "");	//手势密码
		pd.put("TOKEN", "");	//token
		pd.put("CREATE_TIME", new Date());	//创建时间
		pd.put("LAST_LOGIN_TIME", new Date());	//上次登录时间
		pd.put("STATUS", 0);	//状态
		pd.put("DELETE_STATE", 1);	//1正常0删除
		pd.put("LAST_LOGIN_IMEI", "");	//上次登录设备
		clientService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Client");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			clientService.delete(pd);
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
		logBefore(logger, "修改Client");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		clientService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Client");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = clientService.list(page);	//列出Client列表
			mv.setViewName("client/client_list");
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
		logBefore(logger, "去新增Client页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("client/client_edit");
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
		logBefore(logger, "去修改Client页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = clientService.findById(pd);	//根据ID读取
			mv.setViewName("client/client_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Client");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				clientService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Client到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("账号");	//1
			titles.add("密码");	//2
			titles.add("交易密码");	//3
			titles.add("手势密码");	//4
			titles.add("token");	//5
			titles.add("手机号");	//6
			titles.add("邮箱");	//7
			titles.add("创建时间");	//8
			titles.add("上次登录时间");	//9
			titles.add("状态");	//10
			titles.add("1正常0删除");	//11
			titles.add("身份证号");	//12
			titles.add("真实姓名");	//13
			titles.add("是否实名");	//14
			titles.add("余额");	//15
			titles.add("冻结金额");	//16
			titles.add("上次登录设备");	//17
			dataMap.put("titles", titles);
			List<PageData> varOList = clientService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ACC_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("PWD"));	//2
				vpd.put("var3", varOList.get(i).getString("TRADE_PWD"));	//3
				vpd.put("var4", varOList.get(i).getString("LINE_PWD"));	//4
				vpd.put("var5", varOList.get(i).getString("TOKEN"));	//5
				vpd.put("var6", varOList.get(i).getString("MOBILE"));	//6
				vpd.put("var7", varOList.get(i).getString("EMAIL"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATE_TIME"));	//8
				vpd.put("var9", varOList.get(i).getString("LAST_LOGIN_TIME"));	//9
				vpd.put("var10", varOList.get(i).getString("STATUS"));	//10
				vpd.put("var11", varOList.get(i).getString("DELETE_STATE"));	//11
				vpd.put("var12", varOList.get(i).getString("CERT_NO"));	//12
				vpd.put("var13", varOList.get(i).getString("REAL_NAME"));	//13
				vpd.put("var14", varOList.get(i).getString("IS_REAL_NAME"));	//14
				vpd.put("var15", varOList.get(i).getString("MONEY"));	//15
				vpd.put("var16", varOList.get(i).getString("FREEZE_MONEY"));	//16
				vpd.put("var17", varOList.get(i).getString("LAST_LOGIN_IMEI"));	//17
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
