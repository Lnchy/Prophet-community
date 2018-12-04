package com.nf147.seer.controller;

import com.nf147.seer.entity.Imgpath;
import com.nf147.seer.entity.User;
import com.nf147.seer.service.UserServiceImpl;
import com.nf147.seer.util.EmptyUtil;
import com.nf147.seer.util.Message;
import com.nf147.seer.util.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //登录
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Message> login(String userEmail, String userPwd, HttpSession session) {
        Message result = userService.login(userEmail, userPwd, session);
        return ResponseEntity.status(200).body(result);
    }

    //注册
    @PostMapping("/reg")
    @ResponseBody
    public ResponseEntity<Message> reg(User user) {
        Message result = userService.reg(user);
        return ResponseEntity.status(200).body(result);
    }


    //修改密码
    @PostMapping("/changePassword")
    @ResponseBody
    @NeedLogin
    public ResponseEntity changePassWord(String oldPwd, String newPwd, HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        Message result = null;

        //如果两个字符串都不为空
        if (!EmptyUtil.checkUpStringIsNull(oldPwd) && !EmptyUtil.checkUpStringIsNull(newPwd)) {
            result = userService.changePwd(oldPwd, newPwd, userId);
        } else {
            result = new Message(false, "请填写完整");
        }

        return ResponseEntity.status(200).body(result);
    }

    //更改资料
    @PostMapping("/changeInfo")
    @ResponseBody
    @NeedLogin
    public ResponseEntity<Message> changeUserInfo(String oldPwd, String newPwd, HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        Message result = null;

        //如果两个字符串都不为空
        if (!EmptyUtil.checkUpStringIsNull(oldPwd) && !EmptyUtil.checkUpStringIsNull(newPwd)) {
            result = userService.changePwd(oldPwd, newPwd, userId);
        } else {
            result = new Message(false, "请填写完整");
        }

        return ResponseEntity.status(200).body(result);
    }

    //上传图片
    @PostMapping("/upLoadImage")
    @ResponseBody
    @NeedLogin
    public ResponseEntity upLoadImage(@RequestParam("img")MultipartFile ufile, HttpServletRequest req) {
        Message result = userService.upLoadImage(ufile,req);
        return ResponseEntity.ok().body(result);
    }

}
