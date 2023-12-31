package com.easybbs.aspect;

import com.easybbs.annotation.GlobalInterceptor;
import com.easybbs.annotation.VerifyParam;
import com.easybbs.cconst.Constants;
import com.easybbs.cconst.EHttpCode;
import com.easybbs.exception.BusinessException;
import com.easybbs.utils.StringTools;
import com.easybbs.utils.VerifyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


@Aspect
@Component
public class OperactionAspect {

    private static final Logger logger = LoggerFactory.getLogger(OperactionAspect.class);

    private static final String[] TYPE_BASE = {"java.lang.String", "java.lang.INTEGER", "java.lang.Long"};

    @Pointcut("@annotation(com.easybbs.annotation.GlobalInterceptor)")
    private void requestInterceptor() {}

    @Around("requestInterceptor()")
    public Object interceptorDo(ProceedingJoinPoint point) {
        try {
            Object target = point.getTarget();
            Object[] arguments = point.getArgs();
            String methodName = point.getSignature().getName();
            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);

            if (null == interceptor) {
                return null;
            }
            // 校验登录
            if (interceptor.checkLogin()) {
                checkLogin();
            }

            // 校验参数
            if (interceptor.checkParams()) {
                validateParams(method, arguments);
            }
            Object pointResult = point.proceed();

            return pointResult;
        } catch (BusinessException e) {
            logger.error("全局拦截器异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("全局拦截器异常", e);
            throw new BusinessException("服务器返回错误，请联系管理员");
        } catch (Throwable e) {
            logger.error("全局拦截器异常", e);
            throw new BusinessException("服务器返回错误，请联系管理员");
        }
    }

    private void checkLogin() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(Constants.SESSION_KEY);
        if (null == obj) {
            throw new BusinessException(EHttpCode.CODE_901);
        }
    }

    private void validateParams(Method method, Object[] arguments) {
        for (Object argument : arguments) {
            validateObject(argument);
        }
    }

    private void validateObject(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(VerifyParam.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    VerifyParam verifyParam = field.getAnnotation(VerifyParam.class);
                    checkValue(value, verifyParam);
                } catch (IllegalAccessException e) {
                    logger.error("校验对象属性异常", e);
                }
            }
        }
    }


    private void checkObjValue(Parameter parameter, Object value) {


    }

    private void checkValue(Object value, VerifyParam verifyParam) {
        Boolean isEmpty = value == null || StringTools.isEmpty(value.toString());
        Integer length = value == null ? 0 : value.toString().length();

        /**
         * 校验空
         */
        if (isEmpty && verifyParam.required()) {
            throw new BusinessException("请求参数错误");
        }

        /**
         * 校验长度
         */
        if (!isEmpty && verifyParam.max() != -1 && verifyParam.max() < length || verifyParam.min() != -1 && verifyParam.min() > length) {
            throw new BusinessException("请求参数错误");
        }

        /**
         * 校验正则
         */
        if (!isEmpty && !StringTools.isEmpty(verifyParam.regex().getRegex()) && !VerifyUtils.verify(verifyParam.regex(),
                String.valueOf(value))) {
            throw new BusinessException("请求参数错误");
        }
    }
}
