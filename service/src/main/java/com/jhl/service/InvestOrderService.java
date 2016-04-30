package com.jhl.service;

import com.google.common.base.Strings;
import com.jhl.common.InvestStatus;
import com.jhl.dao.InvestOrderDao;
import com.jhl.dao.impl.rmb.ProjectDao;
import com.jhl.db.SQLOperator;
import com.jhl.dto.InvestDto;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.InvestOrder;
import com.jhl.pojo.biz.Project;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/27.
 * 投资订单service
 */
@Service
public class InvestOrderService extends BaseService<InvestOrder>{
    private static Logger logger = LoggerFactory.getLogger(InvestOrderService.class);
    @Autowired
    ProjectDao projectDao;
    @Autowired
    InvestOrderDao investOrderDao;
    @Autowired
    AccountService accountService;

    public List<InvestOrder> getInvestOrderByProjectId(Project project,InvestStatus status) throws Exception{
        SQLCondition sqlCondition1 = new SQLCondition("pro_id", SQLOperator.equal,project.getId());
        SQLCondition sqlCondition2 = new SQLCondition("status", SQLOperator.equal, status.getValue());
        List<SQLCondition> conditions = new ArrayList<SQLCondition>();
        conditions.add(sqlCondition1);
        conditions.add(sqlCondition2);
        return queryListWithCondition(conditions, "");
    }

    /**
     * 查询投资订单
     * @param pageInfo
     * @param account
     * @param dto
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryInvestRecord(PageInfo pageInfo, Account account, InvestDto dto) throws Exception{
        List<SQLCondition> conditions = new ArrayList<SQLCondition>();
        SQLCondition sqlCondition = new SQLCondition("o.acc_id", SQLOperator.equal,account.getId());
        if (!Strings.isNullOrEmpty(dto.getStatus())){
            SQLCondition sqlCondition1 = new SQLCondition("o.status", SQLOperator.equal,dto.getStatus());
            conditions.add(sqlCondition1);
        }
        conditions.add(sqlCondition);
        return investOrderDao.queryInvestRecord(conditions,pageInfo,"order by create_time desc");
    }
}
