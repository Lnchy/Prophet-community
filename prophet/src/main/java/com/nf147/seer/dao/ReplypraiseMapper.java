package com.nf147.seer.dao;

import com.nf147.seer.entity.Replypraise;
import java.util.List;

public interface ReplypraiseMapper {
    int deleteByPrimaryKey(Integer praiseId);

    int insert(Replypraise record);

    Replypraise selectByPrimaryKey(Integer praiseId);

    List<Replypraise> selectAll();

    int updateByPrimaryKey(Replypraise record);
}