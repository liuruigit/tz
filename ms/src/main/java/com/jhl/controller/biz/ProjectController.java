package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.json.JsonBack;
import com.jhl.service.biz.ProjectInfoService;
import com.jhl.service.biz.ProjectService;
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
 * 类名称：ProjectController
 * 创建人：Hally
 * 创建时间：2016-01-14
 */
@Controller
@RequestMapping(value="/project")
public class ProjectController extends BaseController {

	String menuUrl = "project/list.do"; //菜单地址(权限用)
	@Resource(name="msProjectService")
	private ProjectService projectService;
	@Resource(name="projectinfoService")
	private ProjectInfoService projectInfoService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
//		pd.put("PROJECT_ID", this.get32UUID());	//主键
		pd.put("DELETE_STATE", 1);
		pd.put("CREATE_TIME", new Date());	//创建时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(pd.getString("OPEN_DATE") != null && pd.getString("END_DATE") != null) {
			Date openDate = sdf.parse(pd.getString("OPEN_DATE"));
			Date endDate = sdf.parse(pd.getString("END_DATE"));
			pd.put("OPEN_DATE", openDate.getTime());
			pd.put("END_DATE", endDate.getTime());
		}
		projectService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			projectService.delete(pd);
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
		logBefore(logger, "修改Project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("FINAL_AMOUNT", 0);
		pd.put("SELLED_AMOUNT", 0);
		if(pd.getString("OPEN_DATE") != null && pd.getString("END_DATE") != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date openDate = sdf.parse(pd.getString("OPEN_DATE"));
			Date endDate = sdf.parse(pd.getString("END_DATE"));
			pd.put("OPEN_DATE", openDate.getTime());
			pd.put("END_DATE", endDate.getTime());
		}
		projectService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Project");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = projectService.list(page);	//列出Project列表
			if(pd.getString("STATUS") != null && pd.getString("STATUS").trim().equals("")) {
				pd.put("STATUS", -1);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(PageData pageDate : varList) {
				if(pageDate.get("OPEN_DATE") != null) {
					String openStr = pageDate.get("OPEN_DATE").toString();
					pageDate.put("OPEN_DATE", sdf.format(Long.parseLong(openStr)));
				}
				if(pageDate.get("END_DATE") != null) {
					String endStr = pageDate.get("END_DATE").toString();
					pageDate.put("END_DATE", sdf.format(Long.parseLong(endStr)));
				}
			}
			mv.setViewName("project/project_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value="findByNo")
	public @ResponseBody
	JsonBack findByNo(Page page){
		JsonBack jsonBack = new JsonBack();
		logBefore(logger, "findByNo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			PageData obj = projectService.findByNo(pd);	//列出Project列表
			jsonBack.setObj(obj);
			jsonBack.setCode((obj == null) ? 0 : 1);//
			if(obj == null) {
				jsonBack.setCode(0);//NO不存在
			} else {
				if(projectInfoService.findInfoByNo(pd) >= 1) {
					jsonBack.setCode(1);//已存在资产信息
				} else {
					jsonBack.setCode(2);
				}
			}
		} catch(Exception e){
			jsonBack.setCode(0);
			logger.error(e.toString(), e);
		}
		return jsonBack;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Project页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("MIN","50000");
			pd.put("STEP","10000");
			pd.put("LIMIT","300000");
			pd.put("STATUS","0");
			pd.put("NO", SeqNoUtil.nextShortNo());
			mv.setViewName("project/project_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 修改标的状态
	 */
	@RequestMapping(value = "/updateStatus")
	@ResponseBody
	public JsonBack updateStatus() throws Exception {
		JsonBack jsonBack = new JsonBack();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据标的ID，判断该标的是否存在房产信息
		Integer count = projectInfoService.findInfoByNo(pd);
		if(count < 1 ) {
			jsonBack.setCode(0);//不能修改
		} else {
			projectService.updateStatus(pd);
			jsonBack.setCode(1);//可修改
		}
		return jsonBack;
	}

	/**
	 * 修改逻辑删除
	 */
	@RequestMapping("/deleteStatus")
	@ResponseBody
	public JsonBack deleteStatus() {
		logBefore(logger, "删除Project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		JsonBack jsonBack = new JsonBack();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			projectService.updateDeleteStatus(pd);//修改逻辑删除位
			//根据ID修改当前标的资产信息的逻辑删除状态
			projectInfoService.updateStatus(pd);
			jsonBack.setMessage("删除成功!");
		} catch(Exception e){
			logger.error(e.toString(), e);
			jsonBack.setMessage("删除失败!");
		}
		return jsonBack;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Project页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = projectService.findById(pd);	//根据ID读取
			//判断是否录入了房产信息
			Integer count = projectInfoService.findInfoByNo(pd);
			if(count > 0) {
				mv.addObject("msgType", "update");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(pd.get("OPEN_DATE") != null) {
				String openStr = pd.get("OPEN_DATE").toString();
				pd.put("OPEN_DATE", sdf.format(Long.parseLong(openStr)));
			}
			if(pd.get("END_DATE") != null) {
				String endStr = pd.get("END_DATE").toString();
				pd.put("END_DATE", sdf.format(Long.parseLong(endStr)));
			}
			mv.setViewName("project/project_edit");
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
		logBefore(logger, "批量删除Project");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				projectService.deleteAll(ArrayDATA_IDS);
				projectInfoService.deleteAllById(ArrayDATA_IDS);
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
		logBefore(logger, "导出Project到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("项目名称");	//1
			titles.add("产品编号");	//2
			titles.add("保障级别");	//3
			titles.add("付息方式");	//4
			titles.add("融资金额");	//5
			titles.add("已融资金额");	//6
			titles.add("市场价");	//7
			titles.add("起投金额");	//8
			titles.add("累加金额");	//9
			titles.add("限投金额");	//10
			titles.add("预期年化收益率");	//11
			titles.add("服务费率");	//12
			titles.add("开放购买时间");	//13
			titles.add("状态");	//14
			titles.add("项目描述");	//15
			titles.add("创建时间");	//16
			titles.add("是否推荐");	//17
			titles.add("预留信息1");	//18
			titles.add("预留信息2");	//19
			titles.add("预留信息3");	//20
			dataMap.put("titles", titles);
			List<PageData> varOList = projectService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("name"));	//1
				vpd.put("var2", varOList.get(i).getString("no"));	//2
				vpd.put("var3", varOList.get(i).getString("guarantee"));	//3
				vpd.put("var4", varOList.get(i).getString("payInterestWay"));	//4
				vpd.put("var5", varOList.get(i).getString("amount"));	//5
				vpd.put("var6", varOList.get(i).getString("selledAmount"));	//6
				vpd.put("var7", varOList.get(i).getString("marketPrice"));	//7
				vpd.put("var8", varOList.get(i).getString("min"));	//8
				vpd.put("var9", varOList.get(i).getString("step"));	//9
				vpd.put("var10", varOList.get(i).getString("limit"));	//10
				vpd.put("var11", varOList.get(i).getString("annualRate"));	//11
				vpd.put("var12", varOList.get(i).getString("serviceRate"));	//12
				vpd.put("var13", varOList.get(i).getString("openDate"));	//13
				vpd.put("var14", varOList.get(i).getString("status"));	//14
				vpd.put("var15", varOList.get(i).getString("desr"));	//15
				vpd.put("var16", varOList.get(i).getString("createTime"));	//16
				vpd.put("var17", varOList.get(i).getString("recommend"));	//17
				vpd.put("var18", varOList.get(i).getString("extra1"));	//18
				vpd.put("var19", varOList.get(i).getString("extra2"));	//19
				vpd.put("var20", varOList.get(i).getString("extra3"));	//20
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
