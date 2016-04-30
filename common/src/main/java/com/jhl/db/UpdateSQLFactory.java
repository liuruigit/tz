package com.jhl.db;


import com.jhl.pojos.SQLCondition;
import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.Transient;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * User: xin.fang
 * Date: 14-3-13
 * Time: 上午11:57
 * 生成更新语句的工厂
 */
public class UpdateSQLFactory {


    private static volatile UpdateSQLFactory instance  = new UpdateSQLFactory();

    public static UpdateSQLFactory getInstance(){
        return instance;
    }

    /**
     * 获取更新的SQLEntry(以主键id为条件)
     *
     * @param obj 待更新的对象
     * @return
     */
    public static SQLEntry getSQLEntry(Object obj) {
        return getSQLEntry(obj, null, null);
    }

    /**
     * 获取更新的SQLEntry
     *
     * @param obj                 待更新的对象
     * @param updateCondationName 更新条件
     * @return
     */
    public static SQLEntry getSQLEntryByCondation(Object obj, String[] updateCondationName) {
        return getSQLEntry(obj, null, updateCondationName);
    }

    /**
     * 获取更新的SQLEntry(以主键id为条件)
     *
     * @param obj             更新对象
     * @param updateColumName 待更新的列
     * @return
     */
    public static SQLEntry getSQLEntry(Object obj, String[] updateColumName) {
        return getSQLEntry(obj, updateColumName, null);
    }

    /**
     * 获取更新的实体
     *
     * @param obj                 待更新的对象
     * @param updateColumName     需要更新的列(若为null或长度为0则更新除主键外的所有列)
     * @param updateCondationName 更新条件（若为null或长度为0则以主键为条件）
     * @return
     */
    public static SQLEntry getSQLEntry(Object obj, String[] updateColumName, String[] updateCondationName) {
        Class classz = obj.getClass();
        Field[] fieldArray = classz.getDeclaredFields();
        //获取主键列名
        String primaryKeyName = SQLUtils.getPrimaryKeyName(classz);
        Object primaryKeyValue = null;
        //将对象的每一个需要持久化的字段和值存入map中
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (int i = 0; i < fieldArray.length; i++) {
            Field f = fieldArray[i];
            String fieldName = f.getName();
            Transient trasnAnno = f.getAnnotation(Transient.class);
            ForbidUpdate forbidUpdate = f.getAnnotation(ForbidUpdate.class);
            f.setAccessible(true);
            Object value = ReflectionUtils.getField(f, obj);
            //Not transient field
            if (null == trasnAnno && null == forbidUpdate) {
                //排除主键
                if (fieldName.equalsIgnoreCase(primaryKeyName)) {
                    primaryKeyValue = value;
                } else {
                    map.put(fieldName, value);
                }
            }
        }

        if (map.size() == 0) {
            throw new InvalidDataAccessApiUsageException(
                    "can not insert the object util properties been setted.");
        }

        StringBuilder buf = new StringBuilder(" UPDATE ").append(SQLUtils.getTableName(classz)).append(" SET ");
        StringBuilder condationBuf = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        int notNullValueCounter = 0;

        Set<Map.Entry<String, Object>> entrySet = map.entrySet();

        //构造需要更新的列
        if (null == updateColumName) {
            for (Map.Entry<String, Object> entry : entrySet) {
                if (notNullValueCounter != 0) {
                    buf.append(" , ");
                }
                String columnName = entry.getKey();
                buf.append(SQLUtils.convertStrToDBFormat(columnName)).append(" = ? ");
                list.add(entry.getValue());
                notNullValueCounter++;
            }
        } else {
            for (Map.Entry<String, Object> entry : entrySet) {
                if (notNullValueCounter != 0) {
                    buf.append(" , ");
                }
                String columnName = entry.getKey();
                for (String str : updateColumName) {
                    if (str.equals(columnName)) {
                        buf.append(SQLUtils.convertStrToDBFormat(columnName)).append(" = ? ");
                        list.add(entry.getValue());
                        notNullValueCounter++;
                    }
                }
            }
        }

        //构造更新条件
        condationBuf.append(" WHERE  ");
        if (null !=  updateCondationName&& updateCondationName.length != 0) {
            int i = 0;
            for (String str : updateCondationName) {
                if (i != 0) {
                    condationBuf.append(" and ");
                }
                if(str.equalsIgnoreCase(primaryKeyName)){
                    condationBuf.append(SQLUtils.convertStrToDBFormat(primaryKeyName)).append(" = ? ");
                    list.add(primaryKeyValue);
                }
                condationBuf.append(SQLUtils.convertStrToDBFormat(str)).append(" = ? ");
                list.add(map.get(str));
                i++;
            }
        } else {
            condationBuf.append(SQLUtils.convertStrToDBFormat(primaryKeyName)).append(" = ? ");
            list.add(primaryKeyValue);
        }
        buf.append(condationBuf);
        return new SQLEntry(buf.toString(), list.toArray());
    }

    /**
     * SQL拼装方法，目前仅支持 in 和 equal 两种条件表达式
     * @param classz
     * @param updateCols
     * @param whereConditions
     * @author vic
     * @return
     */
    public static SQLEntry getSQLEntryByCondition(Class classz,SQLCondition[] updateCols,SQLCondition[] whereConditions) {
        StringBuilder buf = new StringBuilder(" UPDATE ").append(SQLUtils.getTableName(classz)).append(" SET ");
        List<Object> values = new ArrayList<Object>();
        //col组装
        for(SQLCondition col : updateCols) {
            Assert.notNull(col.getKey());
            buf.append(SQLUtils.convertStrToDBFormat(col.getKey())).append(" = ? ");
            values.add(col.getValue());//添加参数
        }

        buf.append(" WHERE ");

        //where组装
        for(int j = 0;j < whereConditions.length;j++) {
            SQLCondition condition = whereConditions[j];
            Assert.notNull(condition.getKey());
            buf.append(SQLUtils.convertStrToDBFormat(condition.getKey()));
            if (SQLOperator.in == condition.getOperator()) {
                Object[] objs = (Object[])condition.getValue();
                buf.append(" IN (");
                for(int i = 0;i < objs.length;i++) {
                    buf.append("?");
                    if(i != objs.length -1 ) {
                        buf.append(",");
                    }
                    values.add(objs[i]);//继续添加参数
                }
                buf.append(") ");
            }else if(SQLOperator.equal == condition.getOperator()){
                buf.append(SQLUtils.convertStrToDBFormat(condition.getKey())).append(" = ? ");
                values.add(condition.getValue());//还是添加参数
            }else{
                //todo 其他操作符
            }

            if(j != whereConditions.length-1) {
                buf.append(" AND ");
            }
        }

        return new SQLEntry(buf.toString(), values.toArray());
    }


}
