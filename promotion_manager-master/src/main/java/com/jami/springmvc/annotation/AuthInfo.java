package com.jami.springmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by felixzhao on 14-5-16.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthInfo {

    boolean needLogin() default false;
}
