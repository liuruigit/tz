package com.jhl.controller;

import com.jhl.common.constant.CommonConstant;
import com.jhl.dto.BaseDto;
import com.jhl.dto.ProDto;
import com.jhl.pojo.json.JsonBack;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.service.ProjectService;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by hallywu on 16/1/29.
 */
@Controller()
@RequestMapping("pro")
public class ProjectController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    /**
     * 获取在售标的投资人
     * @param dto
     * @return
     */
    @RequestMapping(value = "investors")
    public @ResponseBody
    JsonBack proInvestor(ProDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            PageInfo pageInfo = getPageInfo(dto.getPage());
            int id = parseId(dto.getProId());
            PaginationResult<Map<String,Object>> datas = projectService.queryProInvestors(pageInfo,id);
            parsePageData(datas,"ACC_NAME");
            jsonBack.setObj(datas);
            jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "查询在售标的异常",e);
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        }
        return jsonBack;
    }

    /**
     * 获取在售标的
     * @param page
     * @return
     */
    @RequestMapping(value = "list/open")
    public @ResponseBody
    JsonBack open(String page) {
        JsonBack jsonBack = new JsonBack();
        try {
            PageInfo pageInfo = getPageInfo(page);
            jsonBack.setObj(projectService.queryHotList(pageInfo));
            jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "查询在售标的异常",e);
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        }
        return jsonBack;
    }

    /**
     * 获取所有标的
     * @param dto
     * @return
     */
    @RequestMapping(value = "list/all")
    public @ResponseBody JsonBack all(ProDto dto) {
        JsonBack jsonBack = new JsonBack();
        try {
            PageInfo pageInfo = getPageInfo(dto.getPage());
            jsonBack.setObj(projectService.queryList(pageInfo,dto));
            jsonBack.setCode(CommonConstant.JSON_BACK_SUCCESS);
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "查询所有标的异常",e);
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        }
        return jsonBack;
    }

    /**
     * 查询推荐标的
     * @return
     */
    @RequestMapping(value = "recommend")
    public @ResponseBody
    JsonBack recommend(){
        JsonBack jsonBack = new JsonBack();
        try {
            return buildSuccJsonBack(jsonBack,projectService.queryRecommendProjs());
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "查询推荐标的!",e);
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        }
        return buildErrorJsonBack(jsonBack);
    }

    /**
     * 查询标的
     * @return
     */
    @RequestMapping(value = "info")
    public @ResponseBody JsonBack info(String id){
        JsonBack jsonBack = new JsonBack();
        try {
            return buildSuccJsonBack(jsonBack,projectService.queryProInfo(parseId(id)));
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "查询标的!",e);
            jsonBack.setCode(CommonConstant.JSON_BACK_FAILED);
        }
        return buildErrorJsonBack(jsonBack);
    }


}
