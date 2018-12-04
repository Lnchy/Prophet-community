package com.nf147.seer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class LoginInteceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod myHandler = (HandlerMethod) handler;
            Method method = myHandler.getMethod();
            //如果方法上有标记
            NeedLogin methodAnnotation = method.getAnnotation(NeedLogin.class);
            if (methodAnnotation != null) {
                HttpSession session = request.getSession();

                String userId = String.valueOf(session.getAttribute("userId"));
                if (EmptyUtil.checkUpStringIsNull(userId)) {
                    response.setHeader("Content-type", "application/json;charset=UTF-8");
                    ObjectMapper mapper = new ObjectMapper();
                    String message = mapper.writeValueAsString(new Message(false, "请登陆后再试"));
                    PrintWriter out = response.getWriter();
                    out.print(message);

                    return false;
                }
            }
        }

        return true;
    }
}
