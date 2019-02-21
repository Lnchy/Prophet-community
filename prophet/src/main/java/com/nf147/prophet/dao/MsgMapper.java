package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Msg;
import java.util.List;

public interface MsgMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(Msg record);

    Msg selectByPrimaryKey(Integer msgId);

    List<Msg> selectAll();

    int updateByPrimaryKey(Msg record);
}