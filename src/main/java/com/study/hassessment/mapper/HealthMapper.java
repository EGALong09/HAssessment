package com.study.hassessment.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface HealthMapper {

    //根据id和日期获取健康信息
    @Select("select count(*) from user_health where id=#{id} and date=#{date}")
    Integer find(String id, Date date);

    //查找获取某日的5日信息
    @Select ("select date from user_health where id = #{id} and ${keyword} is not null and date <= #{date} ORDER BY date DESC LIMIT 5")
    Object[] findData1(String id, Date date, String keyword);
    @Select ("select ${keyword} from user_health where id = #{id} and ${keyword} is not null and date <= #{date} ORDER BY date DESC LIMIT 5")
    Object[] findData2(String id, Date date, String keyword);


    //根据关键字和id查询统计信息
    @Select ("select max(${keyword}) from user_health where id = #{id}")
    Float findHigh (String id, String keyword);
    @Select ("select min(${keyword}) from user_health where id = #{id}")
    Float findLow (String id, String keyword);
    @Select ("select ${keyword} from user_health where id = #{id} and date = (select max(date) from user_health where id = #{id} and ${keyword} is not null)")
    Float findNew (String id, String keyword);

    //新建信息
    @Insert("insert into user_health(id,date)" + " values(#{id},#{date}) ")
    void setNew(String id,Date date);

    //总设置信息方法
    @Update("update user_health set ${keyword}=#{param} where id=#{id} and date=#{date}")
    void set(Object param, String id, Date date, String keyword);

    //删除信息
    @Delete("delete from user_health where id=#{id}")
    void deleteAll(String id);

    //获取最新数据日期
//    @Select ("select date,${keyword} from user_health where id = #{id} and ${keyword} is not null ORDER BY date DESC LIMIT 5")
//    @Select("SELECT date, info_a FROM mytable WHERE info_a IS NOT NULL ORDER BY date DESC LIMIT 5")
    @Select ("select max(date) from user_health where id = #{id} and ${keyword} is not null")
    Date findNewDay(String id, String keyword);

}
