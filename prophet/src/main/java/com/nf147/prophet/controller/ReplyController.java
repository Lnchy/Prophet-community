package com.nf147.prophet.controller;

import com.nf147.prophet.entity.Reply;
import com.nf147.prophet.service.Impl.ReplyServiceImpl;
import com.nf147.prophet.service.Impl.UserServiceImpl;
import com.nf147.prophet.util.Result;
import com.nf147.prophet.util.validate.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    ReplyServiceImpl replyService;

    //回复上传图片
    @PostMapping("/upImage")
    @NeedLogin
    public Result upImage(@RequestParam("img") MultipartFile ufile, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return userService.upLoadImage(ufile, userId);
    }

    //获取回答列表
    @GetMapping("/info/{issueId}/{pageCode}")
    @NeedLogin
    public Result getReplyByIssue(@PathVariable("issueId") int issueId, @PathVariable("pageCode") int pageCode) {
        Result result = null;
        if (pageCode > 0) {
            result = replyService.getReplysByIssueId(issueId, pageCode);
        }

        return result;
    }

    //获取一条回答
    @GetMapping("/info/{replyId}")
    @NeedLogin
    public Result getReplyByReplyInfo(@PathVariable("replyId") int replyId) {
        return replyService.getReplyByReplyId(replyId);
    }

    //获取回复的回复
    @GetMapping("/reply/{replyId}/{pageCode}")
    @NeedLogin
    public Result getReplyReply(@PathVariable("replyId") int replyId, @PathVariable("pageCode") int pageCode) {
        return replyService.getReplyReply(replyId, pageCode);
    }

    //添加一条回复
    @PostMapping("/add")
    @NeedLogin
    public Result addReply(@RequestParam("issueId") int issueId,
                           @RequestParam("content") String content,
                           HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return replyService.addReply(issueId, content, userId);
    }

    //添加一条回答的回复
    @PostMapping("/reply/add")
    @NeedLogin
    public Result addReplyReply(@RequestParam("issueId") int issueId,
                                @RequestParam("content") String content,
                                @RequestParam("replyId") int replyId,
                                HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        return replyService.addReplyReply(issueId, content, userId, replyId);
    }

}
