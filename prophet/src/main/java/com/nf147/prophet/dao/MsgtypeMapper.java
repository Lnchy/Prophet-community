package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Msgtype;
import java.util.List;

public interface MsgtypeMapper {
    int deleteByPrimaryKey(Integer msgTypeId);

    int insert(Msgtype record);

    Msgtype selectByPrimaryKey(Integer msgTypeId);

    List<Msgtype> selectAll();

    int updateByPrimaryKey(Msgtype record);
}