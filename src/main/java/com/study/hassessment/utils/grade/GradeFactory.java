package com.study.hassessment.utils.grade;

import com.study.hassessment.utils.grade.impl.HealthGradeStrategy;
import com.study.hassessment.utils.grade.impl.HeartRateGradeStrategy;
import com.study.hassessment.utils.grade.impl.SleepTimeGradeStrategy;
import com.study.hassessment.utils.grade.impl.WeightGradeStrategy;

public class GradeFactory {

    //根据keyword分配不同的评分策略实现
    public static GradeStrategy getGradeStrategy(String keyword){
        switch (keyword){
            case "weight":
                return new WeightGradeStrategy();
            case "height":
                return new HealthGradeStrategy();
            case "heartRate":
                return new HeartRateGradeStrategy();
            case "sleepTime":
                return new SleepTimeGradeStrategy();
            default:
                return null;
        }
    }
}
