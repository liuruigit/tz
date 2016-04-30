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
import com.jhl.service.biz.TranDetailsService;

/** 
 * 类名称：TranDetailsController
 * 创建人：Hally
 * 创建时间：2016-03-01
 */
@Controller
@RequestMapping(value="/trandetails")
public class TranDetailsController extends BaseController {
	
	String menuUrl = "trandetails/list.do"; //菜单地址(权限用)
	@Resource(name="trandetailsService")
	private TranDetailsService trandetailsService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增TranDetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TRAN_TYPE", "否");	//交易类型
		pd.put("TRAN_DATE", "否");	//商户交易时间
		pd.put("TRAN_DATED", "否");	//交易完成时间
		pd.put("ORDER_ID", "否");	//商户订单号
		pd.put("NUM", "否");	//批次号
		pd.put("REP_ORDER_ID", "否");	//代收付平台订单号
		pd.put("TRAN_AMOUT", "否");	//交易金额
		pd.put("CURRENCY", "否");	//币种
		pd.put("BANK_NO", "否");	//客户交易银行账号
		pd.put("BANK_NAME", "否");	//客户交易银行账户名称
		pd.put("USER_FIC_NO", "否");	//商户交易虚拟账号
		pd.put("RESULT_NO", "否");	//交易结果码
		pd.put("RESULT_DESC", "否");	//交易结果描述
		pd.put("POUND", "否");	//手续费
		trandetailsService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除TranDetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			trandetailsService.delete(pd);
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
		logBefore(logger, "修改TranDetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		trandetailsService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表TranDetails");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = trandetailsService.list(page);	//列出TranDetails列表
			mv.setViewName("trandetails/trandetails_list");
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
		logBefore(logger, "去新增TranDetails页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("trandetails/trandetails_edit");
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
		logBefore(logger, "去修改TranDetails页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = trandetailsService.findById(pd);	//根据ID读取
			mv.setViewName("trandetails/trandetails_edit");
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
		logBefore(logger, "批量删除TranDetails");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				trandetailsService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出TranDetails到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("交易类型");	//1
			titles.add("商户交易时间");	//2
			titles.add("交易完成时间");	//3
			titles.add("商户订单号");	//4
			titles.add("批次号");	//5
			titles.add("代收付平台订单号");	//6
			titles.add("交易金额");	//7
			titles.add("币种");	//8
			titles.add("客户交易银行账号");	//9
			titles.add("客户交易银行账户名称");	//10
			titles.add("商户交易虚拟账号");	//11
			titles.add("交易结果码");	//12
			titles.add("交易结果描述");	//13
			titles.add("手续费");	//14
			dataMap.put("titles", titles);
			List<PageData> varOList = trandetailsService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TRAN_TYPE"));	//1
				vpd.put("var2", varOList.get(i).getString("TRAN_DATE"));	//2
				vpd.put("var3", varOList.get(i).getString("TRAN_DATED"));	//3
				vpd.put("var4", varOList.get(i).getString("ORDER_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("NUM"));	//5
				vpd.put("var6", varOList.get(i).getString("REP_ORDER_ID"));	//6
				vpd.put("var7", varOList.get(i).getString("TRAN_AMOUT"));	//7
				vpd.put("var8", varOList.get(i).getString("CURRENCY"));	//8
				vpd.put("var9", varOList.get(i).getString("BANK_NO"));	//9
				vpd.put("var10", varOList.get(i).getString("BANK_NAME"));	//10
				vpd.put("var11", varOList.get(i).getString("USER_FIC_NO"));	//11
				vpd.put("var12", varOList.get(i).getString("RESULT_NO"));	//12
				vpd.put("var13", varOList.get(i).getString("RESULT_DESC"));	//13
				vpd.put("var14", varOList.get(i).getString("POUND"));	//14
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
