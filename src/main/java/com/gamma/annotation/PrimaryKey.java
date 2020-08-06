package com.gamma.annotation;

import java.lang.annotation.*;


@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKey {
    boolean isKey() default true;
}
