package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Issue;
import java.util.List;

public interface IssueMapper {
    int deleteByPrimaryKey(Integer issueId);

    int insert(Issue record);

    Issue selectByPrimaryKey(Integer issueId);

    List<Issue> selectAll();

    int updateByPrimaryKey(Issue record);
}