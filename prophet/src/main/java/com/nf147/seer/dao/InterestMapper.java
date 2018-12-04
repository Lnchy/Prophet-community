package com.nf147.seer.dao;

import com.nf147.seer.entity.Interest;
import java.util.List;

public interface InterestMapper {
    int deleteByPrimaryKey(Integer interestId);

    int insert(Interest record);

    Interest selectByPrimaryKey(Integer interestId);

    List<Interest> selectAll();

    int updateByPrimaryKey(Interest record);
}