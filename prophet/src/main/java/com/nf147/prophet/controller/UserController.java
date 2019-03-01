package com.nf147.prophet.controller;

import com.nf147.prophet.entity.User;
import com.nf147.prophet.service.Impl.UserServiceImpl;
import com.nf147.prophet.util.EmptyUtil;
import com.nf147.prophet.util.validate.NeedLogin;
import com.nf147.prophet.util.validate.NeedRole;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {

        return userService.login(user.getUserEmail(), user.getUserPwd(), session);
    }

    //注册
    @PostMapping("/reg")
    public Result reg(@RequestBody User user) {
        return userService.reg(user);
    }

    //检测邮箱是否注册过
    @GetMapping("/reg/email")
    public Result testEmail(@RequestParam("email") String email) {
        if (userService.testEmailIsReg(email)) {
            return Result.status(false).code(415).msg("邮箱已被注册");
        } else {
            return Result.status(true).code(200).msg("可以注册");
        }
    }

    //检测昵称是否注册过
    @GetMapping("/reg/name")
    public Result testUserName(@RequestParam("name") String name) {
        String regx = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{3,10}$";
        if (Pattern.matches(regx, name)) {
            if (userService.testUserNameIsReg(name)) {
                return Result.status(false).code(415).msg("昵称已被注册，请换一个");
            } else {
                return Result.status(true).code(200).msg("可以注册");
            }
        } else {
            return Result.status(false).code(415).msg("非法昵称");
        }
    }

    //修改密码
    @PostMapping("/changePassword")
    @NeedLogin
    public Result changePassWord(String oldPwd, String newPwd, HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        Result result = null;

        //如果两个字符串都不为空
        if (!EmptyUtil.checkUpStringIsNull(oldPwd) && !EmptyUtil.checkUpStringIsNull(newPwd)) {
            result = userService.changePwd(oldPwd, newPwd, userId);
        } else {
            result = Result.status(false).code(403).msg("请填写完整");
        }

        return result;
    }


    //更改背景图片
    @PostMapping("/changeBgImg")
    @NeedLogin
    public Result changeBgImg(@RequestParam("img") MultipartFile ufile, HttpSession session) {
        //获取id
        int userId = (int) session.getAttribute("userId");
        Result result = userService.upLoadImage(ufile, userId);

        if (result.isResultStatus()) {
            String path = (String) result.getData();
            if (userService.changeBackgroundImg(userId, path)) {
                return Result.status(true).msg("更改成功");
            } else {
                return Result.status(false).code(500).msg("更新失败");
            }
        } else {
            return result;
        }
    }


    //获取自己的所有信息
    @GetMapping("/aboutMe")
    @NeedLogin
    @NeedRole
    public Result aboutMe(HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        return userService.getUserInfoById(userId);
    }

    @GetMapping("/info/{userId}")
    @NeedLogin
    public Result userInfo(@PathVariable("userId") int userId) {
        return userService.getUserInfoById(userId);
    }

    //获取行业列表
    @GetMapping("/industry")
    public Result industryInfo() {
        return userService.getIndustrys();
    }

    //退出登录
    @GetMapping("/outlogin")
    public Result outLogin(HttpSession session) {
        session.removeAttribute(String.valueOf(session.getAttribute("userId")));
        session.invalidate();
        return Result.status(true).body("退出登录");
    }

}
