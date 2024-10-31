package com.study.hassessment.mapper;

import com.study.hassessment.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * 标记mapper类，和数据库进行交互
 */
@Mapper
public interface UserMapper {

    //根据id查找用户
    @Select("select * from user_info where id=#{id}")
    User findByID(String id);



    //添加用户
    //注册必填手机号和密码，默认昵称为id手机号，自行注册均为用户非医生
    @Insert("insert into user_info(id,nickname,password,is_doctor)" + " values(#{id},#{id},#{password},0) ")
    void add(String id, String password);

    //修改用户信息
    @Update("update user_info set nickname = #{nickname} where id = #{id}")
    void updateNickname(String id, String nickname);

    @Update("update user_info set sex = #{sex} where id = #{id}")
    void updateSex(String id, Integer sex);

    @Update("update user_info set birthday = #{birthday} where id = #{id}")
    void updateBirthday(String id, String birthday);

    //更新头像地址
    @Update("update user_info set pic=#{avatarUrl} where id = #{id}")
    void updateAvatar(String id, String avatarUrl);

    //更新密码
    @Update("update  user_info set password=#{newPwd} where id=#{id}")
    void updatePwd(String id, String newPwd);

    //注销用户
    @Delete("delete from user_info where id = #{id}")
    void delete(String id);

    //获取头像地址
    @Select("select pic from user_info where id = #{id}")
    String getAvatar(String id);
}
