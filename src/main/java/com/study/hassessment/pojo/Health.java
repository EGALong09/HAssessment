package com.study.hassessment.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.hassessment.utils.validate.ValidateHealthValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Health<T> {

    private String id; //用户id，11位手机号

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date; //日期时间，与用户id共同构成主键

    @NotNull
    private HealthType keyword; //健康数据的类型


    private T value;//健康数据的值
}
