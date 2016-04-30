package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.biz.AccCouponService;
import com.jhl.service.biz.CouponService;
import com.jhl.service.biz.MsAccountService;
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
 * 类名称：AccCouponController
 * 创建人：Hally
 * 创建时间：2016-03-21
 */
@Controller
@RequestMapping(value="/acccoupon")
public class AccCouponController extends BaseController {
	
	String menuUrl = "acccoupon/list.do"; //菜单地址(权限用)
	@Resource(name="acccouponService")
	private AccCouponService acccouponService;
	@Resource(name="msaccountService")
	private MsAccountService accountService;
	@Resource(name = "msCouponService")
	private CouponService couponService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增AccCoupon");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DELETE_STATE", 1);	//逻辑删除标志位
		pd.put("CREATE_TIME", new Date());
		acccouponService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 是否存在该用户
	 */
	@RequestMapping(value = "/findUserByNo")
	public @ResponseBody JsonBack findUserByNo() {
		JsonBack jsonBack = new JsonBack();
		logBefore(logger, "是否存在该用户");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData pageData = accountService.findById(pd);
			if(pageData != null) {
				jsonBack.setCode(0);
			} else {
				jsonBack.setCode(1);
			}
		} catch (Exception e) {
			jsonBack.setCode(1);
			logger.error("操作异常...", e);
			e.printStackTrace();

		}
		return jsonBack;
	}

	/**
	 * 是否存在优惠券
	 */
	@RequestMapping(value = "/findCouponByNo")
	public @ResponseBody JsonBack findCouponByNo() {
		JsonBack jsonBack = new JsonBack();
		logBefore(logger, "是否存在该用户");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData pageData = couponService.findById(pd);
			if(pageData != null) {
				PageData pageData1 = acccouponService.findByCoupon(pd);
				if(pageData1 != null) {
					jsonBack.setCode(2);//已有人得到了该优惠券
				} else {
					jsonBack.setCode(0);
				}
			} else {
				jsonBack.setCode(1);//不存在该优惠券
			}
		} catch (Exception e) {
			jsonBack.setCode(1);
			logger.error("操作异常...", e);
			e.printStackTrace();

		}
		return  jsonBack;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除AccCoupon");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			acccouponService.delete(pd);
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
		logBefore(logger, "修改AccCoupon");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.get("IS_USED") != null && pd.get("IS_USED").equals("1")) {
			pd.put("USED_TIME", new Date());
		}
		acccouponService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表AccCoupon");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = acccouponService.list(page);	//列出AccCoupon列表
			if(pd.getString("status") != null && pd.getString("status").trim().equals("")) {
				pd.put("status", -1);
			}
			mv.setViewName("acccoupon/acccoupon_list");
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
		logBefore(logger, "去新增AccCoupon页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("acccoupon/acccoupon_edit");
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
		logBefore(logger, "去修改AccCoupon页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = acccouponService.findById(pd);	//根据ID读取
			mv.setViewName("acccoupon/acccoupon_edit");
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
		logBefore(logger, "批量删除AccCoupon");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				acccouponService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出AccCoupon到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户ID");	//1
			titles.add("金额");	//2
			titles.add("是否使用");	//3
			titles.add("投资券ID");	//4
			titles.add("逻辑删除标志位");	//5
			titles.add("创建时间");	//6
			titles.add("使用时间");	//7
			dataMap.put("titles", titles);
			List<PageData> varOList = acccouponService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ACC_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("AMOUNT"));	//2
				vpd.put("var3", varOList.get(i).getString("IS_USED"));	//3
				vpd.put("var4", varOList.get(i).getString("COUPON_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("DELETE_STATE"));	//5
				vpd.put("var6", varOList.get(i).getString("CREATE_TIME"));	//6
				vpd.put("var7", varOList.get(i).getString("USED_TIME"));	//7
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
