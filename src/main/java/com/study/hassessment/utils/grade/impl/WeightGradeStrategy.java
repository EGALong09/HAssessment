package com.study.hassessment.utils.grade.impl;

import com.study.hassessment.mapper.HealthMapper;
import com.study.hassessment.mapper.UserMapper;
import com.study.hassessment.utils.ThreadLocalUtil;
import com.study.hassessment.utils.grade.GradeStrategy;

import java.util.Map;
import java.util.Objects;

public class WeightGradeStrategy implements GradeStrategy {
    HealthMapper healthMapper;
    String keyword = "height";
    Map<String, Object> map = ThreadLocalUtil.get();
    String id = (String) map.get("id");
    @Override
    public Double getGrade(Object param) {
        if(param instanceof Integer || param instanceof Double){
            Float height = healthMapper.findNew(id,keyword);
            Float a = ((Float)param / (height*height));
            Double data = (100-((100/21)*Math.sqrt((a-21)*(a-21))));
            System.out.println("体重评分"+data);
            return data;
        }else{
            return null;
        }
    }
}
