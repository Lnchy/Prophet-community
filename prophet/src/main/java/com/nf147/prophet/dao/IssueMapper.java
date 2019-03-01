package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Issue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueMapper {
    List<Issue> selectAllIssueByinterestId(
            @Param("interestId") int interestId,
            @Param("starNum") int starNum,
            @Param("endNum") int endNum
    );

    int getCount(@Param("interestId") int interestId);

    List<Issue> selectUserLove(@Param("userId")int userId);

    //获取一个问题的详情
    Issue selectIssueByIssueId(@Param("issueId") int issueId);
}