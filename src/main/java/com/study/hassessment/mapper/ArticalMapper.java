package com.study.hassessment.mapper;

import com.study.hassessment.pojo.Artical;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Mapper
public interface ArticalMapper {
    //获取文章上限
    @Select("select title from artical where id = 0")
    String findArticalMax();

    //寻找同一个哈希桶内的用户
    @Select("select id from user_grade where hash = (select hash from user_grade where id = #{userId}) and id <> #{userId}")
    String[] getSimilarUserId(String userId);

    //获取userId最近喜欢的articalId
    @Select("select articalID from artical_love where id = #{userId} and time = (select max(time) from artical_love where id = #{userId})")
    String getUserLoveRecently(String userId);

    //由id获取文章
    @Select("select * from artical where id = #{articalId}")
    Artical getArtical(String articalId);

    //增加文章的喜欢数量
    @Update("update artical set love_num = love_num+1 where id = #{articalId}")
    void loveArtical(String articalId);

    //增加喜欢的记录
    @Insert("insert into artical_love(id, time, articalID)" + " values(#{id},#{nowTime},#{articalId})")
    void setLoveInfo(String articalId, String id, LocalDateTime nowTime);

    //获取文章的喜欢数量
    @Select("select love_num from artical where id = #{articalId}")
    Integer getLoveNum(String articalId);
}
