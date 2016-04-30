package com.jhl.dao;


import com.jhl.db.BaseDao;
import com.jhl.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * 收益查询
 */
@Repository(value = "benifitDao")
public class BenifitDao extends BaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String QUERY_TOTAL_BENIFIT = "SELECT \n" +
            "  SUM((i.`money` / f.`amount`) * f.`amount` * f.`annual_income` * f.`time` / 360) AS totalMoney \n" +
            "  FROM\n" +
            "  t_invest_order i,\n" +
            "  t_financing f \n" +
            "  WHERE i.`financing_id` = f.id \n" +
            "  AND i.`user_id` = ? \n" +
            "  AND i.`status` = 2 ";

    private static final String QUERY_DETAIL_BENIFIT = "SELECT \n" +
            "  SUM(i.`money` * f.`annual_income`/360) AS money\n" +
            "  FROM\n" +
            "  t_invest_order i,\n" +
            "  t_financing f \n" +
            "  WHERE i.`financing_id` = f.id \n" +
            "  AND i.`user_id` = ? \n" +
            "  AND i.`status` = 2 \n" +
            "  AND f.`expire_time` >= ? \n" +
            "  AND f.`interest_time` <= ?";

    private static final String QUERY_MIN_INTEREST_TIME = "SELECT \n" +
            "  MIN(f.`interest_time`) AS minDate,MAX(f.`expire_time`) AS maxDate \n" +
            "  FROM\n" +
            "  t_invest_order i,\n" +
            "  t_financing f \n" +
            "  WHERE i.`financing_id` = f.id \n" +
            "  AND i.`user_id` = ? \n" +
            "  AND i.`status` = 2 \n";

    public Double queryTotalBenifit(int userId) throws Exception {
        Map<String, Object> map = jdbcTemplate.queryForMap(QUERY_TOTAL_BENIFIT, userId);
        return map.get("totalMoney") == null ? 0 : NumberUtil.format_double((Double) map.get("totalMoney"));
    }

    public Double queryBenifit(int userId,Date when) throws Exception {
        Map<String, Object> map = jdbcTemplate.queryForMap(QUERY_DETAIL_BENIFIT, userId, when, when);
        return map.get("money") == null ? 0 : NumberUtil.format_double((Double)map.get("money"));
    }

    public Map<String, Object> queryMinDate(int userId) throws Exception {
        Map<String, Object> map = jdbcTemplate.queryForMap(QUERY_MIN_INTEREST_TIME, userId);
        return map;
    }

}
