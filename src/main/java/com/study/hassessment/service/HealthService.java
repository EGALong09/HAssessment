package com.study.hassessment.service;

import com.study.hassessment.pojo.Health;

import java.util.Date;

public interface HealthService {

    //查询keyword的统计信息
    Health<?> findStats(String id, String keyword);

    //查询某日的5日信息
    //不知道查出来的是个啥所以用Object
    Object[] findData1(String id, Date date, String keyword);
    Object[] findData2(String id, Date date, String keyword);

    //获取用户全部健康评分的方法
    public Double[] getAllGrade(String id);

    //总设置信息方法
    void set(Object param, String id, Date date, String keyword);

    //查找最新数据日期
    Date findNewDay(String id, String keyword);

    //获取用户健康评分
    String getGrade(String id, String keyword);
}
