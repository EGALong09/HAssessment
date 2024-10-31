package com.study.hassessment.interceptors;

import com.study.hassessment.pojo.Result;
import com.study.hassessment.utils.JwtUtil;
import com.study.hassessment.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 登录拦截器
 * component标识组件类
 * */

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证

        //获取token
        String token = request.getHeader("Authorization");

        //验证token登陆状态
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token); //记得这个claims里面有一个id和nickname
            //业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //验证成功，放行
            return true;
        } catch (Exception e) {
            //验证失败，不放行
            //http响应码401
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据，防止内存泄露
        ThreadLocalUtil.remove();
    }
}
