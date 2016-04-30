package com.jhl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: xin.fang
 * Date: 14-3-13
 * Time: 下午5:05
 * 用于标识禁止更新的属性
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForbidUpdate {
}
