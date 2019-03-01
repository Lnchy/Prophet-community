package com.nf147.prophet.service;

import com.nf147.prophet.entity.User;
import com.nf147.prophet.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {

    //注册新用户
    Result reg(User user);

    //登录
    Result login(String email, String pwd, HttpSession session);

    //修改密码
    Result changePwd(String oldPwd, String newPwd, int userId);

    //上传图片
    Result upLoadImage(MultipartFile ufile, int userId);

    //获取自己所有信息
    Result getUserInfoById(int userId);

    //获取行业列表
    Result getIndustrys();

    //更改用户背景图片
    boolean changeBackgroundImg(int userId, String imgPath);

    //判断一个邮箱是否有注册过
    boolean testEmailIsReg(String email);

    //判断一个用户名是否存在
    boolean testUserNameIsReg(String userName);

}
