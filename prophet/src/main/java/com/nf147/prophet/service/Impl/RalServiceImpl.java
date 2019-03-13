package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.RalMapper;
import com.nf147.prophet.entity.Issue;
import com.nf147.prophet.entity.Reply;
import com.nf147.prophet.service.RalService;
import com.nf147.prophet.util.PageHelp;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RalServiceImpl implements RalService {
    @Autowired
    RalMapper ralMapper;

    /**
     * 获取所有的首页推荐
     * @return  返回推荐
     */
    @Override
    public Result getRalList(int pageCode, int pageSize) {

        int count = ralMapper.selectRalCount();
        PageHelp pageHelp = PageHelp.total(count, pageSize, pageCode);

        int star = pageSize * pageCode - pageSize;

        List<Issue> replies = ralMapper.selectIndex(star, pageSize);

        return Result.status(replies.size() > 0).body(replies).page(pageHelp);
    }
}
