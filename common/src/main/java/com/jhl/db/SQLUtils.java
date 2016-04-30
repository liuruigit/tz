package com.jhl.db;


import com.google.common.base.Strings;
import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.RangeQuery;
import com.jhl.annotation.Transient;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * <li>类描述：数据库操作的工具类</li>
 * <li>创建者： amos.zhou</li>
 * <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-17 下午2:57:34</li>
 * <li>版本号： v1.0 </li>
 */
public final class SQLUtils {
	private SQLUtils(){}
	
	
	
	/**默认表前缀以t_开头*/
	public static String tablePrefix ="T_" ;
	/**默认的数据库主键 ID*/
	private static final String DEFAULT_PRIMARY_KEY_NAME = "ID";
	
	public static final String DEFAULT_ALL_COLUMNS = " *  ";
	
	
//	/**
//	 * 
//	 * <p>创建时间： 2013-7-16 上午10:43:01</p>
//	 * <p>获取总记录数的SQL</p>
//	 * @param sql
//	 * @return
//	 */
//	public static String getCountSQL(String sql){
//		StringBuilder buf = new StringBuilder("select count(1) as total from ( ");
//		buf.append(sql).append("  ) aa ");
//		return buf.toString();
//	}
	
	
	
	/**
	 * 
	 * <p>创建时间： 2013-7-16 上午10:49:10</p>
	 * <p>转aaBbCc转为AA_BB_CC<p>
	 * @param str
	 * @return
	 */
	public static String convertStrToDBFormat(String str){
		return str.replaceAll("[a-z](?=[A-Z]+)","$0_");
	}
	
	
	/**
	 * 
	 * <p>创建时间： 2013-7-16 上午11:30:57</p>
	 * <p>获取表名</p>
	 * @param classz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getTableName(Class classz){
		return  StringUtils.trimToEmpty(tablePrefix) + convertStrToDBFormat(classz.getSimpleName());
	}

    /**
     * 获取主键
     * @param clazz
     * @return
     */
    public static String getPrimaryKeyName (Class clazz) {
        String primaryKeyName = null;
        Field[] fieldArray = clazz.getDeclaredFields();
        for (int i = 0; i < fieldArray.length; i++) {
            Field f = fieldArray[i];
            PrimaryKey primaryKey = f.getAnnotation(PrimaryKey.class);
            if(primaryKey != null){
                primaryKeyName = f.getName();
            }
        }
        if(StringUtils.isBlank(primaryKeyName)) {
            primaryKeyName = DEFAULT_PRIMARY_KEY_NAME;
        }
        return primaryKeyName;
    }

    /**
     * @author vic wu
     * 目前仅支持日期范围
     * @param fieldName
     * @param object
     * @param rangeQuery
     * @return
     * @throws IllegalAccessException
     */
    public static SQLCondition bulidRangeSQlCondition(String fieldName,Object object,RangeQuery rangeQuery) throws IllegalAccessException {
        Field fieldBegin = ReflectionUtils.findField(object.getClass(), fieldName+"Begin");
        Field fieldEnd = ReflectionUtils.findField(object.getClass(), fieldName+"End");
        SimpleDateFormat format = new SimpleDateFormat(rangeQuery.formatStr());
        SQLCondition condition = null;

        if(isNotBlank(fieldBegin,object) && !isNotBlank(fieldEnd,object)) {
            Object beginVal = fieldBegin.get(object);
            String formatBeginDate = format.format(beginVal);
            condition = new SQLCondition(SQLUtils.convertStrToDBFormat(fieldName), SQLOperator.greatEqualThan,formatBeginDate);
        }else if(!isNotBlank(fieldBegin,object) && isNotBlank(fieldEnd,object)) {
            Object endVal = fieldEnd.get(object);
            String formatEndDate = format.format(endVal);
            condition = new SQLCondition(SQLUtils.convertStrToDBFormat(fieldName), SQLOperator.lessEqualThan,formatEndDate);
        }else if(isNotBlank(fieldBegin,object) && isNotBlank(fieldEnd,object)) {
            Object beginVal = fieldBegin.get(object);
            String formatBeginDate = format.format(beginVal);
            Object endVal = fieldEnd.get(object);
            String formatEndDate = format.format(endVal);
            condition = new SQLCondition(SQLUtils.convertStrToDBFormat(fieldName), SQLOperator.between,new Object[]{formatBeginDate,formatEndDate});
        }
        return condition;
    }

    private static boolean isNotBlank(Field field,final Object object) throws IllegalAccessException {
        field.setAccessible(true);
        Object obj = field.get(object);
        boolean result = obj!=null && StringUtils.isNotBlank(obj.toString());
        return result;
    }

    public static List<SQLCondition> bulidSqlConditions(final Object object ){
        final List<SQLCondition> conditions = new ArrayList<SQLCondition>();
        final List<String> filterRange = new ArrayList<String>();
        ReflectionUtils.doWithFields(object.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                field.setAccessible(true);
                Transient trans = field.getAnnotation(Transient.class);
                if(trans != null)return;
                Object obj = field.get(object);
                if (obj == null || StringUtils.isBlank(obj.toString())) return;//属性值为空直接返回

                String fieldName = StringUtils.isNotBlank(field.getName())?field.getName().replaceAll("Begin|End",""):"";
                RangeQuery rangeQuery = field.getAnnotation(RangeQuery.class);
                SQLCondition condition = null;
                if (fieldCheck(filterRange,fieldName)){
                    return;
                }
                if(rangeQuery != null) {//判断是否范围查询
                    condition = SQLUtils.bulidRangeSQlCondition(fieldName, object, rangeQuery);
                    excludeField(filterRange,fieldName);
                }else if(obj instanceof Date) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String formarOpertateTime = format.format(obj);
                    condition = new SQLCondition(SQLUtils.convertStrToDBFormat(field.getName()), formarOpertateTime, true);
                }else{//基础数据类型
                    condition = new SQLCondition(SQLUtils.convertStrToDBFormat(field.getName()), obj, true);
                }
                conditions.add(condition);
            }
        });
        return conditions;
    }

    public static String buildCountSql(String sql){
        Assert.isTrue(!Strings.isNullOrEmpty(sql));
        Assert.isTrue(sql.contains("from"));
        String countTailSql = sql.substring(sql.indexOf("from"));
        return "select count('x') " + countTailSql;
    }

    public List<SQLCondition> initConditions(){
        List<SQLCondition> sqlConditions = new ArrayList<SQLCondition>();
        sqlConditions.add(new SQLCondition("delete_state", SQLOperator.equal, 1));
        return sqlConditions;
    }

    private static boolean fieldCheck(List<String> filterRange,String fieldName) {
        return filterRange.contains(fieldName);
    }

    private static void excludeField(List<String> filterRange,String fieldName) {
        filterRange.add(fieldName);
        filterRange.add(fieldName+"Begin");
        filterRange.add(fieldName+"End");
    }

    public static SQLCondition buildSqlConditionByDate(int dateQueryType,SQLOperator sqlOperator,String col) throws Exception{
        SQLCondition sqlCondition = null;
        switch (dateQueryType){
            case TODAY:
                return new SQLCondition(col,sqlOperator,new Object[]{DateUtil.getDayBegin(),DateUtil.getDayEnd()});
            case WEEK:
                return new SQLCondition(col,sqlOperator,new Object[]{new Date(
                        DateUtil.getDayStartTime(DateUtil.addDay(new Date(),-7).getTime())),DateUtil.getDayEnd()});
            case MONTH:
                return new SQLCondition(col,sqlOperator,new Object[]{new Date(
                        DateUtil.getDayStartTime(DateUtil.addMonth(new Date(),-1).getTime())),DateUtil.getDayEnd()});
            case MONTH_3:
                return new SQLCondition(col,sqlOperator,new Object[]{new Date(
                        DateUtil.getDayStartTime(DateUtil.addMonth(new Date(),-3).getTime())),DateUtil.getDayEnd()});
            case HALF_YEAR:
                return new SQLCondition(col,sqlOperator,new Object[]{new Date(
                        DateUtil.getDayStartTime(DateUtil.addMonth(new Date(),-6).getTime())),DateUtil.getDayEnd()});
            default:
                return sqlCondition;
        }
    }

    public static SQLCondition buildSqlConditionByDate(int dateQueryType) throws Exception{
        return buildSqlConditionByDate(dateQueryType,SQLOperator.between,"create_time");
    }

    public static String buildOrderStr(String orderName,String order)throws Exception {
        String base = "order by ";
        if (!Strings.isNullOrEmpty(orderName) &&
                ("desc".equalsIgnoreCase(order) || "asc".equalsIgnoreCase(order))) {
            base += orderName + " " +order +",create_time desc";
        }else{
            base += "c.create_time desc";
        }
        return base;
    }

    private final static int TODAY = 0,WEEK = 1,MONTH = 2,MONTH_3 = 3,HALF_YEAR = 4;

}
