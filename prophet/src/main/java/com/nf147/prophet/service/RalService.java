package com.nf147.prophet.service;

import com.nf147.prophet.util.Result;

public interface RalService {

    /**
     * 获取所有的首页推荐
     * @return  返回推荐
     */
    Result getRalList(int pageCode, int pageSize);
}
