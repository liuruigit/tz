package com.jhl.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * db_ms数据库
 */
@Repository("daoSupport")
public class DaoSupport extends BaseDaoSupport implements DAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	protected SqlSessionTemplate chooseDatasource() {
		return sqlSessionTemplate;
	}
	
}


