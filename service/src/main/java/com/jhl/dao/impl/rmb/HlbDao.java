package com.jhl.dao.impl.rmb;

import com.jhl.db.BaseDao;
import com.jhl.pojo.biz.Account;
import com.jhl.pojo.biz.HlbChangeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/26.
 */
@Repository("hlbDao")
public class HlbDao extends BaseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addChangeDao(Account account,double hlb,String desc) throws Exception{
        HlbChangeRecord hlbChangeRecord = new HlbChangeRecord();
        hlbChangeRecord.setDeleteState(1);
        hlbChangeRecord.setAccId(account.getId());
        hlbChangeRecord.setAmount(account.getPoint() + hlb);
        hlbChangeRecord.setChangeAmount(hlb);
        hlbChangeRecord.setDesc(desc);
        hlbChangeRecord.setCreateTime(new Date());
        add(hlbChangeRecord);
    }

}
