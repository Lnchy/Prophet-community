package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Issue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueMapper {
    //根据分类获得问题列表
    List<Issue> selectAllIssueByinterestId(
            @Param("interestId") int interestId,
            @Param("starNum") int starNum,
            @Param("endNum") int endNum
    );

    //根据分类获取问题的回答
    List<Issue> selectAllReplyByInterestId(
            @Param("interestId") int interestId,
            @Param("starNum") int starNum,
            @Param("endNum") int endNum
    );

    //获取问题总数
    int getCount(@Param("interestId") int interestId);

    //获取用户喜欢的问题
    List<Issue> selectUserLove(@Param("userId")int userId);

    //获取一个问题的详情
    Issue selectIssueByIssueId(@Param("issueId") int issueId);

    //浏览数数量增加
    int updateIssueReadNumByIssueId(@Param("issueId") int issueId);

    //问题的回复加一
    int updateIssueReplyByIssueId(@Param("issueId") int issueId);

    //新增一个问题
    int createIssue(Issue issue);
}