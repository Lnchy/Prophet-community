package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.ReplyMapper;
import com.nf147.prophet.dao.ReplypraiseMapper;
import com.nf147.prophet.entity.Replypraise;
import com.nf147.prophet.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraiseServiceImpl implements PraiseService {
    @Autowired
    ReplypraiseMapper praiseMapper;

    @Autowired
    ReplyMapper replyMapper;

    /**
     * 点赞
     *
     * @param replyId 回复id
     * @param userId  用户id
     * @return 返回点赞是否成功
     */
    @Override
    public boolean agreeWith(int replyId, int userId) {
        if (!whetherAgreeWith(replyId,userId)) {
            Replypraise replypraise = new Replypraise();
            replypraise.setPraiseReplyId(replyId);
            replypraise.setPraiseUserId(userId);
            //回复表中减一
            replyMapper.praiseAddOne(replyId);
            return praiseMapper.insert(replypraise) > 0;
        } else {
            return true;
        }

    }

    /**
     * 取消赞同
     *
     * @param replyId 回复id
     * @param userId  用户id
     * @return 取消赞是否成功
     */
    @Override
    public boolean cancelApproval(int replyId, int userId) {
        if (whetherAgreeWith(replyId,userId)) {
            //回复表中加一
            replyMapper.praiseNotOne(replyId);
            return praiseMapper.deletePraise(replyId, userId) > 0;
        } else {
            return true;
        }
    }

    /**
     * 是否有赞同
     *
     * @param replyId 回复id
     * @param userId  用户id
     * @return 是否有赞同
     */
    @Override
    public boolean whetherAgreeWith(int replyId, int userId) {
        return praiseMapper.selectPraise(replyId, userId) > 0;
    }
}
