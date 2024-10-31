package com.study.hassessment.service.impl;

import com.study.hassessment.mapper.GradeMapper;
import com.study.hassessment.mapper.HealthMapper;
import com.study.hassessment.mapper.UserMapper;
import com.study.hassessment.pojo.User;
import com.study.hassessment.service.UserService;
import com.study.hassessment.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 标记service类，注册一个UserService对象到ioc容器
 */
@Service
public class UserServiceImpl implements UserService {

    //创建mapper层对象
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HealthMapper healthMapper;
    @Autowired
    private GradeMapper gradeMapper;

    //根据id查找用户
    @Override
    public User findByID(String id) {
        User user = userMapper.findByID(id);
        return user;
    }

    //注册
    @Override
    public void register(String id, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加进数据库
        userMapper.add(id, md5String);
        //新建评分
        gradeMapper.setNew(id);
    }

    //更新用户基本信息
    @Override
    public void updateNickname(String id, String nickname) {
        //更新用户昵称
        userMapper.updateNickname(id, nickname);
    }

    @Override
    public void updateSex(String id, Integer sex) {
        //更新用户性别
        userMapper.updateSex(id, sex);
    }

    @Override
    public void updateBirthday(String id, String age) {
        //更新用户年龄
        userMapper.updateBirthday(id, age);
    }

    //更新用户头像
    @Override
    public String updateAvatar(String id, MultipartFile uploadFile) {

        //指定本地文件夹存储图片
//        String filePath = "/static/Hassessment/AvatarUpload";
        String filePath = "E:/aaaaaaMyWork/static/HAssessment/avatar";

        //文件名
        String fileNameSup = "Avatar";
        String fileName = uploadFile.getOriginalFilename();
        //文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String time = dateTime.format(formatter);

        //重新生成文件名
        fileName = id+fileNameSup+time+suffixName;

        //创建一个目录对象
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            //文件夹不存在则新建
            file.mkdir();
        }
        try{
            //创建一个文件对象
            File avatarFile = new File(file,fileName);
            avatarFile.createNewFile();
            avatarFile.setReadable(true,false);
            avatarFile.setWritable(true,false);
            avatarFile.setExecutable(true,false);
            uploadFile.transferTo(avatarFile);
            userMapper.updateAvatar(id,fileName);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

    //更新用户密码
    @Override
    public void updatePwd(String id, String newPwd) {
        userMapper.updatePwd(id, newPwd);
    }

    //删除登录用户
    @Override
    public void delete(String id) {
        userMapper.delete(id);
        healthMapper.deleteAll(id);
        gradeMapper.delete(id);
    }

    //获取用户头像
    @Override
    public Resource getAvatar(String id){
        String avatarPath = userMapper.getAvatar(id);
        if(avatarPath == null){
            return null;
        }else{
            Path path = Paths.get(avatarPath);
            try {
                Resource resource = new UrlResource(path.toUri());
                if(resource.exists()){
                    return resource;
                }else{
                    return null;
                }
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}
