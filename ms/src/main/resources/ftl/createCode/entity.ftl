package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
*
* 类名称：${objectName}.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：${nowDate?string("yyyy-MM-dd")}
* @version 1.0
*/
public class ${objectName} {
	@PrimaryKey
	private int id;
<#list fieldList as var>
	/** ${var[2]} */
	private ${var[5]} ${var[0]};
</#list>
<#list fieldList as var>

	public ${var[5]} get${var[6]}() {
		return ${var[0]};
	}

	public void set${var[6]}(${var[5]} ${var[0]}) {
		this.${var[0]} = ${var[0]};
	}
</#list>
}










