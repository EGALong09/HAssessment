package com.study.hassessment.service.impl;

import com.study.hassessment.mapper.GradeMapper;
import com.study.hassessment.mapper.HealthMapper;
import com.study.hassessment.pojo.Grade;
import com.study.hassessment.pojo.Health;
import com.study.hassessment.service.HealthService;
import com.study.hassessment.utils.LSHUtil;
import com.study.hassessment.utils.grade.GradeFactory;
import com.study.hassessment.utils.grade.GradeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private HealthMapper healthMapper;
    @Autowired
    private GradeMapper gradeMapper;

    //查询表内是否存在某个信息
    private boolean find(String id, Date date) {
        if (healthMapper.find(id, date) > 0) {
            return true;
        } else {
            return false;
        }
    }

    //查询某日的5日信息
    public Object[] findData1(String id, Date date, String keyword) {
        return healthMapper.findData1(id, date, keyword);
    }
    public Object[] findData2(String id, Date date, String keyword) {
        return healthMapper.findData2(id, date, keyword);
    }

    //查询keyword的统计信息
    public Health<?> findStats(String id, String keyword) {
        Health stats = new Health();
        stats.setValue(new Object[]{
                healthMapper.findHigh(id, keyword),
                healthMapper.findLow(id, keyword),
                healthMapper.findNew(id, keyword)
        });
        return stats;
    }

    //获取用户全部健康评分的方法
    public Double[] getAllGrade(String id){
        Grade g = gradeMapper.getAll(id);
        Double[] data = new Double[4];
        data[0] = g.getWeight();
        data[1] = g.getHeight();
        data[2] = g.getHeartRate();
        data[3] = g.getSleepTime();

        return data;
    }

    //总设置信息方法
    @Override
    public void set(Object param, String id, Date date, String keyword) {
        if (!find(id, date)) {
            //用户没有该日信息则新建
            healthMapper.setNew(id, date);
        }
        healthMapper.set(param, id, date, keyword);

        //设置完信息，还要用工厂分配一下评价方法
        //策略工厂返回keyword对应评分策略，进行评分
        //评完分需要重新生成哈希值
        GradeStrategy gradeStrategy = GradeFactory.getGradeStrategy(keyword);
        if (gradeStrategy != null) {
            Double grade = gradeStrategy.getGrade(param);
            if(gradeMapper.find(id) == 0){
                //没有用户信息则新建
                System.out.println("新建用户");
                gradeMapper.setNew(id);
            }
            //填写评分
            gradeMapper.set(id,keyword,grade);
            //生成新的哈希值并填写

            //获取全部成绩
            Double[] data = getAllGrade(id);

            //生成哈希值
            LSHUtil lsh = new LSHUtil();
            String hash = Arrays.toString(lsh.setHash(data));
            System.out.println(hash);
            gradeMapper.setHash(hash,id);
        }

    }

    //获取最新数据日期
    @Override
    public Date findNewDay(String id, String keyword) {
        return healthMapper.findNewDay(id,keyword);
    }

    //获取用户健康评分
    @Override
    public String getGrade(String id, String keyword) {
        return gradeMapper.get(id,keyword);
    }

}
