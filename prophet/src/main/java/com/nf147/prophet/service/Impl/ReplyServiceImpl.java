package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.IssueMapper;
import com.nf147.prophet.dao.ReplyMapper;
import com.nf147.prophet.entity.Reply;
import com.nf147.prophet.service.ReplyService;
import com.nf147.prophet.util.PageHelp;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    IssueMapper issueMapper;

    /**
     * 根据问题ID来获取回复，根据页码
     *
     * @param issueId  问题id
     * @param pageCode 当前页码
     * @return 得到某一页的回复
     */
    @Override
    public Result getReplysByIssueId(int issueId, int pageCode) {
        int pageSize = 10;
        int star = pageCode * pageSize - pageSize;
        List<Reply> replys = replyMapper.getReplysByIssueId(issueId, star, pageSize);
        int count = replyMapper.getReplysByIssueIdCount(issueId);
        PageHelp total = PageHelp.total(count, pageSize, pageCode);

        return Result.status(true).body(replys).page(total);
    }

    /**
     * 根据回复id获取相关信息
     *
     * @param replyId 回复id
     * @return 返回回答信息
     */
    @Override
    public Result getReplyByReplyId(int replyId) {
        Reply reply = replyMapper.selectReplyByReplyId(replyId);
        Result result = null;

        if (reply == null) {
            result = Result.status(false).code(404).msg("没有此回答");
        } else {
            if (reply.getReplyInvalid() == 1) {
                result = Result.status(false).code(415).msg("该回答已被折叠");
            } else {
                result = Result.status(true).body(reply);
            }
        }
        return result;
    }

    /**
     * 获取回复的回复
     *
     * @param replyId  回复ID
     * @param pageCode 页码
     * @return 获取回复的回复
     */
    @Override
    public Result getReplyReply(int replyId, int pageCode) {
        int pageSize = 10;
        int star = pageCode * pageSize - pageSize;
        int count = replyMapper.getReplyReplyCount(replyId);
        List<Reply> replyReply = replyMapper.getReplyReply(replyId, star, pageSize);
        PageHelp total = PageHelp.total(count, pageSize, pageCode);
        return Result.status(true).body(replyReply).page(total);
    }

    /**
     * 添加一条回复
     *
     * @param issueId 问题id
     * @param content 内容
     * @param userId  用户id
     * @return 返回结果
     */
    @Override
    public Result addReply(int issueId, String content, int userId) {
        Reply reply = new Reply();
        reply.setReplyIssueId(issueId);
        reply.setReplyContent(content);
        reply.setReplyUserId(userId);
        int reply1 = replyMapper.insertReply(reply);
        //添加回复的时候在问题的回复数量上加一
        if (reply1 > 0) {
            issueMapper.updateIssueReplyByIssueId(issueId);
        }

        return Result.status(reply1 > 0).body(reply.getReplyId());
    }

    /**
     * 添加一条回答的回复
     *
     * @param issueId 问题id
     * @param content 回复内容
     * @param userId  用户id
     * @param replyId 回答id
     * @return 返回结果
     */
    @Override
    public Result addReplyReply(int issueId, String content, int userId, int replyId) {
        Reply reply = new Reply();
        reply.setReplyIssueId(issueId);
        reply.setReplyContent(content);
        reply.setReplyUserId(userId);
        reply.setReplyReplyId(replyId);
        int reply1 = replyMapper.insertReply(reply);


        return Result.status(reply1 > 0).body(reply.getReplyId());
    }


}
