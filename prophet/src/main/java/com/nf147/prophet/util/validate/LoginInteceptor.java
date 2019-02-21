package com.nf147.prophet.util.validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nf147.prophet.util.EmptyUtil;
import com.nf147.prophet.util.Result;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class LoginInteceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,Authorization,SessionToken,JSESSIONID,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");

        if (handler instanceof HandlerMethod) {
            HandlerMethod myHandler = (HandlerMethod) handler;
            //获取类
            Object bean = myHandler.getBean();
            //获取方法
            Method method = myHandler.getMethod();
            //如果方法上有标记
            NeedLogin methodAnnotation = method.getAnnotation(NeedLogin.class);
            //如果类上有标记
            NeedLogin annotation = bean.getClass().getAnnotation(NeedLogin.class);
            if (methodAnnotation != null || annotation != null) {
                HttpSession session = request.getSession();

                String userId = String.valueOf(session.getAttribute("userId"));
                if (EmptyUtil.checkUpStringIsNull(userId)) {
                    response.setHeader("Content-type", "application/json;charset=UTF-8");
                    ObjectMapper mapper = new ObjectMapper();
                    String message = mapper.writeValueAsString(Result.status(false).code(401).msg("请登陆"));
                    PrintWriter out = response.getWriter();
                    out.print(message);

                    return false;
                }
            }
        }

        return true;
    }
}
