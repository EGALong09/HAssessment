package com.study.hassessment.utils.validate.Impl;

import com.study.hassessment.utils.validate.Validator;

public class heartRateValidator implements Validator {
    @Override
    public boolean validate(Object data) {
        if(data instanceof Integer){
            int heartRate = (Integer)data;
            return heartRate >0 && heartRate <300;
        }else{
            return false;
        }
    }
}
