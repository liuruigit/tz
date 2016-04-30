package com.jhl.Test;

import com.alibaba.fastjson.JSONObject;
import com.jhl.service.AchieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/3/28.
 */
@Controller
@RequestMapping("wx")
public class TestUtil {

    @Autowired
    private AchieveService achieveService;
    /**
     * 测试获取素材列表
     */
    @RequestMapping(value = "getImageTextList")
    public ModelAndView getImageTextList(String type, String offset, String count) {
        ModelAndView mv = new ModelAndView();
        System.out.println("调用素材列表接口:类型："+type+"; 偏移量："+offset+"; 数量："+count);
        JSONObject jsonObject = achieveService.getMediaList(type, offset,count);
        mv.addObject("msg", jsonObject);
        mv.setViewName("forward:/index.jsp");
        return mv;
    }

    /**
     * 测试获取素材
     */
    @RequestMapping(value = "getImageText")
    public void getImageTextList(String keyName, String mediaId) {
        System.out.println("调用获取永久素材接口： 素材ID："+mediaId);
        achieveService.getMediaById(keyName,mediaId);
    }
}
