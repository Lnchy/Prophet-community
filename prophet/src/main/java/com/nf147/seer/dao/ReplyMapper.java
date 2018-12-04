package com.nf147.seer.dao;

import com.nf147.seer.entity.Reply;
import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Integer replyId);

    int insert(Reply record);

    Reply selectByPrimaryKey(Integer replyId);

    List<Reply> selectAll();

    int updateByPrimaryKey(Reply record);
}