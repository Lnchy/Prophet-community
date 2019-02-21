package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Ral;
import java.util.List;

public interface RalMapper {
    int deleteByPrimaryKey(Integer ralId);

    int insert(Ral record);

    Ral selectByPrimaryKey(Integer ralId);

    List<Ral> selectAll();

    int updateByPrimaryKey(Ral record);
}