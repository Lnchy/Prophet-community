package com.nf147.seer.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface NeedLogin {
/*
登录验证注解
 */
}
