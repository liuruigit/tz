package com.jhl.dao;

import com.jhl.db.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by xin.fang on 2015/8/31.
 */
@Repository("financingDao")
public class FinancingDao extends BaseDao {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private static final String QUERY_LIST = "SELECT COLUMN_INFO FROM T_FINANCING f,  " +
//            "(SELECT `key`,`value` FROM t_config WHERE delete_state = 1 AND TYPE = 1) AS creditLevel, " +
//            "(SELECT `key`,`value` FROM t_config WHERE delete_state = 1 AND TYPE = 2) AS fintype, " +
//            "(SELECT `key`,`value` FROM t_config WHERE delete_state = 1 AND TYPE = 3) AS finway " +
//            "WHERE f.delete_state = 1 AND f.`credit_level` = creditLevel.key AND f.`type` = fintype.key AND f.`pay_interest_way` = finway.key ";
//
//    private static final  String UPDATE_STATUS = "update T_FINANCING set status = ? where id = ? ";
//
//    public PaginationResult<Financing> query(Financing financing, PageInfo pageInfo, int startDay, int endDay) throws Exception {
//        StringBuilder sql = new StringBuilder(QUERY_LIST);
//        List<Object> values = new ArrayList<Object>();
//        if (financing.getType() != 0) {
//            sql.append(" and type = ? ");
//            values.add(financing.getType());
//        }
//
//        if (StringUtils.isNotBlank(financing.getName())) {
//            sql.append(" and name like  '%").append(financing.getName()).append("%' ");
//        }
//
//        if (startDay != 0) {
//            sql.append(" and time >= ? ");
//            values.add(startDay);
//        }
//
//        if (endDay != 0 ) {
//            sql.append(" and time <= ? ");
//            values.add(endDay);
//        }
//
//
//        int count = baseDao.queryRecord(sql.toString().replace("COLUMN_INFO", " count(1) "), values.toArray());
//        sql.append(" order by  create_time desc ");
//        sql.append(" limit ").append(pageInfo.getStartRow()).append(", ").append(pageInfo.getPageSize());
//        List<Financing> list = baseDao.query(sql.toString().replace("COLUMN_INFO", " f.*, creditLevel.value as creditLevelName, fintype.value as typeName, finway.value as payInterestWayName "), values.toArray(), Financing.class);
//        return new PaginationResult<Financing>(count, list);
//    }
//
//    public int updateStatus(int id, int status) throws Exception {
//        return baseDao.update(UPDATE_STATUS, new Object[]{status, id});
//    }
//
//    public Financing queryHot() throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("specialType", 0);
//        map.put("isHomepage", 1);
//        return baseDao.queryForObject(Financing.class, map);
//    }
//
//    public Financing queryNew() throws Exception {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("specialType", 1);
//        map.put("isHomepage", 1);
//        return baseDao.queryForObject(Financing.class, map);
//    }
//
//    public Financing queryFanById(int id) throws Exception{
//        String check_sql = "select id_no,id,selled_amount,status," +
//                "amount,time,interest_rate,bank_card_id from t_financing t where id = ? for update";
//        return jdbcTemplate.queryForObject(check_sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Financing.class));
//    }
//
//    public boolean updateMoneyAndStatus(int id,double selledAmount,int status) throws Exception {
//        String check_sql = "update t_financing t set t.selled_amount = ?,t.status = ? where id = ?";
//        int result = baseDao.update(check_sql,selledAmount,status,id);
//        return result == 1;
//    }
}
