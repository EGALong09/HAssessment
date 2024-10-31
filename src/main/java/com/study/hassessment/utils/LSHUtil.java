package com.study.hassessment.utils;
import com.study.hassessment.mapper.GradeMapper;
import info.debatty.java.lsh.LSHSuperBit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 局部敏感哈希算法
 *
 * */
public class LSHUtil {


    int vectors = 4; //数据维度
    int stage = 4; //哈希长度
    int buckets = 3; //桶
    int seed = 1223456; //随机数种子

    LSHSuperBit lsh = new LSHSuperBit(stage,buckets,vectors,seed);

    public int[] setHash(Double[] data){
        double[] data2 = Arrays.stream(data).mapToDouble(Double::doubleValue).toArray();
        int[] hash = lsh.hash(data2);
        return hash;
    }

}
