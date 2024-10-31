package com.study.hassessment.controller;

import com.study.hassessment.pojo.Artical;
import com.study.hassessment.pojo.Result;
import com.study.hassessment.service.ArticalService;
import com.study.hassessment.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/artical")
@Validated
public class ArticleController {

    @Autowired
    private ArticalService articalService;

    //推送10篇文章
    @GetMapping("/recommendN")
    public Result<Artical[]> recommendNArtical(int recommendNum){

        //需要查找的相似用户上限
        int similarUserNum = recommendNum / 2;

        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        //获取相似用户喜欢的文章id,最多5个
        String[] similarUserLoveId = articalService.getSimilarUserLoveId(id,similarUserNum);
        //生成被推送的十个文章id
        String[] recommendArticalId = articalService.getRecommendId(similarUserLoveId,recommendNum);

        //由id去找文章
        Artical[] recommendArticals = new Artical[recommendNum];
        for(int i=0;i<recommendNum;i++){
            recommendArticals[i] = articalService.getArtical(recommendArticalId[i]);
        }
        return Result.success(recommendArticals);
    }

    //获取一篇文章
    @GetMapping("/getArtical")
    public Result<Artical> getArtical(String articalId){
        Artical artical = articalService.getArtical(articalId);
        return Result.success(artical);
    }

    //喜欢文章
    @PostMapping ("/loveArtical")
    public Result<?> loveArtical(String articalId){
        //获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        String id = (String) map.get("id");

        return Result.success(articalService.loveArtical(articalId,id));
    }



}
