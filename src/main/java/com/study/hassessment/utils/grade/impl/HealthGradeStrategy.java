package com.study.hassessment.utils.grade.impl;

import com.study.hassessment.utils.grade.GradeStrategy;

public class HealthGradeStrategy implements GradeStrategy {
    @Override
    public Double getGrade(Object param) {
        if(param instanceof Integer){
            Integer data = (Integer) param / 3;
            System.out.println("身高评分"+data);
            return data.doubleValue();
        } else if (param instanceof Double) {
            Double data = (Double) param / 3;
            System.out.println("身高评分"+data);
            return data.doubleValue();
        }else{
            return null;
        }
    }
}
