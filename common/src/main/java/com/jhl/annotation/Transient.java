package com.jhl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * User: amos.zhou
 * Date: 13-7-17
 * Time: 下午2:54
 * 标识注解，标识属性是否需要与数据库进行映射
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {

}
