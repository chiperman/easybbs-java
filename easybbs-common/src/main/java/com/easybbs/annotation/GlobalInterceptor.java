package com.easybbs.annotation;

import java.lang.annotation.*;

/**
 * 全局拦截器，用于 方法、类型 上
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GlobalInterceptor {
    /**
     * 是否需要登录
     */
    boolean checkLogin() default false;

    /**
     * 是否需要校验参数
     */
    boolean checkParams() default false;

    /**
     * 校验频次
     */

}
