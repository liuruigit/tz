package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.service.biz.ConfigService;
import com.jhl.util.*;
import com.jhl.constant.ConfigKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hally on 2016/1/24.
 * 生成Html页面
 */
@Service(value = "htmlGeneratorService")
public class HtmlGeneratorService {

    @Autowired
    ConfigService configService;

    private String htmlFtlPath = "appHtml";

    /**
     * 生成移动端附件查看页面
     * @param content   附件图片
     * @param title     合同/补充协议
     * @param projectNo 资产编号
     * @param ftlName   模板名称
     * @return
     * @throws Exception
     * todo 增加FileRes类，管理web端的图片加载地址，和移动端的网页URL；图片的大小标准限制
     */
    private String generateHtml(String content,String title,String projectNo,String ftlName, String basePath) throws Exception{
        //获取移动端资源服地址
        String gateway = configService.findValueByKey(ConfigKey.STATIC_APP_DOMAIN);
        String url = title + "/" +title+"_"+projectNo+".html";
        String path = basePath + Const.HTML_APP_OUTPUT+title+"/"+title+"_"+projectNo+".html";
        System.out.println(path);
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("title",title);
        root.put("images",fileParse(content));
        root.put("gateway",gateway);
        Freemarker.printFile(ftlName, root, path, htmlFtlPath);
        return url;
    }

    /**
     * 生成合同附件页面
     * @param contracts
     * @param projectNo
     * @return
     * @throws Exception
     */
    public String generateContract(String contracts,String name, String projectNo, String basePath) throws Exception{
        return generateHtml(contracts, PinyinUtil.getPinYinHeadChar(name),projectNo,"file.ftl", basePath);
    }

    /**
     * 生成内容详情
     * @param content   编辑的内容详情
     * @param title     内容标题
     * @param notiesId 内容编号（ID）
     * @param ftlName   模板名称
     * @return
     * @throws Exception
     * todo 增加FileRes类，管理web端的图片加载地址，和移动端的网页URL；图片的大小标准限制
     */
    private String generateNotiesHtml(String content,String title, String name, String notiesId,String ftlName, String basePath) throws Exception{
        //获取移动端资源服地址
        String gateway = configService.findValueByKey(ConfigKey.WEE_HOME);
        String url = "notices/web" +name+"_"+notiesId+".html";
        String path = basePath + Const.HTML_APP_OUTPUT+"notices/web"+name+"_"+notiesId+".html";
        System.out.println(path);
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("title",title);
        root.put("content",content);
        root.put("gateway",gateway);
        Freemarker.printFile(ftlName, root, path, htmlFtlPath);
        return url;
    }

    /**
     * 生成内容详情页面
     * @param contracts
     * @return
     */
    public String generateNotices(String contracts,String title, String projectNo, String basePath) throws Exception{
        return generateNotiesHtml(contracts,title, PinyinUtil.getPinYinHeadChar(title), projectNo,"notices.ftl", basePath);
    }


    private String[] fileParse(String contracts) {
        Assert.isTrue(!Strings.isNullOrEmpty(contracts));
        String subCon = contracts.substring(1);
        return subCon.split(",");
    }

    /**
     * 根据动态url生成静态页面
     * @param dynamicUrl 动态URL
     * @param targetFileName 目标文件名称
     * @return path 生成的路径
     * @throws Exception
     */
    public String generateByPostHtml(String basePath, String dynamicUrl , String targetFileName , String cookies ) throws  Exception {
        String path = basePath + Const.HTML_APP_OUTPUT + targetFileName +".html";
        String url = targetFileName +".html";
        System.out.println(path);
        String html = HttpClient431Util.doGet(new HashMap<String, String>(),dynamicUrl, cookies);
        writeHtmlFile( html , path );
        return url;
    }


    private void writeHtmlFile(String html , String path){
        try {
            File file = new File(path);
            if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
                file.getParentFile().mkdirs();				//不存在就全部创建
            }
            if(file.exists()){
                file.deleteOnExit();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            out.write(html);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
