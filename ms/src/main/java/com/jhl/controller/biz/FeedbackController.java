package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.service.biz.FeedbackService;
import com.jhl.util.Const;
import com.jhl.util.Jurisdiction;
import com.jhl.util.ObjectExcelView;
import com.jhl.util.PageData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * 类名称：FeedbackController
 * 创建人：Hally
 * 创建时间：2016-02-27
 */
@Controller
@RequestMapping(value="/feedback")
public class FeedbackController extends BaseController {
	
	String menuUrl = "feedback/list.do"; //菜单地址(权限用)
	@Resource(name="feedbackService")
	private FeedbackService feedbackService;
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Feedback");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权f限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATE_TIME",new Date());
		feedbackService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Feedback");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = feedbackService.list(page);	//列出Feedback列表
			mv.setViewName("feedback/feedback_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去修改Feedback页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//查询反馈记录的状态
			PageData pd1 = feedbackService.findById(pd);
			mv.setViewName("feedback/feedback_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd1);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Feedback到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户");	//1
			titles.add("附件");	//2
			titles.add("状态");	//3
			titles.add("创建日期");	//4
			titles.add("更新日期");	//5
			titles.add("逻辑删除标志位");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = feedbackService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("ACC_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("ATTACH"));	//2
				vpd.put("var3", varOList.get(i).getString("STATUS"));	//3
				vpd.put("var4", varOList.get(i).getString("CREATE_TIME"));	//4
				vpd.put("var5", varOList.get(i).getString("UPDATE_TIME"));	//5
				vpd.put("var6", varOList.get(i).getString("DELETE_STATE"));	//6
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
