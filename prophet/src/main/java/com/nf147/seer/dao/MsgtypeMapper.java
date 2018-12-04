package com.nf147.seer.dao;

import com.nf147.seer.entity.Msgtype;
import java.util.List;

public interface MsgtypeMapper {
    int deleteByPrimaryKey(Integer msgTypeId);

    int insert(Msgtype record);

    Msgtype selectByPrimaryKey(Integer msgTypeId);

    List<Msgtype> selectAll();

    int updateByPrimaryKey(Msgtype record);
}