package com.jhl.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/**
 * db_web数据库
 */
@Repository("webDaoSupport")
public class WebDaoSupport extends BaseDaoSupport implements DAO {

	@Resource(name = "webSqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	protected SqlSessionTemplate chooseDatasource() {
		return sqlSessionTemplate;
	}
	
}


