package com.jhl.common;

import com.jhl.util.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amoszhou on 16/1/29.
 */
@Aspect
public class ControllerMonitor {

    @Pointcut("execution(public * *(..)) && (@target(org.springframework.stereotype.Controller))")
//    @Override
    public void methodsNeedLog() {
    }

    @Around("methodsNeedLog()")
    public Object logParamterAndResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        Object[] args = joinPoint.getArgs();
        logger.info("方法签名:{},参数:{}", joinPoint.getSignature().toLongString(), buildArgString(args));
        try {
            Object result = joinPoint.proceed();
            logger.info("方法执行完毕返回值为:{}", result);
            return result;
        } catch (Throwable throwable) {
            logger.error(SessionUtil.getNo() + "方法执行过程中发生错误", throwable);
            throw throwable;
        }
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
