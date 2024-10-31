package com.study.hassessment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
//    @Test
    public void testGen(){
        Map<String, Object> clamins = new HashMap<>();
        clamins.put("id","11112225555");
        clamins.put("nickname","测试小c");

        //生成jwt
        String token = JWT.create()
                .withClaim("user",clamins) //添加数值
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12)) //添加过期时间，系统当前时间加上12h
                .sign(Algorithm.HMAC256("egalong")); //指定加密算法，配置密钥

        System.out.println(token);
    }

//    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7Im5pY2tuYW1lIjoi5rWL6K-V5bCPYyIsImlkIjoiMTExMTIyMjU1NTUifSwiZXhwIjoxNzA2NTc3MTA5fQ." +
                "YzwlKx7W1iCgd3_bvEmHmxNhlCIEniYBcXJYih5yDAI";
        JWTVerifier jwtVerifier =  JWT.require(Algorithm.HMAC256("egalong")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token); //验证token，生成解析后的jwt对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
