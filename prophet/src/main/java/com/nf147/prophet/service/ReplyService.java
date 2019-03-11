package com.nf147.prophet.service;

import com.nf147.prophet.util.Result;


public interface ReplyService {

    /**
     * 根据问题ID来获取回复，根据页码
     *
     * @param issueId  问题id
     * @param pageCode 当前页码
     * @return 得到某一页的回复
     */
    Result getReplysByIssueId(int issueId, int pageCode);

    /**
     * 根据回复id获取相关信息
     * @param replyId   回复id
     * @return          返回回答信息
     */
    Result getReplyByReplyId(int replyId);

    /**
     * 获取回复的回复
     *
     * @param replyId  回复ID
     * @param pageCode 页码
     * @return 获取回复的回复
     */
    Result getReplyReply(int replyId, int pageCode);

    /**
     * 添加一条回复
     *
     * @param issueId 问题id
     * @param content 内容
     * @param userId  用户id
     * @return 返回结果
     */
    Result addReply(int issueId, String content, int userId);

    /**
     * 添加一条回答的回复
     * @param issueId   问题id
     * @param content   回复内容
     * @param userId    用户id
     * @param replyId   回答id
     * @return          返回结果
     */
    Result addReplyReply(int issueId, String content, int userId, int replyId);
}
