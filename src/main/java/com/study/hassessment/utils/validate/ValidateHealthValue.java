package com.study.hassessment.utils.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 自定义一个验证类的注解
 * 用于各种健康数据的自定义验证
 */
@Retention(RetentionPolicy.RUNTIME) //注解保留到运行时
@Target({ElementType.PARAMETER, ElementType.METHOD,ElementType.FIELD}) //注解作用于方法
@Documented
@Constraint(validatedBy = HealthValueValidator.class)
public @interface ValidateHealthValue {
    //校验不通过的提示信息
    String message() default "数据不合法";

    //分组
    Class<?>[] groups() default{};

    //负载
    Class<? extends Payload>[] payload() default{};
}
