package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Interest;

import java.util.List;

public interface InterestMapper {
    int deleteByPrimaryKey(Integer interestId);

    int insert(Interest record);

    Interest selectByPrimaryKey(Integer interestId);

    List<Interest> selectAll();

    int updateByPrimaryKey(Interest record);
}