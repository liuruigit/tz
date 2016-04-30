package com.jhl.dao;

import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.BankCard;
import com.jhl.pojo.biz.BankRule;
import com.jhl.util.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 15/9/26.
 */
@Repository(value = "bankDao")
public class BankDao extends BaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void unbinding(BankCard bankCard) throws Exception {
        String sql = "update t_bank_card set is_default = ?,delete_state = ? where id = ?";
        baseDao.update(sql, bankCard.getIsDefault(),bankCard.getDeleteState(),bankCard.getId());
    }

    public BankCard queryUserByName(String name,int userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bankCardNo", name);
        map.put("userId", userId);
        return baseDao.queryForObject(BankCard.class, map);
    }

    public BankCard queryUserByName(String name) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bankCardNo", name);
        map.put("deleteState", 1);
        return baseDao.queryForObject(BankCard.class, map);
    }

    public BankCard queryDefaultBank(int userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("isDefault", 0);
        map.put("deleteState", 1);
        return baseDao.queryForObject(BankCard.class, map);
    }

    public BankRule queryBankRuleByBankName(String name) throws Exception {
        BankRule bankRule = null;
        try {
            String sql = "SELECT * FROM t_bank_rule where BANK_NAME = ?";
            bankRule = jdbcTemplate.queryForObject(sql, new Object[]{name},
                    BeanPropertyRowMapper.newInstance(BankRule.class));
        } catch (EmptyResultDataAccessException exceptione) {
            //查询的用户不存在
        }
        return bankRule;
    }

    public Map<String, Object> queryDefaultBankMap(int id) throws Exception {
        String sql = "SELECT b.id,b.bank_card_no,b.bank_name,b.is_default,b.mobile from t_bank_card b " +
                "where user_id = ? and is_default = 0 and delete_state = 1";
        try {

            Map<String, Object> result = baseDao.queryForMap(sql, id);
            if (result!=null && result.size() > 0 ) {
                result.put("bank_card_no", DESUtil.getDesString((String) result.get("bank_card_no")));
                result.put("mobile", DESUtil.getDesString((String) result.get("mobile")));
            }
            return result;
        }catch (EmptyResultDataAccessException exception){
            return new HashMap<String, Object>();
        }
    }

    public List<Map<String, Object>> initBankRule() throws Exception {
        //String sql = "select BANK_NAME,BANK_SHORT_NAME,`LIMIT`,DAY_LIMIT from t_bank_rule";
        try {
            //return baseDao.queryForJsonMap(sql);
            return null;
        }catch (EmptyResultDataAccessException exception){
            return new ArrayList<Map<String, Object>>();
        }
    }
}
