package com.jhl.pojos;


import com.jhl.db.SQLOperator;
import org.apache.commons.lang.StringUtils;

/**
 * 用户SQL查询条件的实体类
 */
public class SQLCondition {

	private String key;

	private Object value;

    private SQLOperator operator;//add by vic ,used to control the assembly of 'where' condition

    private String connector = "and";

	private boolean isLike;

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public SQLCondition() {
	}

	public SQLCondition(String key, Object value, boolean isLike) {
		if (StringUtils.isBlank(key)) {
			this.key = "";
		} else {
			this.key = key;
		}

		if (null == value) {
			this.value = "";
		} else {
			this.value = value;
		}
		this.isLike = isLike;
	}

	public SQLCondition(String key, Object value) {
		if (StringUtils.isBlank(key)) {
			this.key = "";
		} else {
			this.key = key;
		}

		if (null == value) {
			this.value = "";
		} else {
			this.value = value;
		}
	}

    public SQLCondition(String key, SQLOperator operator, Object value) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

    public SQLOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLOperator operator) {
        this.operator = operator;
    }
}
