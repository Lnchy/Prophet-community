package com.nf147.prophet.service;

import com.nf147.prophet.entity.Issue;
import com.nf147.prophet.util.Result;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IssueService {

    /**
     * 获取某个分类下的所有问题（分页）
     * @param interestId    分类id
     * @param pageSize      每页显示多少条记录
     * @param pageCode      当前页
     * @return              返回结果
     */
    Result getAllIssueByInterestId(int interestId, int pageSize, int pageCode);

    /**
     * 获取某个分类下的所有回答和问题（分页）
     * @param interestId    分类id
     * @param pageSize      每页显示多少条记录
     * @param pageCode      当前页
     * @return              返回结果
     */
    Result getAllReplyByInterestId(int interestId, int pageSize, int pageCode);

    /**
     * 获取用户感兴趣的分类
     * @param userId    用户id
     * @return          随机条数
     */
    List<Issue> getLoveIssueByUserId(int userId);

    /**
     * 获取一个问题的详细信息
     * @param issueId   问题id
     * @return          返回问题的信息
     */
    Issue getIssueByIssueId(int issueId);

    /**
     * 返回精华问题
     * @param interestId    分类id
     * @param pageSize      每页显示多少记录
     * @param pageCode      当前页数
     * @return              返回精华问题
     */
    Result getAllEssenceByInterestId(int interestId, int pageSize, int pageCode);


    /**
     * 创建一个新的问题
     * @param issueInterestId   分类id
     * @param issueTitle        问题标题
     * @param issueContent      问题内容
     * @param userId            用户id
     * @return                  返回结果
     */
    Result createNewIssue(int issueInterestId, String issueTitle, String issueContent, int userId);
}
