package com.nf147.prophet.controller;

import com.nf147.prophet.service.Impl.PraiseServiceImpl;
import com.nf147.prophet.util.Result;
import com.nf147.prophet.util.validate.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/praise")
public class PraiseController {
    @Autowired
    PraiseServiceImpl praiseService;

    //点赞
    @GetMapping("/agreeWith/{replyId}")
    @NeedLogin
    public Result agreeWith(@PathVariable("replyId") int replyId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return Result.status(praiseService.agreeWith(replyId, userId));
    }

    //取消赞
    @GetMapping("/cancelApproval/{replyId}")
    @NeedLogin
    public Result cancelApproval(@PathVariable("replyId") int replyId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return Result.status(praiseService.cancelApproval(replyId, userId));
    }

    //判断是否有赞
    @GetMapping("/whetherAgreeWith/{replyId}")
    @NeedLogin
    public Result whetherAgreeWith(@PathVariable("replyId") int replyId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return Result.status(praiseService.whetherAgreeWith(replyId, userId));
    }
}
