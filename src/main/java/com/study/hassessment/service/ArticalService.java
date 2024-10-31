package com.study.hassessment.service;

import com.study.hassessment.pojo.Artical;

public interface ArticalService {

    //获取给定id用户的相似用户喜欢的文章id数组
    public String[] getSimilarUserLoveId(String id,int userNum);

    //获取推荐文章的文章id数组
    public String[] getRecommendId(String[] similarUserLoveId,int recommendNum);

    //根据文章id获取文章内容
    Artical getArtical(String articalId);

    Object loveArtical(String articalId, String id);
}
