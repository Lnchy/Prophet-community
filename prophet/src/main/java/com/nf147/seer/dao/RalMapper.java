package com.nf147.seer.dao;

import com.nf147.seer.entity.Ral;
import java.util.List;

public interface RalMapper {
    int deleteByPrimaryKey(Integer ralId);

    int insert(Ral record);

    Ral selectByPrimaryKey(Integer ralId);

    List<Ral> selectAll();

    int updateByPrimaryKey(Ral record);
}