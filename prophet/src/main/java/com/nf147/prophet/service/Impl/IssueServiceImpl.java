package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.IssueMapper;
import com.nf147.prophet.entity.Issue;
import com.nf147.prophet.service.IssueService;
import com.nf147.prophet.util.PageHelp;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    IssueMapper issueMapper;

    /**
     * 获取某个分类下的所有问题（分页）
     * @param interestId    分类id
     * @param pageSize      每页显示多少条记录
     * @param pageCode      当前页
     * @return              返回结果
     */
    @Override
    public Result getAllIssueByInterestId(int interestId, int pageSize, int pageCode) {
        //总共条数
        int count = issueMapper.getCount(interestId);
        PageHelp page = PageHelp.total(count, pageSize, pageCode);

        int star = pageSize * pageCode - pageSize;
        int end = pageSize;
        List<Issue> issues = issueMapper.selectAllIssueByinterestId(interestId, star, end);

        return Result.status(true).body(issues).page(page);
    }

    /**
     * 获取用户感兴趣的分类
     * @param userId    用户id
     * @return          随机条数
     */
    @Override
    public List<Issue> getLoveIssueByUserId(int userId) {
        return issueMapper.selectUserLove(userId);
    }

    /**
     * 获取一个问题的详细信息
     * @param issueId   问题id
     * @return          返回问题的信息
     */
    @Override
    public Issue getIssueByIssueId(int issueId) {
        return issueMapper.selectIssueByIssueId(issueId);
    }

    /**
     * 返回精华问题
     * @param interestId    分类id
     * @param pageSize      每页显示多少记录
     * @param pageCode      当前页数
     * @return              返回精华问题
     */
    @Override
    public Result getAllEssenceByInterestId(int interestId, int pageSize, int pageCode) {
        return null;
    }
}
