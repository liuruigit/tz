package com.jhl.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: vic
 * Date: 13-5-20
 * Time: 上午11:26
 * 系统级拦截点定义
 */
@Aspect
@Component
public class SystemPointCut {

    /**
     * 拦截所有业务Service
     */
    @Pointcut("execution( * com.sz.jhl.*.*.service.impl.*.*(..))")
    public void dbOperationPointcut(){}

}
