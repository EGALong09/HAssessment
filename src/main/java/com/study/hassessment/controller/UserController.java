package com.study.hassessment.controller;

import com.study.hassessment.pojo.Result;
import com.study.hassessment.pojo.User;
import com.study.hassessment.service.UserService;
import com.study.hassessment.utils.JwtUtil;
import com.study.hassessment.utils.Md5Util;
import com.study.hassessment.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * controller层对前端请求进行处理，并调用相应后端接口完成操作
 * Controller和ResponseBody的组合注解；
 * 前者标记控制器类，处理客户端发起的请求，并返回适当的视图（View）作为响应；
 * 后者让方法的返回值直接写入HTTP响应体中（例如json或者xml），而不是被解析为视图；
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    //1.1注册
    @PostMapping("/register")
    public Result<?> register(@Pattern(regexp = "^\\d{11}$") String id, @Pattern(regexp = "^\\S{6,20}") String password) {
        //根据id手机号查询用户是否存在
        User registerUser = userService.findByID(id);
        if (registerUser == null) {
            //用户不存在，可以注册
            userService.register(id, password);
            return Result.success(); //返回成功的信息
        } else {
            //用户存在，跳转回登录
            return Result.error("用户已存在！");
        }
    }

    //1.2登录
    @PostMapping("/login")
    public Result<?> login(@Pattern(regexp = "^\\d{11}$") String id, @Pattern(regexp = "^\\S{6,20}") String password) {
        //根据id手机号查询用户是否存在
        User loginUser = userService.findByID(id);
        if (loginUser == null) {
            //判断用户是否存在
            return Result.error("用户不存在！");
        }
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            //判断密码是否正确
            //登录成功则生成登录令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId()); //用id和昵称生成令牌
            claims.put("nickname", loginUser.getNickname());

            String token = JwtUtil.genToken(claims);

            return Result.success(token); //返回登录成功生成的令牌
        } else {
            return Result.error("密码错误");
        }
    }

    //1.3获取用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        //获取ThreadLocal中的业务数据（map类型）
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //调用service
        User user = userService.findByID(id);//寻找id，返回用户数据对象
        return Result.success(user);
    }

    //1.4更新用户基本信息
    @PatchMapping("/userUpdateInfo")
    public Result<?> userUpdateInfo(@RequestBody @Validated User user) {
        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        String nickname = user.getNickname();
        Integer sex = user.getSex();
        String birthday = user.getBirthday();

        //调用service
        if (nickname != null) {
            //更新用户昵称
            userService.updateNickname(id, nickname);
        }
        if (sex != null) {
            //更新用户性别
            userService.updateSex(id, sex);
        }
        if (birthday != null) {
            //更新用户年龄
            userService.updateBirthday(id, birthday);
        }
        return Result.success();
    }

    //1.5上传用户头像
    @PostMapping("/userUpdateAvatar")
    public Result<?> userUpdateAvatar(@RequestParam("avatar")  MultipartFile uploadFile) {

        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        System.out.println(uploadFile);
        //调用service
        String result = userService.updateAvatar(id, uploadFile);
        if(result.equals("false")){
            return Result.error("上传失败");
        }else{
            return Result.success(result);
        }
    }

    //1.6更新用户密码
    @PatchMapping("/userUpdatePwd")
    public Result<?> userUpdatePwd(@RequestBody Map<String,String> params){
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");
        String oldPwd = Md5Util.getMD5String(params.get("oldPwd")); //将用户输入的密码转为加密字符串
        String newPwd = Md5Util.getMD5String(params.get("newPwd"));
        String rePwd = Md5Util.getMD5String(params.get("rePwd"));

        //手动校验参数（validation无法满足此处校验参数的需求）
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            //1.确认必要参数非空
            return Result.error("缺少必要参数！");
        }
        User loginUser = userService.findByID(id);
        if (!loginUser.getPassword().equals(oldPwd)){
            //2.校验原密码是否正确
            return Result.error("原密码错误！");
        }
        if (!rePwd.equals(newPwd)){
            //3.验证两次密码输入是否相等
            return Result.error("两次填写的密码不同！");
        }

        //调用service
        userService.updatePwd(id,newPwd);
        return Result.success();
    }

    //1.7注销用户
    @DeleteMapping("/userDelete")
    public Result<?> userDelete(/*@Pattern(regexp = "^\\S{6,20}") String password*/){

        //获取ThreadLocal中的业务数据（map类型）
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");
        User loginUser = userService.findByID(id);

        //校验密码是否正确
//        if (!loginUser.getPassword().equals(Md5Util.getMD5String(password))){
//            return Result.error("密码错误！");
//        }

        //调用service
        userService.delete(id);
        return Result.success();
    }

    //1.8返回用户头像
    @GetMapping("/getAvatar")
    public Resource getAvatar(){
        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        return userService.getAvatar(id);
    }

}
