package com.nf147.prophet.service;

public interface PraiseService {

    /**
     * 点赞
     * @param replyId   回复id
     * @param userId    用户id
     * @return          返回点赞是否成功
     */
    boolean agreeWith (int replyId, int userId);

    /**
     * 取消赞同
     * @param replyId   回复id
     * @param userId    用户id
     * @return          取消赞是否成功
     */
    boolean cancelApproval (int replyId, int userId);


    /**
     * 是否有赞同
     * @param replyId   回复id
     * @param userId    用户id
     * @return          是否有赞同
     */
    boolean whetherAgreeWith (int replyId, int userId);
}
