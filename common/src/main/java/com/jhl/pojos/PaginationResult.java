package com.jhl.pojos;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

public final class PaginationResult<E> {

	private int total = 0;
	private List<E> resultList;
	private int pageSize;

	/**
	 * Constructor
	 *
	 * @param total
	 * @param result
     * 默认每页为20
	 */
	public PaginationResult(int total, List<E> result) {
		super();
		this.total = total;
		this.resultList = result;
		this.pageSize = 20;
	}

	public PaginationResult(int total, List<E> resultList, int pageSize) {
		this.total = total;
		this.resultList = resultList;
		this.pageSize = pageSize;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

    public void setTotal(int total) {
        this.total = total;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    /**

	 * @return the result
	 */
	public List<E> getResultList() {
		return resultList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
