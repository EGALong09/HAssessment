package com.study.hassessment.pojo;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class User {
    @Pattern(regexp = "^\\d{11}$")
    private String id; //用户id，11位手机号

    @JsonIgnore //使springmvc将当前对象转化为json字符串时，忽略password
    private String password; //密码长度255

    @Pattern(regexp = "^\\S{1,7}$") //设置用户昵称长度
    private String nickname; //用户昵称长度7

    @Max(2)
    @Min(1)
    private Integer sex; //性别，男1女2

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String birthday;

    private Integer isDoctor; //是否是医生

    private String pic;
}
