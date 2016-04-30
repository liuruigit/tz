package com.jhl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: vic wu
 * Date: 14-3-6
 * Time: 下午12:11
 * 权限的注解类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeQuery {

    String type();

    String formatStr();

}
