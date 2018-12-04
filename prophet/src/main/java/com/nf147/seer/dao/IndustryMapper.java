package com.nf147.seer.dao;

import com.nf147.seer.entity.Industry;
import java.util.List;

public interface IndustryMapper {
    int deleteByPrimaryKey(Integer industryId);

    int insert(Industry record);

    Industry selectByPrimaryKey(Integer industryId);

    List<Industry> selectAll();

    int updateByPrimaryKey(Industry record);
}