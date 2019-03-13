package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Issue;
import com.nf147.prophet.entity.Ral;
import com.nf147.prophet.entity.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RalMapper {
    int deleteByPrimaryKey(Integer ralId);

    int insert(Ral record);

    Ral selectByPrimaryKey(Integer ralId);

    List<Ral> selectAll();

    int updateByPrimaryKey(Ral record);

    //查询首页推荐的内容
    List<Issue> selectIndex(@Param("star") int star, @Param("end") int end);

    //获取总数
    int selectRalCount();
}