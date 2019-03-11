package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {

    /**
     * 根据问题id来获取回复
     * @param issueId   问题id
     * @param star      开始条数
     * @param end       取出的条舒
     * @return          返回结果
     */
    List<Reply> getReplysByIssueId(
            @Param("issueId")int issueId,
            @Param("star") int star,
            @Param("end") int end
            );

    /**
     * 获取回答的总条数
     * @param issueId   问题ID
     * @return          总条数
     */
    int getReplysByIssueIdCount(@Param("issueId")int issueId);

    /**
     * 获取一个问题的总回答数量，除去折叠的回答
     * @param issueId   问题id
     * @return          返回总数
     */
    int getCount(@Param("issueId") int issueId);

    /**
     * 获取一条回答
     * @param replyId   回答id
     * @return          返回回答
     */
    Reply selectReplyByReplyId(@Param("replyId") int replyId);

    /**
     * 获取回复的回复
     * @param replyId   回复id
     * @param star      开始条
     * @param end       偏移条数
     * @return          回复的回复
     */
    List<Reply> getReplyReply(
            @Param("replyId") int replyId,
            @Param("star") int star,
            @Param("end") int end
            );

    /**
     * 获取回复的回复总条数
     * @param replyId   回复id
     * @return          总条数
     */
    int getReplyReplyCount(@Param("replyId") int replyId);

    /**
     * 增加一条记录
     * @param reply
     * @return
     */
    int insertReply(Reply reply);

    /**
     * 点赞加一
     * @param replyId
     * @return
     */
    int praiseAddOne(@Param("replyId") int replyId);

    /**
     * 点赞减一
     * @param replyId
     * @return
     */
    int praiseNotOne(@Param("replyId") int replyId);
}