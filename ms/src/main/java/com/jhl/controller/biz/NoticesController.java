package com.jhl.controller.biz;

import com.jhl.constant.ConfigKey;
import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.service.HtmlGeneratorService;
import com.jhl.service.SysConfig;
import com.jhl.service.biz.ConfigService;
import com.jhl.service.biz.ContentTypeService;
import com.jhl.service.biz.NoticesService;
import com.jhl.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * 类名称：NoticesController
 * 创建人：martin
 * 创建时间：2016-01-24
 *
 * 网站前端接口返回：List<Map<String,Object>>
 */
@Controller
@RequestMapping(value="/notices")
public class NoticesController extends BaseController {
	
	String menuUrl = "notices/list.do"; //菜单地址(权限用)
	@Resource(name="noticesService")
	private NoticesService noticesService;

    @Autowired
    private HtmlGeneratorService htmlGeneratorService ;

	@Resource(name="msConfigService")
    private ConfigService configService;

    @Autowired
    private ContentTypeService contentTypeService;


	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Notices");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATE_TIME", new Date());
		noticesService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Notices");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			noticesService.delete(pd);
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
		logBefore(logger, "修改Notices");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		noticesService.edit(pd);
		mv.addObject("msg","success");
        addPageInfo(mv , pd );
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page ){
		logBefore(logger, "列表Notices");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = noticesService.list(page);	//列出Notices列表121.199.130.29
			pd.put("local_ip", SysConfig.geteConfigByKey(ConfigKey.GEN_IP));
//			pd.put("local_ip","http://10.139.51.42:80/jhlMs/");
			mv.setViewName("notices/notices_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
            addPageInfo(mv , pd );
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
		logBefore(logger, "去新增Notices页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("notices/notices_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);

            addPageInfo(mv , pd );
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
		logBefore(logger, "去修改Notices页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = noticesService.findById(pd);	//根据ID读取
			mv.setViewName("notices/notices_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
            addPageInfo(mv , pd );
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

    /**
     * 去preview页面
     */
    @RequestMapping(value="/goPreview")
    public ModelAndView goPreview(){
        logBefore(logger, "去preview页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = noticesService.findById(pd);	//根据ID读取
            mv.setViewName("notices/notices_preview");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
            addPageInfo(mv , pd );
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 生成页面
     */
    @ResponseBody
    @RequestMapping(value="/generateHtml")
    public Map generateHtml(String url ,String ID , HttpServletRequest request){
        logBefore(logger, "生成页面");
		PageData pd = new PageData();
        Map<String,String> rs = new HashMap<String,String>();
        try {
			pd.put("ID", ID);
			String c_id = String.format("notices/notices_web%s" , ID);
			//根据ID查询详细内容
			PageData pageData = noticesService.findById(pd);
			String webPath = htmlGeneratorService.generateNotices(pageData.getString("CONTENT"), pageData.getString("TITLE"), ID, getBasePath());
            String path = htmlGeneratorService.generateByPostHtml(getBasePath(), url , String.format("notices/notices_%s" , ID) , request.getHeader("Cookie") );
			pd.put("URL", path);
			pd.put("WEB_URL", webPath);
			pd.put("ID", ID);
			noticesService.updateURLByID(pd);
			rs.put("path", path ) ;
            rs.put("result" , "ok");
        } catch (Exception e) {
            logger.error(e.toString(), e);
            rs.put("result" , "error");
        }
        return rs;
    }

    /**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Notices");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				noticesService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Notices到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("id");	//1
			titles.add("标题");	//2
			titles.add("富文本内容");	//3
			titles.add("轮播图片URL");	//4
			titles.add("创建日期");	//5
			titles.add("逻辑删除标志位：1正常0删除");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = noticesService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("NOTICES_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("TITLE"));	//2
				vpd.put("var3", varOList.get(i).getString("CONTENT"));	//3
				vpd.put("var4", varOList.get(i).getString("PICTURE_URL"));	//4
				vpd.put("var5", varOList.get(i).getString("CREATE_TIME"));	//5
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


    /**
     * 添加页面需要用到的数据
     * @param mv
     * @param pd
     */
    private void addPageInfo(ModelAndView mv , PageData pd ) throws Exception {
        setContentTypes(mv , pd );
        setImagePath(mv);
    }

    /**
     * 添加页面用到的contentType
     * @param mv
     * @param pd
     */
    private void setContentTypes(ModelAndView mv , PageData pd ) throws Exception {
        List<PageData> pageDatas = contentTypeService.listAll(pd);
        mv.addObject("contentTypes", pageDatas);
    }

    /**
     * 设置图片地址
     * @param mv 返回前端的ModelAndView
     * @throws Exception
     */
    private void setImagePath(ModelAndView mv) throws Exception {

        //md.addObject("gateway",configService.findValueByKey(ConfigKey.STATIC_APP_DOMAIN) + Const.FILEPATHIMG);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String proName = request.getContextPath();
		//System.out.println(proName+"/"+Const.FILEPATHFILESTR);
		mv.addObject("gateway",proName +"/"+ Const.FILEPATHFILESTR );
        mv.addObject("notices_path" , Const.FILEPATH_NOTICES);//图片存放的路径
    }
}
