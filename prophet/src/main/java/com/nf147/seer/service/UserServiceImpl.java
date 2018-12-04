package com.nf147.seer.service;

import com.nf147.seer.dao.ImgpathMapper;
import com.nf147.seer.dao.UserMapper;
import com.nf147.seer.entity.Imgpath;
import com.nf147.seer.entity.User;
import com.nf147.seer.util.MD5Util;
import com.nf147.seer.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ImgpathMapper imgpathMapper;

    //注册用户
    @Override
    public Message reg(User user) {

        Message result = null;
        User user1 = userMapper.selectByEmail(user.getUserEmail());

        if (user1 != null) {
            result = new Message(false, "邮箱已被注册");
        } else {
            //对密码进行加密
            String md5 = MD5Util.getMd5(user.getUserPwd());
            user.setUserPwd(md5);
            //设置默认头像和默认背景图
            user.setUserPortraitUrl("image/default.png");
            user.setUserBackgroundImg("image/default.png");
            //没有此邮箱，就可以注册
            if(userMapper.insert(user) > 0) {
                result = new Message(true,"注册成功");
            } else {
                result = new Message(false, "注册失败");
            }
        }


        return result;

    }

    //用户登录
    @Override
    public Message login(String userEmail, String userPwd, HttpSession session) {
        Message result = null;
        User user = userMapper.selectByEmail(userEmail);
        if (user == null) {
            result = new Message(false,"用户名不存在");
        } else if (!user.getUserPwd().equals(MD5Util.getMd5(userPwd))) {
            result = new Message(false,"密码错误");
        } else {
            //存入用户ID
            session.setAttribute("userId", user.getUserId());
            result = new Message(true, "登录成功");
        }
        return result;
    }

    @Override
    public Message changePwd(String oldPwd, String newPwd, int userId) {
        Message result = null;

        User user = userMapper.selectByPrimaryKey(userId);

        if (user == null) {
            result = new Message(false, "用户不存在");
        } else {
            if (user.getUserPwd().equals(MD5Util.getMd5(oldPwd))) {
                result = new Message(false, "原密码错误");
            } else {
                int i = userMapper.changePwd(userId, MD5Util.getMd5(newPwd));
                if (i > 0) {
                    result = new Message(true, "修改密码成功");
                } else {
                    result = new Message(true, "修改失败");
                }
            }
        }

        return result;
    }

    @Override
    public Message upLoadImage(MultipartFile ufile, HttpServletRequest req) {
        Message result = null;

        if (ufile.getSize() > 5242880) {
            result = new Message(false, "图片过大");
        } else if (!ufile.getContentType().contains("image/")) {
            result = new Message(false, "只允许上传图片");
        } else if (ufile.isEmpty()){
            result = new Message(false, "文件不能为空");
        } else {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            String path = "/image/" + year + "/" + month + "/" + day + "/";
            File file = new File(req.getServletContext().getRealPath(path));

            try {
                if (!file.exists()) {
                    //如果路径不存在就创建路径
                    file.mkdirs();
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
                    result = new Message(true, imgpath.getImgPath());
                } else {
                    result = new Message(false, "网络过慢，请稍后重试");
                }


            } catch (IOException ex) {
                result = new Message(false, "内部错误，请稍后重试");
            } catch (Exception ex) {
                result = new Message(false, "上传失败，请稍后重试");
            }

        }

        return result;
    }


}
