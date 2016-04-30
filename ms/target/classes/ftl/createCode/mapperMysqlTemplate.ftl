<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	
	<!-- 新增-->
	<insert id="save" parameterType="pd">
		insert into ${tabletop}${objectNameUpper}(
	<#list fieldList as var>
			`${var[7]}`,
	</#list>
		) values (
	<#list fieldList as var>
			${r"#{"}${var[7]}${r"}"},
	</#list>
		)
	</insert>
	
	
	<!-- 删除-->
	<update id="delete" parameterType="pd">
        update ${tabletop}${objectNameUpper} SET `DELETE_STATE` = 0
        where
        ID = ${r"#{"}ID${r"}"}
	</update>
	
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update  ${tabletop}${objectNameUpper}
			set 
	<#list fieldList as var>
		<#if var[3] == "是">
			`${var[7]}` = ${r"#{"}${var[7]}${r"}"},
		</#if>
	</#list>
        	ID = ${r"#{"}ID${r"}"}
			where
				ID = ${r"#{"}ID${r"}"}
	</update>
	
	
	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
	<#list fieldList as var>
			`${var[7]}`,
	</#list>
			ID
		from 
			${tabletop}${objectNameUpper}
		where 
			ID = ${r"#{"}ID${r"}"}
	</select>
	
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
		<#list fieldList as var>
				a.${var[7]},
		</#list>
				a.ID
		from 
				${tabletop}${objectNameUpper} a
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
		<#list fieldList as var>
				a.${var[7]},
		</#list>
				a.ID
		from 
				${tabletop}${objectNameUpper} a
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from ${tabletop}${objectNameUpper}
		where 
			ID in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>