package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Replypraise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplypraiseMapper {

    int insert(Replypraise record);

    int deletePraise(@Param("replyId") int replyId, @Param("userId") int userId);

    int selectPraise(@Param("replyId") int replyId, @Param("userId") int userId);
}