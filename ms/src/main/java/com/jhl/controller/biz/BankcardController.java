package com.jhl.controller.biz;

import com.jhl.constant.ConfigKey;
import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.service.biz.BankcardService;
import com.jhl.service.biz.ConfigService;
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
 * 类名称：BankcardController
 * 创建人：Hally
 * 创建时间：2016-03-27
 */
@Controller
@RequestMapping(value="/bankcard")
public class BankcardController extends BaseController {
	
	String menuUrl = "bankcard/list.do"; //菜单地址(权限用)
	@Resource(name="bankcardService")
	private BankcardService bankcardService;

	@Resource(name = "msConfigService")
	private ConfigService configService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Bankcard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DELETE_STATE", 1);	//逻辑删除标志位：1正常0删除
		bankcardService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Bankcard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			bankcardService.delete(pd);
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
		logBefore(logger, "修改Bankcard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String name = pd.getString("NAME");
		String cartN0 = pd.getString("BANK_CARD_NO");
		String mobile = pd.getString("MOBILE");
		pd.put("NAME", DESUtil.getEncString(name));
		pd.put("BANK_CARD_NO", DESUtil.getEncString(cartN0));
		pd.put("MOBILE", DESUtil.getEncString(mobile));
		bankcardService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Bankcard");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			DESUtil.setKey(configService.findValueByKey(ConfigKey.SECRET_KEY));
			pd = this.getPageData();
			String name1 = pd.getString("name");
			pd.put("name", DESUtil.getEncString(name1));
			page.setPd(pd);

			List<PageData>	varList = bankcardService.list(page);	//列出Bankcard列表
			for(PageData pageDate : varList) {
				String name = pageDate.getString("NAME");
				String cartN0 = pageDate.getString("BANK_CARD_NO");
				String mobile = pageDate.getString("MOBILE");
				pageDate.put("NAME", DESUtil.getDesString(name));
				pageDate.put("BANK_CARD_NO", DESUtil.getDesString(cartN0));
				pageDate.put("MOBILE", DESUtil.getDesString(mobile));

			}
			mv.setViewName("bankcard/bankcard_list");
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
		logBefore(logger, "去新增Bankcard页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("bankcard/bankcard_edit");
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
		logBefore(logger, "去修改Bankcard页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			DESUtil.setKey(configService.findValueByKey(ConfigKey.SECRET_KEY));
			pd = bankcardService.findById(pd);	//根据ID读取
			String name = pd.getString("NAME");
			String cartN0 = pd.getString("BANK_CARD_NO");
			String mobile = pd.getString("MOBILE");
			pd.put("NAME", DESUtil.getDesString(name));
			pd.put("BANK_CARD_NO", DESUtil.getDesString(cartN0));
			pd.put("MOBILE", DESUtil.getDesString(mobile));
			mv.setViewName("bankcard/bankcard_edit");
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
		logBefore(logger, "批量删除Bankcard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				bankcardService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Bankcard到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("银行名称");	//1
			titles.add("名称");	//2
			titles.add("用户ID");	//3
			titles.add("银行卡号");	//4
			titles.add("省份");	//5
			titles.add("城市");	//6
			titles.add("支行");	//7
			titles.add("code");	//8
			titles.add("是否默认卡");	//9
			titles.add("协议号");	//10
			titles.add("签约协议号");	//11
			titles.add("绑定的手机号");	//12
			titles.add("创建时间");	//13
			titles.add("逻辑删除标志位：1正常0删除");	//14
			dataMap.put("titles", titles);
			List<PageData> varOList = bankcardService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("BANK_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("USER_ID"));	//3
				vpd.put("var4", varOList.get(i).getString("BANK_CARD_NO"));	//4
				vpd.put("var5", varOList.get(i).getString("PRO"));	//5
				vpd.put("var6", varOList.get(i).getString("CITY"));	//6
				vpd.put("var7", varOList.get(i).getString("BRANCH"));	//7
				vpd.put("var8", varOList.get(i).getString("BANK_CODE"));	//8
				vpd.put("var9", varOList.get(i).getString("IS_DEFAULT"));	//9
				vpd.put("var10", varOList.get(i).getString("PRCPTCD"));	//10
				vpd.put("var11", varOList.get(i).getString("NO_AGREE"));	//11
				vpd.put("var12", varOList.get(i).getString("MOBILE"));	//12
				vpd.put("var13", varOList.get(i).getString("CREATE_TIME"));	//13
				vpd.put("var14", varOList.get(i).getString("DELETE_STATE"));	//14
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
