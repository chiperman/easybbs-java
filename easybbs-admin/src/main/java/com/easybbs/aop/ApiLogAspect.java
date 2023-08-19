package com.easybbs.aop;

import com.easybbs.controller.UserController;
import com.mysql.cj.log.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Arrays;

@Aspect
@Component
public class ApiLogAspect {
    private static final Logger log = LoggerFactory.getLogger(ApiLogAspect.class);

    @Before("execution(public * com.easybbs.controller.*.*(..))")
    public void before(JoinPoint point) {
        log.info("startTime:{}, url:{}, method:{}, ip:{}, args:[{}]", System.currentTimeMillis(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr(),
                Arrays.toString(point.getArgs()));
    }

    @AfterReturning(returning = "result", pointcut = "execution(public * com.easybbs.controller.*.*(..))")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("endTime:{}, result:{}",System.currentTimeMillis(), result.toString());
    }

}
