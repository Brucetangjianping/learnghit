package com.jami.springmvc.annotation;

import com.jami.mc.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by felixzhao on 14/11/22.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MCInfo {

    Module module() default Module.S_MPOS_API_DEFAULT;

}
