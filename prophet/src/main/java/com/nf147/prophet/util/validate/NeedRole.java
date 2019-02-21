package com.nf147.prophet.util.validate;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface NeedRole {
    /**
     * 需要某个角色才能访问
     */
    String roleName() default "";
}
