package com.nf147.prophet.util.validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nf147.prophet.service.Impl.RoleRelatedServiceImpl;
import com.nf147.prophet.util.EmptyUtil;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class RoleInteceptor implements HandlerInterceptor {
    @Autowired
    RoleRelatedServiceImpl roleRelatedService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            Method method = handlerMethod.getMethod();

            //如果方法上有该标记
            NeedRole annotation = method.getAnnotation(NeedRole.class);

            //如果方法上没有标记就获取类上的标记
            if (annotation == null) {
                annotation = bean.getClass().getAnnotation(NeedRole.class);
            }

            if (annotation != null) {
                //获取session
                HttpSession session = request.getSession();
                //获取要验证的角色
                String checkRole = EmptyUtil.checkUpStringIsNull(annotation.roleName()) ? "user" : annotation.roleName();

                //如果没有基本的user权限，则阻止
                if (!roleRelatedService.userIsRole((Integer) session.getAttribute("userId"), checkRole)) {
                    response.setHeader("Content-type", "application/json;charset=UTF-8");
                    ObjectMapper mapper = new ObjectMapper();
                    String message = mapper.writeValueAsString(Result.status(false).code(401).msg("您无权进行此操作"));
                    PrintWriter out = response.getWriter();
                    out.print(message);
                    return false;
                }

            }
        }
        return true;
    }
}
