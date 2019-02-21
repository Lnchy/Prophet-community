package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Industry;

import java.util.List;

public interface IndustryMapper {
    int deleteByPrimaryKey(Integer industryId);

    int insert(Industry record);

    Industry selectByPrimaryKey(Integer industryId);

    List<Industry> selectAll();

    int updateByPrimaryKey(Industry record);
}