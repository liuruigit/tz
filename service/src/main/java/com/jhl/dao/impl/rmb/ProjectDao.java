package com.jhl.dao.impl.rmb;

import com.jhl.common.InvestStatus;
import com.jhl.dao.IProjectDao;
import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.Project;
import com.jhl.pojo.biz.SupplierProjectMapping;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by amoszhou on 16/1/25.
 */
@Repository(value = "projectDao")
public class ProjectDao extends BaseDao implements IProjectDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int updateInvestAmount(Integer id, Money amount) throws Exception {
        String sql = " update t_project set update_time= Now(),  SELLED_AMOUNT = SELLED_AMOUNT + ? where id = ? and  AMOUNT >= SELLED_AMOUNT + ? ";
        return baseDao.update(sql, amount.getAmount(), id, amount.getAmount());
    }

    @Override
    public int updateStatusIfFull(Integer id) throws Exception {
        String sql = "update t_project set update_time= Now(),sold_out_date = Now(),status = ? where ID = ?  and AMOUNT = SELLED_AMOUNT";
        return baseDao.update(sql, Project.Status.FULL.getValue(), id);
    }

    @Override
    public int updateStatus(Integer id,Project.Status status) throws Exception {
        String sql = "update t_project set update_time= Now(),status = ? where ID = ?";
        return baseDao.update(sql, status.getValue(), id);
    }

    @Override
    public Integer queryInfoForCount(Project project) {
        String sql = "select count(ID) from t_project_info where PRO_ID = ?";
        return baseDao.queryForInt(sql, project.getId());
    }

    @Override
    public int preSettle(Project project,Project.Status status) throws Exception {
        String sql = "update t_project set update_time= Now(),status = ?,final_amount=? where ID = ?";
        return baseDao.update(sql, status.getValue(), project.getFinalAmount(), project.getId());
    }

    @Override
    public SupplierProjectMapping queryMappingSupplier(String no) throws Exception {
        String sql = "select p.`GS_CODE`,p.`LOCATION`,p.`NAME`," +
                "p.`TOTAL_AMOUNT`,p.`REAL_TOTAL_AMOUNT`,p.`SUPPLIER_REAL_AMOUNT`,p.`SUPPLIER_HOLD_PERC`,p.`PRO_ID` from T_SUPPLIER_PROJECT_MAPPING p where p.PRO_ID = ?";
        SupplierProjectMapping supplierProjectMapping = null;
        try {
            supplierProjectMapping = jdbcTemplate.queryForObject(sql, new Object[]{no},
                    BeanPropertyRowMapper.newInstance(SupplierProjectMapping.class));
        } catch (EmptyResultDataAccessException exceptione) {}
        return supplierProjectMapping;
    }

    @Override
    public Map<String, Object> queryProName(Integer id) throws Exception {
        String sql = "select p.`NAME` from t_project_info p where p.PRO_ID = ?";
        return baseDao.queryForMap(sql,id);
    }

    @Override
    public List<Map<String, Object>> queryProAttach(int id) throws Exception {
        return baseDao.queryForJsonMap(SqlContainer.PRO_ATTACH,id);
    }

    @Override
    public List<Map<String, Object>> queryRecommendProjs() throws Exception {
        return baseDao.queryForJsonMap(SqlContainer.PRO_RECOMMEND,new Date().getTime());
    }

    @Override
    public boolean checkInvest(int pid) throws Exception {
        String sql = "select sum(o.AMOUNT) as totalInvestAmount,p.amount from t_invest_order o,t_project p " +
                "where p.ID = o.PRO_ID and o.`STATUS` = ? and p.ID = ? and p.status = ?";
        Map<String, Object> result = baseDao.queryForMap(sql, InvestStatus.APPLYING.getValue(),pid,Project.Status.FULL.getValue());
        long totalInvestAmount = result.get("totalInvestAmount") == null ? 0l : Long.parseLong(result.get("totalInvestAmount").toString());
        Double amount = result.get("amount") == null ? 0d : Double.parseDouble(result.get("amount").toString());
        return amount.longValue() == Money.centToYuan(totalInvestAmount).getAmount().longValue();
    }

    @Override
    public PaginationResult<Map<String, Object>> queryForPageMap(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        PaginationResult<Map<String, Object>> result = baseDao.queryForPage(SqlContainer.PRO_LIST, conditions, pageInfo, order);
        return result;
    }

    @Override
    public PaginationResult<Map<String, Object>> queryInvestAcc(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        PaginationResult<Map<String, Object>> result = baseDao.queryForPage(SqlContainer.PRO_INVEST, conditions, pageInfo, order);
        return result;
    }

    @Override
    public Map<String, Object> queryProInfo(int id) throws Exception {
        return baseDao.queryForMap(SqlContainer.PRO_INFO, id);
    }
}
