package com.nf147.prophet.util.validate;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface NeedLogin {
/*
登录验证注解
 */
}
