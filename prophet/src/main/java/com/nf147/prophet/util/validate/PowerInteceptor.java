package com.nf147.prophet.util.validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nf147.prophet.service.Impl.RoleRelatedServiceImpl;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class PowerInteceptor implements HandlerInterceptor {
    @Autowired
    RoleRelatedServiceImpl roleRelatedService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            Method method = handlerMethod.getMethod();

            //如果方法上有该标记
            NeedPower annotation = method.getAnnotation(NeedPower.class);

            //如果方法上没有标记就获取类上的标记
            if (annotation == null) {
                annotation = bean.getClass().getAnnotation(NeedPower.class);
            }

            if (annotation != null) {
                //获取session
                HttpSession session = request.getSession();

                //获取参数数组
                String[] powers = annotation.powerName();
                //获取用户名
                int userId = (int) session.getAttribute("userId");

                //验证必须要有权限名，如果没有权限名则不拦截，也可以是权限数组
                if (powers.length > 0) {
                    for (String power : powers) {
                        if (!roleRelatedService.userHavePower(userId, power)) {
                            response.setHeader("Content-type", "application/json;charset=UTF-8");
                            ObjectMapper mapper = new ObjectMapper();
                            String message = mapper.writeValueAsString(Result.status(false).code(401).msg("您无权进行此操作"));
                            PrintWriter out = response.getWriter();
                            out.print(message);
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
