package com.study.hassessment.utils.grade.impl;

import com.study.hassessment.utils.grade.GradeStrategy;

public class HeartRateGradeStrategy implements GradeStrategy {
    @Override
    public Double getGrade(Object param) {
        if(param instanceof Integer){
            Integer data = (Integer) param / 3;
            System.out.println("心率评分"+data);
            return data.doubleValue();
        } else{
            return null;
        }
    }
}
