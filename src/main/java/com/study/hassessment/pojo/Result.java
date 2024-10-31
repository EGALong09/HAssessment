package com.study.hassessment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//响应数据类，将获取的数据返回给前端
public class Result<T> {
    private Integer code; //业务状态码，0成功，1失败
    private String message; //提示信息
    private T data; //响应数据

    //返回操作成功的响应结果（带数据）
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //返回操作成功的响应结果（不带数据）
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    //返回操作失败的响应结果（无数据）
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
