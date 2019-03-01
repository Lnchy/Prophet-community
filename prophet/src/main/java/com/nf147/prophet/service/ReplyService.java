package com.nf147.prophet.service;

import com.nf147.prophet.entity.Reply;
import com.nf147.prophet.util.Result;

import java.util.List;

public interface ReplyService {

    /**
     * 根据问题ID来获取回复，根据页码
     * @param issueId   问题id
     * @param pageCode  当前页码
     * @return          得到某一页的回复
     */
    Result getReplysByIssueId(int issueId, int pageCode);

    /**
     * 获取回复的回复
     * @param replyId   回复ID
     * @param pageCode  页码
     * @return          获取回复的回复
     */
    Result getReplyReply(int replyId, int pageCode);

    /**
     * 添加一条回复
     * @param issueId   问题id
     * @param content   内容
     * @param userId    用户id
     * @return          返回结果
     */
    Result addReply(int issueId, String content,int userId);
}
