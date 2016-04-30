package com.jhl.controller.biz;

import com.jhl.constant.ConfigKey;
import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.HtmlGeneratorService;
import com.jhl.service.SysConfig;
import com.jhl.service.biz.ProjectAttachService;
import com.jhl.service.biz.ProjectInfoService;
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
 * 类名称：ProjectInfoController
 * 创建人：Hally
 * 创建时间：2016-01-21
 */
@Controller
@RequestMapping(value="/projectinfo")
public class ProjectInfoController extends BaseController {
	
	String menuUrl = "projectinfo/list.do"; //菜单地址(权限用)
	@Resource(name="projectAttachService")
	private ProjectAttachService projectAttachService;
	@Resource(name="projectinfoService")
	private ProjectInfoService projectinfoService;
	@Resource(name="htmlGeneratorService")
	private HtmlGeneratorService htmlGeneratorService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增ProjectInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("DELETE_STATE", 1);
		pd.put("CREATE_TIME", new Date());
		pd.put("DELETE_STATE", 1);
		projectinfoService.save(pd);
		generateAttach(pd.getString("PRO_ID"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	//生成并保存HTML的url
	private void generateAttach(String projectNo) throws Exception {
		List<PageData> pdList = projectinfoService.findAttach(projectNo);
		if(pdList.size() > 0) {
			for(PageData data : pdList) {
				String htmlPath = htmlGeneratorService.generateContract(data.getString("FILE_PATH"), data.getString("NAME"), data.get("PRO_ID").toString(), getBasePath());
				//保存URL
				data.put("URL", SysConfig.geteConfigByKey(ConfigKey.STATIC_APP_PAGE) + htmlPath);
				projectAttachService.saveURL(data);
			}
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value="/saveAttach")
	public void saveAttach(PrintWriter out) throws Exception {
		logBefore(logger, "新增附件");
		PageData pd = new PageData();
		pd = this.getPageData();
		System.out.println(pd.getString("NAME"));
		pd.put("CREATE_TIME", new Date());
		pd.put("UPDATE_TIME", new Date());
		pd.put("DELETE_STATE", 1);
		projectAttachService.save(pd);
		out.write("success");
		out.close();
	}

	/**
	 * 校验名字是否存在
	 */
	@RequestMapping(value="/checkSaveFile")
	public @ResponseBody
	JsonBack checkSaveFile() throws Exception{
		logBefore(logger, "校验附件名");
		JsonBack jsonBack = new JsonBack();
		PageData pd = new PageData();
		pd = this.getPageData();
		int checkResult = projectAttachService.checkSaveFile(pd);
		jsonBack.setCode(checkResult);
		return jsonBack;
	}

	/**
	 * 附件详情
	 */
	@RequestMapping(value="/findAttach")
	public ModelAndView findAttach() throws Exception {
		logBefore(logger, "附件详情");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("PRO_ID") != null && !pd.getString("PRO_ID").equals("")) {
			List<PageData> info = projectinfoService.findAttach(pd.getString("PRO_ID"));
			if(info.size() > 0 ) {
				mv.addObject("pd", info);
			}
		}
		mv.setViewName("projectinfo/attach_file_list");
		return mv;
	}

	/**
	 * 删除附件
	 */
	@RequestMapping(value="/deleteAttach")
	public void deleteAttach(PrintWriter out) throws Exception {
		logBefore(logger, "附件详情");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		projectAttachService.delete(pd);
		out.write("success");
		out.close();
	}


	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ProjectInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			projectinfoService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	/**
	 * 逻辑删除标的资产信息
	 */
	@RequestMapping(value="/deleteStatus")
	public @ResponseBody JsonBack deleteStatus() throws Exception {
		logBefore(logger, "删除ProjectInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		JsonBack jsonBack = new JsonBack();
		pd = this.getPageData();
		projectinfoService.updateInfoStatus(pd);
		//删除完成之后，根据ID获取该房产信息所属的标的PRO_ID
		PageData pageData = projectinfoService.findById(pd);
		String proId = pageData.get("PRO_ID").toString();
		jsonBack.setMessage(proId);
		return jsonBack;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改ProjectInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		projectinfoService.edit(pd);
		generateAttach(pd.getString("PRO_ID"));
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表ProjectInfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = projectinfoService.list(page);	//列出ProjectInfo列表
			mv.setViewName("projectinfo/projectinfo_list");
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
		logBefore(logger, "去新增ProjectInfo页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("projectinfo/projectinfo_edit");
			mv.addObject("msg", "save");
			mv.addObject("NO", pd.getString("NO"));
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAttachFile")
	public ModelAndView goAttachFile(){
		logBefore(logger, "跳转新增标的页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("projectinfo/attach_file_upload");
			mv.addObject("PRO_ID", pd.getString("PRO_ID"));
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
		logBefore(logger, "去修改ProjectInfo页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = projectinfoService.findById(pd);	//根据ID读取
			mv.setViewName("projectinfo/projectinfo_edit");
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
		logBefore(logger, "批量删除ProjectInfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				projectinfoService.deleteAll(ArrayDATA_IDS);
				//根据资产信息ID数组，查询到所有的标的ID
				List<PageData> list = projectinfoService.findProIdById(ArrayDATA_IDS);
				String arrStr = "";
				for(PageData pageData : list) {
					arrStr = arrStr + pageData.get("PRO_ID").toString()+",";
				}
				arrStr = arrStr.substring(0, arrStr.lastIndexOf(","));
				pd.put("msg", "ok");
				map.put("array", arrStr);
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
		logBefore(logger, "导出ProjectInfo到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("项目名称");	//1
			titles.add("资产类型");	//2
			titles.add("地域");	//3
			titles.add("产权面积");	//4
			titles.add("出售价格");	//5
			titles.add("市场价");	//6
			titles.add("产权证");	//7
			titles.add("土地证");	//8
			titles.add("资产权属");	//9
			titles.add("物权情况");	//10
			titles.add("附加信息");	//11
			titles.add("买卖合同");	//12
			titles.add("补充协议");	//13
			titles.add("房源清单");	//14
			titles.add("备案摘要");	//15
			titles.add("创建时间");	//16
			dataMap.put("titles", titles);
			List<PageData> varOList = projectinfoService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("PROPERTY_TYPE"));	//2
				vpd.put("var3", varOList.get(i).getString("LOCATION"));	//3
				vpd.put("var4", varOList.get(i).getString("AREA"));	//4
				vpd.put("var5", varOList.get(i).getString("SELL_PRICE"));	//5
				vpd.put("var6", varOList.get(i).getString("MARKET_PRICE"));	//6
				vpd.put("var7", varOList.get(i).getString("PROPERTY_CERT"));	//7
				vpd.put("var8", varOList.get(i).getString("LAND_CERT"));	//8
				vpd.put("var9", varOList.get(i).getString("PROPERTY_OWNER"));	//9
				vpd.put("var10", varOList.get(i).getString("PROPERTY_RIGHT"));	//10
				vpd.put("var11", varOList.get(i).getString("EXTRA_INFO"));	//11
				vpd.put("var12", varOList.get(i).getString("CONTRACT"));	//12
				vpd.put("var13", varOList.get(i).getString("ADDED_CONTRACT"));	//13
				vpd.put("var14", varOList.get(i).getString("PROPERTY_LIST"));	//14
				vpd.put("var15", varOList.get(i).getString("RECORD"));	//15
				vpd.put("var16", varOList.get(i).getString("CREATE_TIME"));	//16
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
