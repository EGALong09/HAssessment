package com.study.hassessment.mapper;

import com.study.hassessment.pojo.Grade;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface GradeMapper {

    //根据id查询是否有评分
    @Select("select exists(select * from user_grade where id=#{id})")
    int find(String id);

    //新建用户评分项
    @Insert("insert into user_grade(id,weight,height,sleepTime,heartRate)" + " values(#{id},0,0,0,0) ")
    void setNew(String id);

    @Update("update user_grade set ${keyword}=#{grade} where id=#{id}")
    //设置分数方法
    void set(String id, String keyword, Double grade);

    //获取分数方法
    @Select("select weight,height,sleepTime,heartRate from user_grade where id=#{id}")
    Grade getAll(String id);

    @Select("select ${keyword} from user_grade where id=#{id}")
    String get(String id,String keyword);

    //填写哈希值方法
    @Update("update user_grade set hash=#{hash} where id=#{id}")
    void setHash(String hash,String id);

    //注销用户成绩
    @Delete("delete from user_grade where id = #{id}")
    void delete(String id);
}
