package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Replypraise;
import java.util.List;

public interface ReplypraiseMapper {
    int deleteByPrimaryKey(Integer praiseId);

    int insert(Replypraise record);

    Replypraise selectByPrimaryKey(Integer praiseId);

    List<Replypraise> selectAll();

    int updateByPrimaryKey(Replypraise record);
}