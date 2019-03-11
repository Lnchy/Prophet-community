package com.nf147.prophet.controller;

import com.nf147.prophet.entity.Issue;
import com.nf147.prophet.service.Impl.IssueServiceImpl;
import com.nf147.prophet.util.Result;
import com.nf147.prophet.util.validate.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {
    @Autowired
    IssueServiceImpl issueService;

    //获取某个问题分类下的所有问题
    @GetMapping("/interest/{interestId}/{pageCode}")
    @NeedLogin
    public Result getIssueByInterestId(@PathVariable("interestId") int interestId, @PathVariable("pageCode") int pageCode) {
        if (interestId > 0 && pageCode > 0) {
            return issueService.getAllIssueByInterestId(interestId, 10, pageCode);
        } else {
            return Result.status(false).code(405).msg("请传入正确的页数和兴趣id");
        }
    }

    //获取某个分类下的所有精彩回答
    @GetMapping("/reply/{interestId}/{pageCode}")
    @NeedLogin
    public Result getReplyByInterestId(@PathVariable("interestId") int interestId, @PathVariable("pageCode") int pageCode) {
        if (interestId > 0 && pageCode > 0) {
            return issueService.getAllReplyByInterestId(interestId, 10, pageCode);
        } else {
            return Result.status(false).code(405).msg("请传入正确的页数和兴趣id");
        }
    }

    //获取用户喜欢的问题
    @GetMapping("/like")
    @NeedLogin
    public Result getUserLike(HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        List<Issue> loveIssueByUserId = issueService.getLoveIssueByUserId(userId);
        if (loveIssueByUserId.size() > 0) {
            return Result.status(true).body(loveIssueByUserId);
        } else {
            return Result.status(true).msg("空空如也，快去关注吧");
        }
    }

    // 获取问题详情
    @GetMapping("/info/{id}")
    @NeedLogin
    public Result getIssueInfo(@PathVariable("id") int issueId) {
        if (issueId > 0) {
            Issue issueByIssueId = issueService.getIssueByIssueId(issueId);

            if (issueByIssueId != null) {
                return Result.status(true).body(issueByIssueId);
            } else {
                return Result.status(false).msg("没有找到该问题");
            }
        } else {
            return Result.status(false).msg("请输入正确的问题ID");
        }
    }

    //新增一个问题
    @PostMapping("/add")
    @NeedLogin
    public Result createIssue(
            @RequestParam("issueInterestId") int issueInterestId,
            @RequestParam("issueTitle") String issueTitle,
            @RequestParam("issueContent") String issueContent,
            HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        return issueService.createNewIssue(issueInterestId, issueTitle, issueContent, userId);
    }

}

