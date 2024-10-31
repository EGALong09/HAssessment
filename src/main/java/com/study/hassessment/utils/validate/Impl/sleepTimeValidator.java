package com.study.hassessment.utils.validate.Impl;

import com.study.hassessment.utils.validate.Validator;

public class sleepTimeValidator implements Validator {
    @Override
    public boolean validate(Object data) {
        if(data instanceof Double){
            double time = (Double) data;
            return time > 0 && time < 24;
        }else if(data instanceof Integer){
            int time = (Integer) data;
            return time > 0 && time < 24;
        }else{
            return false;
        }
    }
}
