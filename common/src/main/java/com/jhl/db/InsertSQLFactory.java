/**
 * Copyright  2013-7-17 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:32:17
 * 版本号： v1.0
*/

package com.jhl.db;


import com.jhl.annotation.Transient;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <li>类描述：插入数据的SQL构建工厂</li>
 * <li>创建者： amos.zhou</li>
 * <li>项目名称： 7road-common</li>
 * <li>创建时间： 2013-7-17 上午10:32:17</li>
 * <li>版本号： v1.0 </li>
 */
public class InsertSQLFactory{

	
	private InsertSQLFactory(){}

	private static volatile InsertSQLFactory instance  = new InsertSQLFactory();
	
	public static InsertSQLFactory getInstance(){
		return instance;
	}
	

	
	public SQLEntry getSQLEntry(Object obj) {
		Class classz =  obj.getClass();
		StringBuilder buf = new StringBuilder(" insert into ").append(SQLUtils.getTableName(classz)).append(" ( ");
		StringBuilder valBuf = new StringBuilder("  values ( ");
		Field[] fieldArray = classz.getDeclaredFields();
		List<Object> list = new ArrayList<Object>();
		int notNullValueCounter = 0;
		for (int i = 0; i < fieldArray.length; i++) {
			Field f = fieldArray[i];
			Transient trasnAnno = f.getAnnotation(Transient.class);
			f.setAccessible(true);
			Object value = ReflectionUtils.getField(f, obj);
			//Not transient field  and the value be setted
			if (null == trasnAnno && null != value) {
				if (notNullValueCounter !=  0) {
					buf.append(" , ");
					valBuf.append(" , ");
				}
				buf.append("`"+SQLUtils.convertStrToDBFormat(f.getName())+"`");
				valBuf.append(" ? ");
				list.add(value);
				notNullValueCounter++;
			}
		}
		buf.append(" ) ").append(valBuf).append(" ) ");
		if (notNullValueCounter == 0) {
			throw new InvalidDataAccessApiUsageException(
					"can not insert the object util properties been setted.");
		}
		return new SQLEntry(buf.toString(), list.toArray());
	}

    public <T> String getInsertSQL(Class<T> clazz) {
        StringBuilder buf = new StringBuilder(" insert into ").append(SQLUtils.getTableName(clazz)).append(" ( ");
        StringBuilder valBuf = new StringBuilder("  values ( ");
        Field[] fieldArray = clazz.getDeclaredFields();
        List<Object> list = new ArrayList<Object>();
        int notNullValueCounter = 0;
        for (int i = 0; i < fieldArray.length; i++) {
            Field f = fieldArray[i];
            Transient trasnAnno = f.getAnnotation(Transient.class);
            f.setAccessible(true);
            //Not transient field  and the value be setted
            if (null == trasnAnno) {
                if (notNullValueCounter !=  0) {
                    buf.append(" , ");
                    valBuf.append(" , ");
                }
                buf.append(SQLUtils.convertStrToDBFormat(f.getName()));
                valBuf.append(" ? ");
                notNullValueCounter++;
            }
        }
        buf.append(" ) ").append(valBuf).append(" ) ");
        if (notNullValueCounter == 0) {
            throw new InvalidDataAccessApiUsageException(
                    "can not insert the object util properties been setted.");
        }
        return buf.toString();
    }

}
