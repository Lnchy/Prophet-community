package com.nf147.seer.dao;

import com.nf147.seer.entity.Msg;
import java.util.List;

public interface MsgMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(Msg record);

    Msg selectByPrimaryKey(Integer msgId);

    List<Msg> selectAll();

    int updateByPrimaryKey(Msg record);
}