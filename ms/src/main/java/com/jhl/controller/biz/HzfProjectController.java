package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.biz.Project;
import com.jhl.service.ProjectService;
import com.jhl.service.TransactionService;
import com.jhl.util.Const;
import com.jhl.util.Jurisdiction;
import com.jhl.util.PageData;
import com.jhl.util.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 合作方标的管理
 * 类名称：ProjectController
 * 创建人：Hally
 * 创建时间：2016-01-14
 */
@Controller
@RequestMapping(value="/hzfProject")
public class HzfProjectController extends BaseController {

	String menuUrl = "hzfProject/list.do"; //菜单地址(权限用)
	@Resource(name="projectService")
	private ProjectService projectService;
	@Resource(name="msProjectService")
	private com.jhl.service.biz.ProjectService msprojectService;
	@Autowired
	private TransactionService transactionService;


	/**
	 * 合作方清算标
	 */
	@RequestMapping(value = "/settlement")
	public ModelAndView settlement(PrintWriter out)  {
		logBefore(logger, "合作方结算标的");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Integer id = Integer.parseInt(pd.getString("ID"));
			Double fianlAmount = Double.parseDouble(pd.getString("FINAL_AMOUNT").toString());
			Project project = projectService.queryById(id);
			project.setFinalAmount(new Double(fianlAmount).longValue());
			transactionService.preSettle(project);
			mv.addObject("msg","success");
		} catch (Exception e) {
			logger.error(SessionUtil.getNo() + "结算失败!", e);
			mv.addObject("msg","false");
		}
		mv.setViewName("save_result");
		return mv;
	}

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
		msprojectService.save(pd);
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
			msprojectService.delete(pd);
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
		msprojectService.edit(pd);
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
			List<PageData>	varList = msprojectService.list(page);	//列出Project列表
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
			mv.setViewName("hzfproject/hzfproject_list");
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
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Project页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = msprojectService.findById(pd);	//根据ID读取
			mv.setViewName("hzfproject/hzfproject_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面
	 */
/*	@RequestMapping(value="/goAdd")
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
	}*/


	/*
	 * 导出到excel
	 * @return
	 */
/*	@RequestMapping(value="/excel")
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
			List<PageData> varOList = msprojectService.listAll(pd);
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
	}*/

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
