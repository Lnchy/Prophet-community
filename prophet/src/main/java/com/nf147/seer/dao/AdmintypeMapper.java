package com.nf147.seer.dao;

import com.nf147.seer.entity.Admintype;
import java.util.List;

public interface AdmintypeMapper {
    int deleteByPrimaryKey(Integer adminTypeId);

    int insert(Admintype record);

    Admintype selectByPrimaryKey(Integer adminTypeId);

    List<Admintype> selectAll();

    int updateByPrimaryKey(Admintype record);
}