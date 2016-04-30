package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.common.InvestStatus;
import com.jhl.dao.IProjectDao;
import com.jhl.db.SQLOperator;
import com.jhl.dto.ProDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.Project;
import com.jhl.pojo.biz.SupplierProjectMapping;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.security.JwtHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 16/1/29.
 */
@Service("projectService")
public class ProjectService extends BaseService<Project> {

    @Resource(name = "projectDao")
    IProjectDao projectDao;

    /**
     * 查询推荐标的
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryRecommendProjs() throws Exception {
        return projectDao.queryRecommendProjs();
    }

    /**
     * 查询标的详情
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryProInfo(int id) throws Exception {
        Map<String, Object> proInfo = projectDao.queryProInfo(id);
        List<Map<String, Object>> attach = projectDao.queryProAttach(id);
        proInfo.put("attachFiles",attach);
        return proInfo;
    }

    /**
     * 查询标的 - 甲方信息
     * @param id
     * @return
     * @throws Exception
     */
    public SupplierProjectMapping queryMappingSupplier(String no) throws Exception {
        return projectDao.queryMappingSupplier(no);
    }

    /**
     * 分页查询在售热标
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryHotList(PageInfo pageInfo) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("p.status", SQLOperator.equal,Project.Status.INIT.getValue()));
        sqlConditions.add(new SQLCondition("p.delete_state", SQLOperator.equal,1));
        return projectDao.queryForPageMap(sqlConditions, pageInfo, "order by p.open_date desc");
    }

    /**
     * 查询所有新建、购买中的标的
     * @return
     * @throws Exception
     */
    public List<Project> queryTaskPro() throws Exception {
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("status", SQLOperator.lessEqualThan,Project.Status.INIT.getValue()));
        sqlConditions.add(new SQLCondition("delete_state", SQLOperator.equal,1));
        return queryList(sqlConditions, "");
    }

    /**
     * 修改标的状态
     * @return
     * @throws Exception
     */
    public void updateStatus(Project project,Project.Status status) throws Exception {
        projectDao.updateStatus(project.getId(), status);
    }

    /**
     * 查询标的是否存在房产信息
     */
    public Integer queryInfoForCount(Project project) {
        return projectDao.queryInfoForCount(project);
    }

    /**
     * 查询标的投资人
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryProInvestors(PageInfo pageInfo,int proId) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("proId", SQLOperator.equal,proId));
        sqlConditions.add(new SQLCondition("status", SQLOperator.equal, InvestStatus.APPLYING.getValue()));
        sqlConditions.add(new SQLCondition("delete_state", SQLOperator.equal, 1));
        return projectDao.queryInvestAcc(sqlConditions, pageInfo, "order by create_time desc");
    }

    /**
     * 分页查询标
     * @param pageInfo
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryList(PageInfo pageInfo, ProDto dto) throws Exception{
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        String order = "order by ";
        if ("ai".equalsIgnoreCase(dto.getOrderName())){
            order += "p.status asc,p.create_time desc";
        }else{
            if (!Strings.isNullOrEmpty(dto.getOrderName()) &&
                    ("desc".equalsIgnoreCase(dto.getOrder()) || "asc".equalsIgnoreCase(dto.getOrder()))) {
                order += dto.getOrderName() + " " +dto.getOrder() +",p.status asc,p.create_time desc";
            }else{
                order += "p.status asc,p.create_time desc";
            }
        }

        if (Strings.isNullOrEmpty(dto.getStatus())){
            sqlConditions.add(new SQLCondition("p.status", SQLOperator.greatThan,Project.Status.CREATED.getValue()));
        }else{
            sqlConditions.add(new SQLCondition("p.status", SQLOperator.equal,dto.getStatus()));
        }
        if (!Strings.isNullOrEmpty(dto.getProType())){
            sqlConditions.add(new SQLCondition("i.PROPERTY_TYPE", SQLOperator.equal,dto.getProType()));
        }
        if (!Strings.isNullOrEmpty(dto.getDays())){
            sqlConditions.add(new SQLCondition("p.days", SQLOperator.lessEqualThan,dto.getDays()));
        }

        String token = dto.getToken();
        sqlConditions.add(new SQLCondition("p.vip_limit", SQLOperator.lessEqualThan,getVip(token)));
        sqlConditions.add(new SQLCondition("p.delete_state", SQLOperator.equal,1));
        return projectDao.queryForPageMap(sqlConditions,pageInfo,order);
    }

    /**
     * 获取VIP等级
     * @param token
     * @return
     * @throws Exception
     */
    private int getVip(String token) throws Exception{
        if (Strings.isNullOrEmpty(token))return 0;
        Account acc = JwtHolder._instance().verifyToken(token);
        if (acc == null)return 0;
        return acc.getVip();
    }
}
