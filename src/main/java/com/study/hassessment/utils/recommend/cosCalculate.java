package com.study.hassessment.utils.recommend;

import static org.apache.coyote.http11.Constants.a;

/**
 * 计算两个向量之间的余弦
 */
public class cosCalculate {

    //以basic为基础，计算param的余弦
    public static double cc(Double[] basic,Double[] param){

        //验证参数
        if(basic == null || param == null || basic.length != param.length){
            throw new IllegalArgumentException("参数不合法");
        }

        //相关变量
        double cos = 0;
        double dot = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for(int i=0;i<basic.length;i++){
            dot += basic[i]*param[i]; //点积
            normA += basic[i]*basic[i]; //平方和
            normB += param[i]*param[i];
        }
        if (normA == 0.0 || normB == 0.0){
            //存在一个0向量，考虑健康数据均为0分
            return 0.0;
        }else{
            cos = dot/(Math.sqrt(normA) *Math.sqrt(normB));
        }
        return cos;
    }
}
