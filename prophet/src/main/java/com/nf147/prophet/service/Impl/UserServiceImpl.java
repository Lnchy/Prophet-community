package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.ImgpathMapper;
import com.nf147.prophet.dao.IndustryMapper;
import com.nf147.prophet.dao.UserMapper;
import com.nf147.prophet.entity.Imgpath;
import com.nf147.prophet.entity.Industry;
import com.nf147.prophet.entity.Role;
import com.nf147.prophet.entity.User;
import com.nf147.prophet.service.UserService;
import com.nf147.prophet.util.MD5Util;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //用户
    @Autowired
    private UserMapper userMapper;

    //图片
    @Autowired
    private ImgpathMapper imgpathMapper;

    //行业
    @Autowired
    private IndustryMapper industryMapper;

    //权限
    @Autowired
    RoleRelatedServiceImpl roleService;

    //注册用户
    @Override
    public Result reg(User user) {

        Result result = null;

        if (testEmailIsReg(user.getUserEmail())){
            result = Result.status(false).msg("邮箱已被注册");
        } else if (testUserNameIsReg(user.getUserName())) {
            result = Result.status(false).msg("昵称已被注册");
        } else {
            //对密码进行加密
            String md5 = MD5Util.getMd5(user.getUserPwd());
            user.setUserPwd(md5);
            //设置默认头像和默认背景图
            user.setUserPortraitUrl("/image/default/portrait.png");
            user.setUserBackgroundImg("/image/default/portrait.png");
            //没有此邮箱，就可以注册
            if (userMapper.insert(user) > 0) {
                User user1 = userMapper.selectByEmail(user.getUserEmail());

                List<Role> allRoles = roleService.getAllRoles();
                for (Role role : allRoles) {
                    if (role.getName().equals("user")) {
                        roleService.giveUserRole(user1.getUserId(), role.getId());
                        return Result.status(true).msg("注册成功");
                    }
                }

                result = Result.status(true).msg("注册成功，请联系管理员激活");
            } else {
                result = Result.status(false).msg("注册失败");
            }
        }


        return result;
    }

    //用户登录
    @Override
    public Result login(String userEmail, String userPwd, HttpSession session) {
        Result result = null;
        User user = userMapper.selectByEmail(userEmail);
        if (user == null) {
            result = Result.status(false).code(404).msg("用户名不存在");
        } else if (!user.getUserPwd().equals(MD5Util.getMd5(userPwd))) {
            result = Result.status(false).code(406).msg("密码错误");
        } else {
            //存入用户ID
            session.setAttribute("userId", user.getUserId());
            result = Result.status(true).code(200).msg("登录成功");
        }
        return result;
    }

    //修改密码
    @Override
    public Result changePwd(String oldPwd, String newPwd, int userId) {
        Result result = null;

        User user = userMapper.selectByPrimaryKey(userId);

        if (user == null) {
            result = Result.status(false).code(404).msg("用户不存在");
        } else {
            if (user.getUserPwd().equals(MD5Util.getMd5(oldPwd))) {
                result = Result.status(false).code(406).msg("原密码错误");
            } else {
                int i = userMapper.changePwd(userId, MD5Util.getMd5(newPwd));
                if (i > 0) {
                    result = Result.status(true).code(200).msg("修改密码成功");
                } else {
                    result = Result.status(false).code(500).msg("修改失败");
                }
            }
        }

        return result;
    }

    //用户上传图片
    @Override
    public Result upLoadImage(MultipartFile ufile, HttpServletRequest req) {
        Result result = null;

        if (ufile.getSize() > 5242880) {
            result = Result.status(false).code(416).msg("图片过大");
        } else if (!(ufile.getContentType().contains("image/"))) {
            result = Result.status(false).code(403).msg("只允许上传图片");
        } else if (ufile.isEmpty()) {
            result = Result.status(false).code(400).msg("文件不能为空");
        } else {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String path = "/image/" + year + "/" + month + "/" + day + "/";
            File file = new File(req.getServletContext().getRealPath(path));

            try {
                //如果路径不存在就创建路径
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        throw new RuntimeException("创建文件夹失败");
                    }
                }


                //得到文件后缀
                String[] split = String.valueOf(ufile.getOriginalFilename()).split("[.]");
                path = path + cal.getTimeInMillis() + "." + split[1];
                file = new File(req.getServletContext().getRealPath(path));
                //保存文件
                ufile.transferTo(file);

                Imgpath imgpath = new Imgpath();

                imgpath.setImgPath(path);
                imgpath.setImgUserId((int) req.getSession().getAttribute("userId"));

                //保存到数据库
                if (imgpathMapper.insert(imgpath) > 0) {
                    result = Result.status(true).msg("上传成功").body(imgpath.getImgPath());
                } else {
                    result = Result.status(false).code(502).msg("网络过慢，请稍后重试");
                }


            } catch (IOException ex) {
                result = Result.status(false).code(500).msg("内部错误，请稍后重试");
            } catch (Exception ex) {
                result = Result.status(false).code(406).msg("上传失败，请稍后重试");
            }

        }

        return result;
    }

    //更新用户资料
    @Override
    public Result upUserInfo(User user) {
        try {
            if (userMapper.upUserInfo(user) > 0) {
                return Result.status(true).msg("更新成功");
            } else {
                return Result.status(false).code(500).msg("更新失败");
            }
        } catch (Exception ex) {
            return Result.status(false).code(500).msg("更新失败");
        }
    }

    //获取用户的所有资料
    @Override
    public Result getUserInfoById(int userId) {
        Result result = null;
        try {
            User user = userMapper.selectByPrimaryKey(userId);
            user.setUserPwd(null);
            result = Result.status(true).code(200).body(user);
        } catch (Exception ex) {
            result = Result.status(false).code(500).msg("网络错误");
        }


        return result;
    }

    //获得行业列表
    public Result getIndustrys() {
        try {
            List<Industry> industries = industryMapper.selectAll();
            return Result.status(true).body(industries);
        } catch (Exception ex){
            return Result.status(false).msg("网络错误请重试");
        }

    }

    //判断一个邮箱是否有注册过
    @Override
    public boolean testEmailIsReg(String email) {
        User user = userMapper.selectByEmail(email);

        return !(user == null);

    }

    //判断一个用户名是否存在
    @Override
    public boolean testUserNameIsReg(String userName) {

        User user = userMapper.selectByName(userName);

        return !(user == null);

    }

}
