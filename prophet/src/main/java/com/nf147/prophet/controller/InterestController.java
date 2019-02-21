package com.nf147.prophet.controller;

import com.nf147.prophet.service.Impl.InterestServiceImpl;
import com.nf147.prophet.util.validate.NeedLogin;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/interest")
public class InterestController {
    @Autowired
    InterestServiceImpl interestService;

    //获取所有兴趣分类
    @GetMapping
    public Result getInterests() {
        return interestService.getInterests();
    }

    //关注一个分类
    @PostMapping("/follow")
    @NeedLogin
    public Result followInterest(HttpSession session, @RequestParam("id") int interestId) {
        int userId = (int) session.getAttribute("userId");
        return interestService.userFollowInterest(userId, interestId);
    }

    //取消关注一个分类
    @PostMapping("/NoFollow")
    @NeedLogin
    public Result noFollowInterest(HttpSession session, @RequestParam("id") int interestId) {
        int userId = (int) session.getAttribute("userId");
        return interestService.userNotFollowInterest(userId, interestId);
    }

    //获取某个关注分类有多少关注量
    @GetMapping("/follow/{id}")
    @NeedLogin
    public Result getInterestsNumber(@PathVariable("id") int interestId) {
        return interestService.usersFollowInterestNumbers(interestId);
    }

    //获取我关注的分类列表
    @GetMapping("/follow/user")
    @NeedLogin
    public Result getUserFollow(HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return Result.status(true).code(200).body(interestService.getUserFollowInterest(userId));
    }
}
