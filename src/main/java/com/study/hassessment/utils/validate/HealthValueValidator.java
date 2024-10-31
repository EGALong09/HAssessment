package com.study.hassessment.utils.validate;

import com.study.hassessment.pojo.Health;
import com.study.hassessment.pojo.HealthType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 校验工具类，实现自定义校验注解
 */
public class HealthValueValidator implements ConstraintValidator<ValidateHealthValue,Object> {

    @Override
    public void initialize(ValidateHealthValue constraintAnnotation){
        //初始化方法
    }

    //重写逻辑校验方法
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        if(value == null){
            //value不可为空
            return false;
        }
        if(value instanceof Health<?> health){

            //待校验数据的日期不可为空
            if(health.getDate() == null){
                return false;
            }
            //获取待校验数据的种类
            HealthType healthType = health.getKeyword();


            //获取待校验数据的值
            Object data = health.getValue();

            //声名一个校验器
            //根据待校验数据的种类从校验器工厂类里找到合适的校验器
            Validator validator = ValidatorFactory.getValidator(healthType);
            if(validator != null){
                return validator.validate(data);
            }else{
                return false;
            }

        }
        //value不是Health类型就返回
        return false;
    }
}


