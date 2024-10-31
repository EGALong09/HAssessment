package com.study.hassessment.utils.validate.Impl;

import com.study.hassessment.utils.validate.Validator;

public class heightAndWeightValidator implements Validator {
    @Override
    //身高的校验类
    //身高需要在0-300之间
    public boolean validate(Object data) {

        if(data instanceof Double){
            double height = (Double) data;
            return height > 0 && height < 300;
        }else if(data instanceof Integer){
            int height = (Integer) data;
            return height > 0 && height < 300;
        }else {
            return false;
        }
    }
}
