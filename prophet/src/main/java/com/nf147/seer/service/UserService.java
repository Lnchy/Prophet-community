package com.nf147.seer.service;

import com.nf147.seer.entity.User;
import com.nf147.seer.util.Message;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

    //注册新用户
    Message reg (User user);

    //登录
    Message login(String email, String pwd, HttpSession session);

    //修改密码
    Message changePwd(String oldPwd,String newPwd,int userId);

    //上传图片
    Message upLoadImage(MultipartFile ufile, HttpServletRequest req);
}
