package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.service.biz.ChannelOrderService;
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
 * 类名称：ChannelOrderController
 * 创建人：Hally
 * 创建时间：2016-02-26
 */
@Controller
@RequestMapping(value="/channelorder")
public class ChannelOrderController extends BaseController {
	
	String menuUrl = "channelorder/list.do"; //菜单地址(权限用)
	@Resource(name="msChannelorderService")
	private ChannelOrderService channelorderService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		channelorderService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			channelorderService.delete(pd);
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
		logBefore(logger, "修改ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		channelorderService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 查询申请提审中的全部订单
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page , String user_name){
		logBefore(logger, "列表ChannelOrder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		try{
			pd = this.getPageData();
            pd.put("type", ChannelOrder.Type.CASH.getValue());
            pd.put("real_name" , DESUtil.getEncString(user_name));
			page.setPd(pd);
			List<PageData>	varList = channelorderService.list(page);	//列出ChannelOrder列表
			mv.setViewName("channelorder/channelorder_list");
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
		logBefore(logger, "去新增ChannelOrder页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("channelorder/channelorder_edit");
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
		logBefore(logger, "去修改ChannelOrder页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = channelorderService.findById(pd);	//根据ID读取
			mv.setViewName("channelorder/channelorder_edit");
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
		logBefore(logger, "批量删除ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				channelorderService.deleteAll(ArrayDATA_IDS);
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
     * 批准准备
     */
    @RequestMapping(value="/passAll")
    @ResponseBody
    public Object passAll() {
        logBefore(logger, "批量批准ChannelOrder");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if(null != DATA_IDS && !"".equals(DATA_IDS)){
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
				//根据ID查询审批的对象是否都是新建的
				if(channelorderService.findArrayIDisNew(ArrayDATA_IDS, "1")) {
					HashMap<String, List> rs = channelorderService.passAll(ArrayDATA_IDS, ChannelOrder.STATUS_SUCCESS);
					//做审批通过操作
					pd.put("msg", "ok");
					pd.put("rs" , rs );
				} else {
					pd.put("msg", "no");
				}
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
	 * 准备申请，汇总
	 */
	@RequestMapping(value="/readyAll")
	@ResponseBody
	public Object readyAll() {
		logBefore(logger, "批量准备ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				//根据ID查询审批的对象是否都是新建的
				if(channelorderService.findArrayIDisNew(ArrayDATA_IDS, "0")) {
					channelorderService.updateStatus(ArrayDATA_IDS);
					//做审批通过操作
					pd.put("msg", "ok");
				} else {
					pd.put("msg", "no");
				}
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
	 * 汇总
	 */
	@RequestMapping(value="/sumAll")
	@ResponseBody
	public Object sumAll() {
		logBefore(logger, "批量准备ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				//根据ID查询审批的对象是否都是新建的
				if(channelorderService.findArrayIDisNew(ArrayDATA_IDS, "0")) {
					//查询总和
					PageData pageData = channelorderService.getAmountAll(ArrayDATA_IDS);
					if(pageData != null && pageData.get("amountAll") != null) {
						pd.put("msg", 1);
						pd.put("rs" , pageData.get("amountAll"));
					} else {
						pd.put("msg", 3);
					}
				} else {
					pd.put("msg", 2);
				}
			}else{
				pd.put("msg", 4);
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
		logBefore(logger, "导出ChannelOrder到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("金额");	//1
			titles.add("银行卡ID");	//2
			titles.add("请求订单号");	//3
			titles.add("同步应答码");	//4
			titles.add("异步应答码");	//5
			titles.add("通知时间");	//6
			titles.add("外部订单号");	//7
			titles.add("用户ID");	//8
			titles.add("0发起充值1平台接收处理中2到账");	//9
			titles.add("创建时间");	//10
			titles.add("1有效");	//11
			titles.add("订单类型0充值1提现");	//12
			dataMap.put("titles", titles);
			List<PageData> varOList = channelorderService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("AMOUNT"));	//1
				vpd.put("var2", varOList.get(i).getString("BANK_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("ORDER_NO"));	//3
				vpd.put("var4", varOList.get(i).getString("SYN_RESULT"));	//4
				vpd.put("var5", varOList.get(i).getString("ASY_RESULT"));	//5
				vpd.put("var6", varOList.get(i).getString("NOTIFY_TIME"));	//6
				vpd.put("var7", varOList.get(i).getString("TRAN_FLOW"));	//7
				vpd.put("var8", varOList.get(i).getString("USER_ID"));	//8
				vpd.put("var9", varOList.get(i).getString("STATUS"));	//9
				vpd.put("var10", varOList.get(i).getString("CREATE_TIME"));	//10
				vpd.put("var11", varOList.get(i).getString("DELETE_STATE"));	//11
				vpd.put("var12", varOList.get(i).getString("TYPE"));	//12
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
