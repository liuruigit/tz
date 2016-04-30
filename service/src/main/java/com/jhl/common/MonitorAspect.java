package com.jhl.common;

import com.jhl.util.SeqNoUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amoszhou on 16/1/28.
 */
@Aspect
public class MonitorAspect {

    private static final String WHERE_BEGIN = "BEGIN";
    private static final String WHERE_END = "END";

    @Pointcut("execution(public * *(..)) && (@target(org.springframework.stereotype.Repository) ||  @target(org.springframework.stereotype.Service)) ")
    public void methodsNeedLog() {

    }

    @Around("methodsNeedLog()")
    public Object logParamterAndResult(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        String no = SeqNoUtil.nextSeqNo();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        Object[] args = joinPoint.getArgs();
        logger.info(buildClassTag(no) +"方法签名:{},参数:{}", joinPoint.getSignature().toLongString(), buildArgString(args));
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.info(buildClassTag(no) + "方法执行完毕返回值为:{}，执行时间:{}毫秒", result,end - begin);
            return result;
        } catch (Throwable throwable) {
            logger.error(buildClassTag(no) + "方法执行过程中发生错误:{}" + buildClassTag(no), throwable);
            throw throwable;
        }
    }

    private String buildClassTag(String no) {
        return "****  "+no+"  *****";
    }

    private String buildArgString(Object[] args) {
        StringBuilder sb = new StringBuilder("[");
        if (args != null && args.length > 0) {
            for (Object o : args) {
                sb.append(o).append(",");
            }
        } else {
            sb.append("没有参数");
        }
        //最后多一个,无所谓
        sb.append("]");
        return sb.toString();
    }


}
