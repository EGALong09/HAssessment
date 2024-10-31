package com.study.hassessment.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;
/**
 * 为了登陆验证生成JWT令牌的工具
 * */
public class JwtUtil {

    //设置密钥
    private static final String KEY = "egalong";

    //接收业务数据，生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims) //添加数值
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) //添加过期时间，系统当前时间加上12h
                .sign(Algorithm.HMAC256("egalong")); //指定加密算法，配置密钥
    }

    //接收token，验证token，并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256("egalong"))
                .build()
                .verify(token) //验证token，生成解析后的jwt对象
                .getClaim("claims")
                .asMap();
    } //验证token，生成解析后的jwt对象
}