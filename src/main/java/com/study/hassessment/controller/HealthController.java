package com.study.hassessment.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.hassessment.pojo.Health;
import com.study.hassessment.pojo.HealthType;
import com.study.hassessment.pojo.Result;
import com.study.hassessment.service.HealthService;
import com.study.hassessment.utils.ThreadLocalUtil;
import com.study.hassessment.utils.validate.ValidateHealthValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/health")
@Validated
public class HealthController {

    //坐在电脑前面挠脑袋死掉了捏

    @Autowired
    private HealthService healthService;

    //2.1统计信息
    //需要前端传入一下被统计信息的关键字
    @GetMapping("/statsGet")
    public Result<?> statsGet(String keyword) {

        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //验证keyword在可获取的信息枚举类
        try {
            HealthType t = HealthType.valueOf(keyword);
            //获取字段的统计信息
            Health<?> stats = healthService.findStats(id, keyword);
            stats.setId(id);
            stats.setKeyword(t);

            if (stats.getValue() == null) {
                return Result.error("无信息！");
            } else {
                return Result.success(stats);
            }
        } catch (IllegalArgumentException e) {
            return Result.error("keyword字段无效");
        }
    }

    //2.2设置信息
    //为了减少各类不同数据验证的压力，这里传入Health对象，在Health类里完成数据验证
    //其中必传日期，keyword，某个数据
    @PatchMapping("/set")
    public Result<?> set(@RequestBody @ValidateHealthValue Health<?> params) {

        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //验证完成，开始操作数据库
        //数据项依次是：数据值，用户id，日期，数据值类型
        healthService.set(params.getValue(), id, params.getDate(), String.valueOf(params.getKeyword()));
        return Result.success();
    }

    //2.3获取信息
    //要获取那么多天的信息
    //深思熟虑后决定写两个get方法分别获取
    //必传日期和keyword
    @GetMapping(value = {"/get1", "/get2"}) //get1获取日期
    public Result<?> get(@NotNull @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @NotNull String keyword, HttpServletRequest request) {
        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //验证keyword在可获取的信息枚举类
        try {
            HealthType t = HealthType.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            return Result.error("keyword字段无效");
        }

        //获取请求的路径
        String uri = request.getRequestURI();

        if (uri.equals("/health/get1")) {
            Object[] healthData = healthService.findData1(id, date, keyword);
            if (healthData == null) {
                return Result.error("该日无所需信息！");
            } else {
                return Result.success(healthData);
            }

        } else if (uri.equals("/health/get2")) {
            Object[] healthData = healthService.findData2(id, date, keyword);
            if (healthData == null) {
                return Result.error("该日无所需信息！");
            } else {
                return Result.success(healthData);
            }
        } else {
            return Result.error("无效请求");
        }

    }

    //2.4获取最新数据日期
    //必传keyword
    @GetMapping("/getDay")
    public Result<?> getDay(String keyword) {

        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //验证keyword在可获取的信息枚举类
        try {
            HealthType t = HealthType.valueOf(keyword);

            //获取字段的数据和日期
            //是五个日期组成的数组类型
            Date dayday = healthService.findNewDay(id, keyword);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            if (dayday == null) {
                return Result.error("该信息无数据");
            } else {
                String day = df.format(dayday);
                return Result.success(day);
            }
        } catch (IllegalArgumentException e) {
            return Result.error("keyword字段无效");
        }
    }

    //2.5获取用户健康评分
    @GetMapping("/getGrade")
    public Result<?> getGrade(String keyword) {
        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //验证keyword在可获取的信息枚举类
        try {
            HealthType t = HealthType.valueOf(keyword);
        } catch (IllegalArgumentException e) {
            if(!keyword.equals("total")){
                return Result.error("keyword字段无效");
            }
        }

        String grade = healthService.getGrade(id,keyword);

        if(grade == null){
            return Result.error("暂无评分");
        }else{
            return Result.success(grade);
        }
    }
    //2.6获取用户全部健康评分
    @GetMapping("/getAllGrade")
    public Result<Double[]> getAllGrade() {

        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        Double[] grade = healthService.getAllGrade(id);

        if(grade == null){
            return Result.error("暂无评分");
        }else{
            return Result.success(grade);
        }
    }
}
