package com.jhl.controller.biz;

import com.jhl.entity.Page;
import com.jhl.pojo.json.JsonBack;
import com.jhl.util.*;
import com.jhl.controller.base.BaseController;
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
import com.jhl.service.biz.AccountChangeService;

/** 
 * 类名称：AccountChangeController
 * 创建人：Hally
 * 创建时间：2016-03-21
 */
@Controller
@RequestMapping(value="/accountchange")
public class AccountChangeController extends BaseController {
	
	String menuUrl = "accountchange/list.do"; //菜单地址(权限用)
	@Resource(name="accountchangeService")
	private AccountChangeService accountchangeService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增AccountChange");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", "否");	//用户ID
		pd.put("AMOUNT", "否");	//交易金额
		pd.put("ACC_MONEY", "否");	//用户余额
		pd.put("ORDER_NO", "否");	//订单号
		pd.put("TRAN_NAME", "否");	//交易名称
		pd.put("CREATE_TIME", "否");	//交易时间
		pd.put("EXTRA_MSG1", "否");	// 补充信息（1）
		pd.put("EXTRA_MSG2", "否");	//补充信息（2）
		pd.put("EXTRA_MSG3", "否");	//补充信息（3）
		pd.put("EXTRA_MSG4", "否");	//补充信息（4）
		accountchangeService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除AccountChange");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			accountchangeService.delete(pd);
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
		logBefore(logger, "修改AccountChange");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		accountchangeService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表AccountChange");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = accountchangeService.list(page);	//列出AccountChange列表
			mv.setViewName("accountchange/accountchange_list");
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
		logBefore(logger, "去新增AccountChange页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("accountchange/accountchange_edit");
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
		logBefore(logger, "去修改AccountChange页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = accountchangeService.findById(pd);	//根据ID读取
			mv.setViewName("accountchange/accountchange_edit");
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
		logBefore(logger, "批量删除AccountChange");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				accountchangeService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出AccountChange到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户ID");	//1
			titles.add("交易金额");	//2
			titles.add("用户余额");	//3
			titles.add("订单号");	//4
			titles.add("交易名称");	//5
			titles.add("交易时间");	//6
			titles.add(" 补充信息（1）");	//7
			titles.add("补充信息（2）");	//8
			titles.add("补充信息（3）");	//9
			titles.add("补充信息（4）");	//10
			dataMap.put("titles", titles);
			List<PageData> varOList = accountchangeService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("USER_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("AMOUNT"));	//2
				vpd.put("var3", varOList.get(i).getString("ACC_MONEY"));	//3
				vpd.put("var4", varOList.get(i).getString("ORDER_NO"));	//4
				vpd.put("var5", varOList.get(i).getString("TRAN_NAME"));	//5
				vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));	//6
				vpd.put("var7", varOList.get(i).getString("EXTRA_MSG1"));	//7
				vpd.put("var8", varOList.get(i).getString("EXTRA_MSG2"));	//8
				vpd.put("var9", varOList.get(i).getString("EXTRA_MSG3"));	//9
				vpd.put("var10", varOList.get(i).getString("EXTRA_MSG4"));	//10
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
