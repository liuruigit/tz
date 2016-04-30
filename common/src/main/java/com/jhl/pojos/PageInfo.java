package com.jhl.pojos;


public class PageInfo {

	private int startRow = 1;

	private int pageSize = 99999;

	private int totalRowCount = -1;

	private String offset;

	private String order;

	public PageInfo() {

	}

	public PageInfo(int startRow, int pageSize) {
		this.startRow = startRow;
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageInfo [getStartRow()=").append(getStartRow()).append(", getPageSize()=")
				.append(getPageSize()).append(", getTotalRowCount()=").append(getTotalRowCount()).append(", ");
		if (getOrder() != null)
			builder.append("getOrder()=").append(getOrder()).append(", ");
		if (getOffset() != null)
			builder.append("getOffset()=").append(getOffset());
		builder.append("]");
		return builder.toString();
	}




}
