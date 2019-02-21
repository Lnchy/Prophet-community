package com.nf147.prophet.util.validate;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface NeedPower {
    /**
     * 需要某个权限才能访问
     */
    String[] powerName() default "";
}
