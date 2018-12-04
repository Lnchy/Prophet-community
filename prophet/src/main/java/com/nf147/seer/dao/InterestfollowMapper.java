package com.nf147.seer.dao;

import com.nf147.seer.entity.Interestfollow;
import java.util.List;

public interface InterestfollowMapper {
    int deleteByPrimaryKey(Integer followId);

    int insert(Interestfollow record);

    Interestfollow selectByPrimaryKey(Integer followId);

    List<Interestfollow> selectAll();

    int updateByPrimaryKey(Interestfollow record);
}