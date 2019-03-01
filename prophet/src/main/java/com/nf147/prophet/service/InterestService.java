package com.nf147.prophet.service;

import com.nf147.prophet.entity.Interest;
import com.nf147.prophet.entity.Interestfollow;
import com.nf147.prophet.util.Result;

import java.util.List;

public interface InterestService {

    //获取所有兴趣分类
    Result getInterests();

    //根据Id获取分类
    Result getInterestById(int id);

    //根据名称获取分类
    Result getInterestByName(String name);

    //用户关注一个兴趣
    Result userFollowInterest(int userId, int InterestId);

    //用户取消关注一个兴趣
    Result userNotFollowInterest(int userId, int InterestId);


    //管理员操作
    //更改兴趣名称
    Result changeInterestName(int interestId, String nameNew);

    //更改兴趣描述
    Result changeInterestDescribe(int interestId, String Describe);

    //更改兴趣图片
    Result changeInterestImage(int interestId, String imagePath);


    //判断一个用户是否关注某个分类
    boolean userFollowInterestWhether(int userId, int InterestId);

    //判断某个分类是否存在
    boolean followInterestWhether(int interestId);

    //分类的关注量
    Result usersFollowInterestNumbers(int interestId);

    //获取某个用户关注的分类
    List<Interest> getUserFollowInterest(int userId);

    //获取推荐的分类
    List<Interest> getRecommendInterest();
}
