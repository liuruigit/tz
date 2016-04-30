package com.jhl.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: vic
 * Date: 13-5-18
 * Time: 下午4:44
 * 选择数据源注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChooseDataSource {

    /**
     * 数据库操作类型
     */
    String mainType() default "";
    /**
     * 业务类型
     */
    String bizType() default "";

    /**
     * 使用mainType_bizType逻辑完之后应该返回的数据库
     * 如果为null or empty，则使用mainType_bizType
     * @return
     */
    String respDB() default "";
}
