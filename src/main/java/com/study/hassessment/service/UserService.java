package com.study.hassessment.service;

import com.study.hassessment.pojo.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    //根据用户id查询用户
    User findByID(String id);

    //注册
    void register(String id, String password);

    //更新用户基本信息
    void updateNickname(String id, String nickname);

    void updateSex(String id, Integer sex);

    void updateBirthday(String id, String birthday);


    //更新用户头像
    String updateAvatar(String id, MultipartFile uploadFile);

    //更新用户密码
    void updatePwd(String id, String newPwd);

    //删除登录用户
    void delete(String id);

    //获取用户头像
    public Resource getAvatar(String id);
}
