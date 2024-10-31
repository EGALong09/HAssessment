package com.study.hassessment.service.impl;

import com.study.hassessment.mapper.ArticalMapper;
import com.study.hassessment.mapper.GradeMapper;
import com.study.hassessment.pojo.Artical;
import com.study.hassessment.pojo.Grade;
import com.study.hassessment.service.ArticalService;
import com.study.hassessment.service.HealthService;
import com.study.hassessment.utils.LSHUtil;
import com.study.hassessment.utils.recommend.cosCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

@Service
public class ArticalServiceImpl implements ArticalService {

    @Autowired
    private ArticalMapper articalMapper;
    @Autowired
    private HealthService healthService;

    //寻找相似用户
    private String[] getSimilarUserId(String userAId,int userNum){
        //寻找到同一个哈希桶内的用户id
        String[] hashUserId = articalMapper.getSimilarUserId(userAId);

//        System.out.println("相似用户id："+Arrays.deepToString(hashUserId));

        //获取userA全部成绩
        Double[] userAData = healthService.getAllGrade(userAId);

//        System.out.println("被查询的用户成绩："+Arrays.toString(userAData));


        String[][] idSimilarity = new String[hashUserId.length][2]; //记录被查询的用户和其相似度

        //遍历计算相似度
        for(int i=0;i<hashUserId.length;i++){
            //获取当前id和数据
            String userBId = hashUserId[i];
            Double[] userBData = healthService.getAllGrade(userBId);
            //计算余弦相似度
            double similarity = cosCalculate.cc(userAData,userBData);

            //存数组
            idSimilarity[i][0] = userBId;
            idSimilarity[i][1] = String.valueOf(similarity);
        }
//        System.out.println("相似用户id和相似度："+Arrays.deepToString(idSimilarity));

        //相似度降序排序
        Arrays.sort(idSimilarity, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                //相似度字符转为double类型进行比较
                return Double.compare(Double.parseDouble(o2[1]),Double.parseDouble(o1[1]));
            }
        });
//        System.out.println("排序后的相似用户id和相似度："+Arrays.deepToString(idSimilarity));

        if(hashUserId.length <= userNum){
            String[] result = Arrays.stream(idSimilarity).map(o->o[0]).toArray(String[]::new);
//            System.out.println("头五位相似用户id和相似度："+Arrays.toString(result));
            return result;
        }else{
            //获取相似度最大的五个id
            String[][] topFive = Arrays.copyOfRange(idSimilarity,0,userNum);

            //转一维数组
            String[] result = Arrays.stream(topFive).map(o->o[0]).toArray(String[]::new);

//            System.out.println("头五位相似用户id和相似度："+Arrays.toString(result));
            return result;
        }
    }

    //获取给定id用户的相似用户喜欢的文章id组
    @Override
    public String[] getSimilarUserLoveId(String id,int userNum){
        //找到相似用户的用户id
        String[] similarUserId = getSimilarUserId(id,userNum);
        //查询每个id最新喜欢的文章id
        String[] articalsId = new String[similarUserId.length];
        for(int i =0;i<articalsId.length;i++){
            articalsId[i] = articalMapper.getUserLoveRecently(similarUserId[i]);
        }
//        System.out.println("相似用户喜欢的文章id："+Arrays.toString(articalsId));
        return articalsId;
    }

    //获取推荐10篇文章的文章id数组
    @Override
    public String[] getRecommendId(String[] similarUserLoveId,int recommendNum){
        //获取随机文章id数组的上限
        String max = articalMapper.findArticalMax();
        int similarUserLoveIdSize = similarUserLoveId.length;
        int recommendIdSupSize = recommendNum - similarUserLoveIdSize;

        //随机数数组
        String[] recommendIdSup =  new String[recommendIdSupSize];
        HashSet<String> set = new HashSet<>();

        //填充随机数数组
        Random random = new Random();
        for(int i=0;i<recommendIdSupSize;i++){
            //随机数范围在1-num之间
            int randomNum = random.nextInt(Integer.parseInt(max))+1;
            //检查数字重复
            if(Arrays.asList(similarUserLoveId).contains(Integer.toString(randomNum)) ||
            set.contains(Integer.toString(randomNum))){
                //重复则返回生成一个随机数
                i--;
                continue;
            }else{
                //不同则添加
                recommendIdSup[i] = Integer.toString(randomNum);
                set.add(Integer.toString(randomNum));
            }
        }
//        System.out.println("生成的推荐文章id列表："+Arrays.toString(recommendIdSup));

        //合并数组返回
        String[] recommendId = Arrays.copyOf(similarUserLoveId,recommendNum);
        System.arraycopy(recommendIdSup,0,recommendId,similarUserLoveIdSize,recommendIdSupSize);

        System.out.println("合并后的文章id列表："+Arrays.toString(recommendId));
        return recommendId;
    }

    //由id获取文章
    @Override
    public Artical getArtical(String articalId) {
        System.out.println(articalId);
        return articalMapper.getArtical(articalId);
    }

    @Override
    public Integer loveArtical(String articalId, String id) {
        //文章表增加喜欢的数量
        articalMapper.loveArtical(articalId);
        //喜欢表增加用户喜欢的记录
        articalMapper.setLoveInfo(articalId,id, LocalDateTime.now());
        //返回当前文章喜欢的数量
        return articalMapper.getLoveNum(articalId);
    }

}
