package com.jhl.dao;

import com.jhl.db.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by amoszhou on 16/1/31.
 */
@Repository("accountChangeDao")
public class AccountChangeDao extends BaseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

}
