package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Reply;
import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Integer replyId);

    int insert(Reply record);

    Reply selectByPrimaryKey(Integer replyId);

    List<Reply> selectAll();

    int updateByPrimaryKey(Reply record);
}