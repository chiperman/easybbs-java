package com.easybbs.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class ApiLogAspect {
    private static final Logger log = LoggerFactory.getLogger(ApiLogAspect.class);

    @Before("execution(public * com.easybbs.controller.*.*(..))")
    public void before(JoinPoint point) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String ip = Objects.isNull(request.getHeader("X-Real-IP")) ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
        log.info("startTime:{}, url:{}, method:{}, ip:{}, args:[{}]", sdf.format(System.currentTimeMillis()),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ip,
                Arrays.toString(point.getArgs()));
    }

    @AfterReturning(returning = "result", pointcut = "execution(public * com.easybbs.controller.*.*(..))")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("endTime:{}, result:{}",sdf.format(System.currentTimeMillis()), result.toString());
    }

}
