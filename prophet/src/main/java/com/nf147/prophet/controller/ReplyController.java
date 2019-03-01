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
import java.util.List;

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

    //获取回复列表
    @GetMapping("/info/{issueId}/{pageCode}")
    @NeedLogin
    public Result getReplyByIssue(@PathVariable("issueId") int issueId, @PathVariable("pageCode") int pageCode) {
        Result result = null;
        if (pageCode > 0) {
            result = replyService.getReplysByIssueId(issueId, pageCode);
        }

        return result;
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

}
