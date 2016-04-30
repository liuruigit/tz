package com.jhl.dao;


import com.jhl.db.BaseDao;
import com.jhl.db.JDBCBaseDao;
import com.jhl.pojo.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author v5inter
 * date Jun 4, 2014
 */
@Repository("configDao")
public class ConfigDao  extends BaseDao {

	@Autowired
	private JDBCBaseDao baseDao;
	
	private static final String TABLE = "`t_config`";

	private static final String QUERY_CONFIG = "SELECT * FROM " + TABLE + " where delete_state = 1 ";

	private static final String SQL_DELETE_BY_SITE = "UPDATE " + TABLE + " SET delete_state = -1 WHERE site_id = ?";

	public List<Config> queryAllConfig() throws Exception {
		StringBuilder sql = new StringBuilder(QUERY_CONFIG);
		sql.append(" order by `key`");
		return baseDao.query(sql.toString(), new Object[]{}, Config.class);
	}

	public int delConfigBySite(int siteId) throws Exception {
		return baseDao.update(SQL_DELETE_BY_SITE, siteId);
	}

}
