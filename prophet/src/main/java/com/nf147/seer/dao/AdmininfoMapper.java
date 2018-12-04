package com.nf147.seer.dao;

import com.nf147.seer.entity.Admininfo;
import java.util.List;

public interface AdmininfoMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admininfo record);

    Admininfo selectByPrimaryKey(Integer adminId);

    List<Admininfo> selectAll();

    int updateByPrimaryKey(Admininfo record);
}