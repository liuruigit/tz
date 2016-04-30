package com.jhl.controller.biz;

import com.google.common.base.Strings;
import com.jhl.controller.base.BaseController;
import com.jhl.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * 类名称：ExtraFileUploadController
 * 创建人：Hally
 * 创建时间：2016-03-21
 */
@Controller
@RequestMapping(value="/file")
public class ExtraFileUploadController extends BaseController {

	//basePath + img/,basePath + html/
	String menuUrl = "file/list.do"; //菜单地址(权限用)
	
	/**
	 * basepath/img,basepath/html
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(
			@RequestParam(required=false) MultipartFile file
			) throws Exception{
		logBefore(logger, "文件上传");
		Map<String,String> map = new HashMap<String,String>();
		String fileName = "";
		PageData pd = new PageData();
		String filePath = "";		//文件上传路径
		if (null != file && !file.isEmpty()) {
			pd = this.getPageData();
			String bath = getBasePath();
			filePath = getBasePath() + Const.FILEPATHFILE;//文件上传路径
			System.out.println(filePath);
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());//文件名
			map.put("result", "ok");//执行上传
		}else{
			map.put("result", "fail");
			System.out.println("上传失败");
		}
		map.put("url", fileName);
		return AppUtil.returnObject(pd, map);
	}

	private String tansZH_CN_Name(String str){
		return !Strings.isNullOrEmpty(str)&&RegexUtil.regexZH_CN(str)?PinyinUtil.getPinYinHeadChar(str):str;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Pictures");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String[] fileNames = pd.getString("data").split(",");
			for (int i = 0; i < fileNames.length; i++) {
				String fileName = fileNames[i];
				DelAllFile.delFolder(fileName); //删除图片
			}
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goUpload")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Project页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
		pd = this.getPageData();
			mv.setViewName("upload/upload");
			mv.addObject("fileBelong", pd.getString("fileBelong"));
			mv.addObject("fileType", pd.getString("fileType"));
			mv.addObject("pd", pd);
		} catch (Exception e) {
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
