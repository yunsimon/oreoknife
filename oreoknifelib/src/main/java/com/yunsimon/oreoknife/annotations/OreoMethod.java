package com.yunsimon.oreoknife.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by yunsimon on 2018/6/5.
 */
@Target(METHOD)
@Retention(CLASS)
public @interface OreoMethod {
    String log() default "";
}
