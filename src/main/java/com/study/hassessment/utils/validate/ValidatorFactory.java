package com.study.hassessment.utils.validate;

import com.study.hassessment.pojo.HealthType;
import com.study.hassessment.utils.validate.Impl.heartRateValidator;
import com.study.hassessment.utils.validate.Impl.heightAndWeightValidator;
import com.study.hassessment.utils.validate.Impl.sleepTimeValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * 校验器工厂类
 * 根据健康数据种类获取对应的校验器
 * */
public class ValidatorFactory {

    //定义一个校验器的缓存，存储不同的校验器
    private static final Map<HealthType,Validator> validatorMap = new HashMap<>();

    //静态代码块，初始化校验器的缓存，注册校验器
    static{
        register(HealthType.height,new heightAndWeightValidator());
        register(HealthType.weight,new heightAndWeightValidator());
        register(HealthType.heartRate,new heartRateValidator());
        register(HealthType.sleepTime,new sleepTimeValidator());
    }

    //注册校验器
    private static void register(HealthType healthType,Validator validator){
        validatorMap.put(healthType,validator);
    }

    //获取校验器
    //根据数据类型返回对应的校验器
    public static Validator getValidator(HealthType healthType){
        return validatorMap.get(healthType);
    }

}
