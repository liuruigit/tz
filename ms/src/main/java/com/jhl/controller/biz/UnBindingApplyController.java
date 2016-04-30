package com.jhl.controller.biz;

import com.google.common.base.Strings;
import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.biz.UnBindingApplyService;
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
 * 类名称：UnBindingApplyController
 * 创建人：Hally
 * 创建时间：2016-02-26
 */
@Controller
@RequestMapping(value="/unbindingapply")
public class UnBindingApplyController extends BaseController {
	
	String menuUrl = "unbindingapply/list.do"; //菜单地址(权限用)
	@Resource(name="unbindingapplyService")
	private UnBindingApplyService unbindingapplyService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增UnBindingApply");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ACC_ID", "否");	//用户ID
		pd.put("ATTACH", "否");	//附件
		pd.put("STATUS", "否");	//状态
		pd.put("CREATE_TIME", "否");	//创建日期
		pd.put("UPDATE_TIME", "否");	//更新日期
		pd.put("DELETE_STATE", "否");	//逻辑删除标志位
		unbindingapplyService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除UnBindingApply");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			unbindingapplyService.delete(pd);
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
	@ResponseBody
	public JsonBack edit() throws Exception{
		logBefore(logger, "修改UnBindingApply");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		JsonBack jsonBack = new JsonBack();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATE_TIME", new Date());
		unbindingapplyService.edit(pd);
		jsonBack.setMessage("状态修改成功！");
		return jsonBack;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表UnBindingApply");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = unbindingapplyService.list(page);	//列出UnBindingApply列表
			mv.setViewName("unbindingapply/unbindingapply_list");
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
		logBefore(logger, "去新增UnBindingApply页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("unbindingapply/unbindingapply_edit");
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
		logBefore(logger, "去修改UnBindingApply页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = unbindingapplyService.findById(pd);	//根据ID读取
			mv.setViewName("unbindingapply/unbindingapply_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

    /**
     * 进入审批页面
     * @return
     */
    @RequestMapping(value="/goAgree")
    public ModelAndView goAgree(){
        logBefore(logger, "进入解绑审批页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = unbindingapplyService.findWithBankInfo(pd);	//根据ID读取
			if (pd.size()>0){
				String name = pd.getString("name");
				String bank_card_no = pd.getString("bank_card_no");
				String mobile = pd.getString("mobile");
				pd.put("name", Strings.isNullOrEmpty(name)?"":DESUtil.getDesString(name));
				pd.put("bank_card_no", Strings.isNullOrEmpty(name)?"":DESUtil.getDesString(bank_card_no));
				pd.put("mobile", Strings.isNullOrEmpty(name)?"":DESUtil.getDesString(mobile));
			}
            mv.setViewName("unbindingapply/unbindingapply_agree");
            mv.addObject("msg", "updateAgree");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 进入审批页面
     * @return
     */
    @RequestMapping(value="/updateAgree")
    public ModelAndView agree(){
        logBefore(logger, "进入解绑审批页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            boolean rs = unbindingapplyService.updateAgree(pd);

            mv.addObject("msg",rs);
            mv.setViewName("save_result");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

	@RequestMapping(value="/cancel")
	public ModelAndView cancel(){
		logBefore(logger, "进入解绑审批页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			boolean rs = unbindingapplyService.disagree(pd);

			mv.addObject("msg",rs);
			mv.setViewName("save_result");
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
		logBefore(logger, "批量删除UnBindingApply");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				unbindingapplyService.deleteAll(ArrayDATA_IDS);
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

    /**
     * 批量通过
     */
    @RequestMapping(value="/passAll")
    @ResponseBody
    public Object passAll() {
        logBefore(logger, "批量通过UnBindingApply");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if(null != DATA_IDS && !"".equals(DATA_IDS)){
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                unbindingapplyService.passAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出UnBindingApply到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户ID");	//1
			titles.add("附件");	//2
			titles.add("状态");	//3
			titles.add("创建日期");	//4
			titles.add("更新日期");	//5
			titles.add("逻辑删除标志位");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = unbindingapplyService.listAll(pd);
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
