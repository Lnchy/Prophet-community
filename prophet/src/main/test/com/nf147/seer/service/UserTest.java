package com.nf147.seer.service;

import com.nf147.seer.entity.User;
import com.nf147.seer.util.MD5Util;
import com.nf147.seer.util.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class UserTest {

    @Autowired
    UserServiceImpl userService;

    @Test
    public void regTest() {
        User user = new User();
        LocalDate of = LocalDate.of(1997, 12, 16);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = of.atStartOfDay().atZone(zoneId).toInstant();
        Date date = Date.from(instant);
//        System.out.println(date);
        user.setUserName("包子");
        user.setUserPwd("123456789");
        user.setUserEmail("809324988@qq.com");
        user.setUserPortraitUrl("image/default.png");
        user.setUserBackgroundImg("image/default.png");
        user.setUserBirthday(date);
        Message reg = userService.reg(user);
        System.out.println(reg.getMessage());
    }

    @Test
    public void loginTest() {
        Message login = userService.login("809324988@qq.com", "12345678", null);

        System.out.println(login.getMessage());
    }

    @Test
    public void testMd5(){
        System.out.println(MD5Util.getMd5("123456789"));
    }

    @Test
    public void testDate() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.YEAR);
        System.out.println(i);
    }
}
