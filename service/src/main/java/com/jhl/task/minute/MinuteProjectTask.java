package com.jhl.task.minute;

import com.jhl.pojo.biz.Project;
import com.jhl.service.BaseService;
import com.jhl.service.ProjectService;
import com.jhl.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15.
 */
@Component
@Lazy(false)
public class MinuteProjectTask extends BaseService{

    private static final Logger logger = LoggerFactory.getLogger(MinuteProjectTask.class);
    @Autowired
    private ProjectService projectService;

    /**
     * 每三分鐘檢查一次
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void periodOpenPro(){
        logger.info("自动检查新建状态下的标的");
        try {
            Long now = System.currentTimeMillis();
            List<Project> projects = projectService.queryTaskPro();
            for (Project project : projects) {
                if(projectService.queryInfoForCount(project) > 0) {
                    Long openDate = project.getOpenDate();
                    Long closeDate = project.getEndDate();
                    if (project.getStatus() == Project.Status.INIT.getValue()) {
                        if (now >= closeDate || Math.abs(now - closeDate) < 60 * 1000){//當前時間大於关闭时间，或她們的差值在1分鐘以內，都过期
                            projectService.updateStatus(project,Project.Status.OUT_OF_DATE);
                        }
                    }else if (project.getStatus() == Project.Status.CREATED.getValue()){
                        if (now >= openDate || Math.abs(now - openDate) < 60 * 1000){//當前時間大於開放購買時間，或她們的差值在1分鐘以內，都開發
                            projectService.updateStatus(project,Project.Status.INIT);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(SessionUtil.getNo() + "定時任務執行異常",e);
        }
    }
}
