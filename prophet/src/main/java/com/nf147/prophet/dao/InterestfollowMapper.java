package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Interestfollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterestfollowMapper {
    int deleteByPrimaryKey(Integer followId);

    int insert(Interestfollow record);

    Interestfollow selectByPrimaryKey(Integer followId);

    List<Interestfollow> selectAll();

    int updateByPrimaryKey(Interestfollow record);

    //取消关注一个兴趣
    int deleteByInterestIdAndUserId(@Param("interId") int interestId, @Param("userId") int userId);

    //获取一个用户关注的分类
    Interestfollow userWhetherFollowInterest(@Param("interId") int interestId, @Param("userId") int userId);

    //查询一个分类的关注量
    int selectInterestFollowNumber(@Param("interId") int interestId);

    //获取用户关注的分类
    List<Interestfollow> selectUserFollows(@Param("userId") int userId);
}