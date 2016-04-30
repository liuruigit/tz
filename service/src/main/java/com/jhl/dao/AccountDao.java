package com.jhl.dao;

import com.jhl.db.BaseDao;
import com.jhl.dto.UserBalanceDTO;
import com.jhl.pojo.biz.Account;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vic wu on 2015/8/14.
 */
@Repository(value = "accountDao")
public class AccountDao extends BaseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Account loginQuery(String nameOrMobile) throws Exception {
        Account user = null;
        try {
            String sql = "select * from t_account where acc_name = ?";
            user = jdbcTemplate.queryForObject(sql, new Object[]{nameOrMobile},
                    BeanPropertyRowMapper.newInstance(Account.class));
        } catch (EmptyResultDataAccessException exceptione) {
            //查询的用户不存在
        }
        return user;
    }

    public Account queryAccountByName(String name) throws Exception {
        String sql = "select * from t_account t where t.acc_name = ? and delete_state = 1";
        List<Account> users = baseDao.query(sql, new Object[]{name}, Account.class);

        return users.size() == 1 ? users.get(0) : null;
    }

    /**
     * 更新用户帐户余额信息
     *
     * @return
     */
    public int updateBalance(UserBalanceDTO balanceDTO) {
        String sql = "update t_account set update_time = Now(),money = ?, frozen_Money = ? ,invest_money = ? ,acc_income = ?," +
                " digest = ? , version = version +1 where id =?  and version = ?";
        return baseDao.update(sql, balanceDTO.getBalance(), balanceDTO.getFrozenMoney(), balanceDTO.getInvestMoney(),balanceDTO.getAccIncome(),
                balanceDTO.getDigest(), balanceDTO.getUserId(), balanceDTO.getVersion());
    }

    /**
     * 更新用户帐户葫芦币信息
     *
     * @return
     */
    public int updateHlb(int userId,double hlb) {
        String sql = "update t_account set POINT = POINT + ? where id = ?";
        return baseDao.update(sql, hlb,userId);
    }

    public Account queryAccountForLock(int userId) {
        String sql = " select * from t_account where id= ? and delete_state = 1 for update";
        List<Account> accounts = baseDao.query(sql, new Object[]{userId}, Account.class);
        if (accounts != null)
            return accounts.get(0);
        return null;
    }

    public boolean isRepeat(String mobile) throws Exception {
        String check_sql = "select count('x') from t_account t  where t.acc_name = ? and delete_state = 1";
        int result = baseDao.queryForInt(check_sql, mobile);
        return result == 0;
    }

    public Account isExistsCertNo(String idNo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("certNo", idNo);
        map.put("deleteState", 1);
        return baseDao.queryForObject(Account.class, map);
    }

    public Account queryUserByPartId(String partId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parterUserId1", partId);
        return baseDao.queryForObject(Account.class, map);
    }

    public List<Account> queryAccountByPage( int row ){
        int account_state = 0 ;//状态：0正常1临时封号2永久封号
        String sql = "SELECT * FROM T_ACCOUNT WHERE STATUS = ? AND DELETE_STATE=1 LIMIT ?,100";
        return jdbcTemplate.query(sql , new Object[]{ account_state , row } , BeanPropertyRowMapper.newInstance(Account.class));
    }

    public PaginationResult<Map<String, Object>> queryAccCoupon(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        String sql = "select a.`id`,a.`status`,c.`NAME`,c.`DESC`,a.VALID_DATE," +
                "a.AMOUNT from t_acc_coupon a left join t_coupon c on a.COUPON_ID = c.ID";
        return baseDao.queryForPage(sql, conditions, pageInfo, order);
    }

    public PaginationResult<Map<String, Object>> queryMsg(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception {
        String sql = "select id,title,content,create_time,is_read from t_message";
        return baseDao.queryForPage(sql, conditions, pageInfo, order);
    }

    public void updatePrepareStep(int id,int value) throws Exception {
        String sql = "update t_account set PREPARE_STEP = ? where id =?";
        baseDao.update(sql,value,id);
    }

    public void setMsgToRead(int id) throws Exception {
        String sql = "update t_message set IS_READ = 1 where id =?";
        baseDao.update(sql, id);
    }

    public void setOpenIdToAccount(Account account) throws Exception {
        String sql = "update T_ACCOUNT set OPEN_ID = ? where id =?";
        baseDao.update(sql, account.getOpenId(), account.getId());
    }

}
