package com.jhl.controller;


import com.google.common.base.Strings;
import com.jhl.cache.RedisClient;
import com.jhl.cache.VerifyCodeCache;
import com.jhl.common.constant.CommonConstant;
import com.jhl.common.util.IpAddressUtil;
import com.jhl.dto.BaseDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.json.JsonBack;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.security.JwtHolder;
import com.jhl.service.AccountService;
import com.jhl.service.SmsService;
import com.jhl.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vic wu on 2015/8/19.
 */
@Component(value = "webBaseController")
public class BaseController {
    @Autowired
    VerifyCodeCache verifyCodeCache;
    @Autowired
    AccountService accountService;
    @Autowired
    RedisClient redisClient;
    @Autowired
    SmsService smsService;

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    private String ERROR_MSG = "服务暂不可用，请联系客服人员！";

    protected String getAddress(HttpServletRequest request){
        String info = "";
        String addrInfo = "";
        try {
            addrInfo = IPUtil.findAddressByIp(IpAddressUtil.getIpAddr(request));
            if(addrInfo != null) {
                JSONObject jsonObject = JSONObject.fromObject(addrInfo);
                if(jsonObject.get("status") != null && jsonObject.get("status").equals("0")) {//返回成功
                    jsonObject = JSONObject.fromObject(jsonObject.get("content"));
                    jsonObject = JSONObject.fromObject(jsonObject.get("address_detail"));
                    info = jsonObject.get("province") + " " + jsonObject.get("city");
                }
            }
        } catch (Exception e) {
            logger.error("百度解析位置异常:{}",e);
            return "";
        }
        return info;
    }

    protected Integer parseId(String id) throws Exception{
        if (!(StringUtils.isNotBlank(id) && StringUtils.isNumeric(id))) {
            throw new IllegalArgumentException("非法的请求参数！");
        }
        return Integer.parseInt(id);
    }

    protected boolean checkFingerPwd(BaseDto dto, Account account) throws Exception {
        if (Strings.isNullOrEmpty(dto.getPayToken()))return true;
        if (JwtHolder._instance().isOutOfDate(dto.getPayToken())) {
            return account != null && !Strings.isNullOrEmpty(account.getFingerPwd())
                    && dto.getPayToken().equalsIgnoreCase(account.getFingerPwd());
        }else {
            return false;
        }
    }

    protected JsonBack checkWithFigerPwd(BaseDto dto, Account account,JsonBack jsonBack) throws Exception{
        if (Strings.isNullOrEmpty(dto.getPayToken())){
            if(!PasswordUtil.verify(dto.getTradePwd(),account.getTradePwd())){
                return buildErrorJsonBack(jsonBack,"交易密码错误！");
            }else{
                return buildSuccJsonBack(jsonBack);
            }
        }else{
            if(checkFingerPwd(dto,account)) {
                return buildSuccJsonBack(jsonBack);
            }else{
                return buildErrorJsonBack(jsonBack,"指纹密码认证失败");
            }
        }
    }

    protected void parsePageData(PaginationResult<Map<String,Object>> data,String key)throws Exception{
        List<Map<String,Object>> result = data.getResultList();
        if (result == null)return;
        for (Map<String,Object> ele:result) {
            parseVal(ele,key);
        }

    }

    private String mobileHide(String mobile){
        if (ValidateUtil.checkMobile(mobile)){
            return mobile.substring(0,2) + "***" + mobile.substring(8,11);
        }else {
            return mobile;
        }
    }

    protected void parseVal(Map<String,Object> datas,String key){
        String val = (String) datas.get(key);
        datas.put(key, Strings.isNullOrEmpty(val)?"": mobileHide(DESUtil.getDesString(val)));
    }

    protected PageInfo getPageInfo(String page){
        int pageNum = CommonConstant.PAGE_SIZE;
        int currentPage = 1;
        if (StringUtils.isNotBlank(page) && StringUtils.isNumeric(page)) {
            currentPage = Integer.parseInt(page);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }
        int startRow = (currentPage - 1) * pageNum;
        return new PageInfo(startRow, pageNum);
    }

    protected JsonBack buildJsonBack(JsonBack jsonBack,String msg,int code) {
        jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        jsonBack.setMessage(msg);
        jsonBack.setCode(code);
        return jsonBack;
    }

    protected JsonBack buildErrorJsonBack(JsonBack jsonBack,String msg) {
        jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        jsonBack.setMessage(msg);
        return jsonBack;
    }

    protected JsonBack buildErrorJsonBack(JsonBack jsonBack) {
        jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        return jsonBack;
    }

    protected JsonBack buildSuccJsonBack(JsonBack jsonBack,Object obj) {
        jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
        jsonBack.setObj(obj);
        return jsonBack;
    }

    protected JsonBack buildSuccJsonBack(JsonBack jsonBack,Object obj,String msg) {
        jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
        jsonBack.setObj(obj);
        jsonBack.setMessage(msg);
        return jsonBack;
    }

    protected JsonBack buildSuccJsonBack(JsonBack jsonBack) {
        jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
        return jsonBack;
    }

    /**
     * 文件上传
     * @param request
     * @return
     * @throws Exception
     */
    protected List<String> fileUpload(HttpServletRequest request,String bizPath) throws Exception{
        List<String> result = new ArrayList<String>();
        String uploadBasePath = "upload/";
        String baseFilePath = request.getSession().getServletContext().getRealPath("/") + uploadBasePath;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //记录上传过程起始时的时间，用来计算上传时间
//                int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    String path = baseFilePath + bizPath + File.separator;
                    String  fileName = FileUploadUtil.saveUploadFile(file,path);
                    result.add(uploadBasePath + bizPath +"/" + fileName);
                }
                //记录上传该文件后的时间
//                int finaltime = (int) System.currentTimeMillis();
//                System.out.println(finaltime - pre);
            }

        }
        return result;
    }

    public static String baseUploadPath(HttpServletRequest request,String bizPath){
        String uploadBasePath = "upload/";
        String baseFilePath = request.getSession().getServletContext().getRealPath("/") + uploadBasePath;
        return baseFilePath + bizPath + File.separator;
    }

    public static String savePath(String bizPath,String fileName){
        return "upload/" + bizPath +"/" + fileName;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
