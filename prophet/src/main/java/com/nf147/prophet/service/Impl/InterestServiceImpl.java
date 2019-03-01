package com.nf147.prophet.service.Impl;

import com.nf147.prophet.dao.InterestMapper;
import com.nf147.prophet.dao.InterestfollowMapper;
import com.nf147.prophet.entity.Interest;
import com.nf147.prophet.entity.Interestfollow;
import com.nf147.prophet.service.InterestService;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {
    //分类列表信息
    @Autowired
    private InterestMapper interestMapper;

    @Autowired
    private InterestfollowMapper interestfollowMapper;

    //获取所有兴趣分类
    @Override
    public Result getInterests() {
        try {
            List<Interest> interests = interestMapper.selectAll();

            if (interests == null) {
                return Result.status(false).code(404).msg("没有兴趣");
            } else {
                return Result.status(true).body(interests);
            }
        } catch (Exception ex) {
            return Result.status(false).msg(ex.getMessage());
        }


    }

    //根据Id获取分类
    @Override
    public Result getInterestById(int id) {
        try {
            Interest interest = interestMapper.selectByPrimaryKey(id);
            return Result.status(true).code(200).body(interest);
        } catch (Exception e) {
            return Result.status(false).code(500).msg(e.getMessage());
        }
    }

    //根据名称获取分类
    @Override
    public Result getInterestByName(String name) {
        return null;
    }

    //用户关注一个兴趣
    @Override
    public Result userFollowInterest(int userId, int interestId) {
        try {
            if (followInterestWhether(interestId)) {
                if (!userFollowInterestWhether(userId, interestId)) {
                    Interestfollow interestfollow = new Interestfollow();
                    interestfollow.setFollowUserId(userId);
                    interestfollow.setFollowInterestId(interestId);
                    if (interestfollowMapper.insert(interestfollow) > 0) {
                        return Result.status(true).msg("关注成功");
                    } else {
                        return Result.status(false).code(500).msg("关注失败");
                    }
                } else {
                    return Result.status(false).code(403).msg("您已经关注过了");
                }
            } else {
                return Result.status(false).code(403).msg("该分类不存在");
            }

        } catch (Exception e) {
            return Result.status(false).code(500).msg(e.getMessage());
        }

    }

    //用户取消关注一个兴趣
    @Override
    public Result userNotFollowInterest(int userId, int interestId) {
        try {
            if (interestfollowMapper.deleteByInterestIdAndUserId(interestId, userId) > 0) {
                return Result.status(true).msg("取消关注成功");
            } else {
                return Result.status(false).code(404).msg("取消失败");
            }
        } catch (Exception e) {
            return Result.status(false).code(500).msg(e.getMessage());
        }

    }

    //管理员操作
    //更改兴趣名称
    @Override
    public Result changeInterestName(int interestId, String nameNew) {
        return null;
    }

    //更改兴趣描述
    @Override
    public Result changeInterestDescribe(int interestId, String Describe) {
        return null;
    }

    //更改兴趣图片
    @Override
    public Result changeInterestImage(int interestId, String imagePath) {
        return null;
    }

    //返回某个用户是否关注了某个兴趣
    @Override
    public boolean userFollowInterestWhether(int userId, int interestId) {
        Interestfollow interestfollow = interestfollowMapper.userWhetherFollowInterest(interestId, userId);
        return !(interestfollow == null);
    }

    //判断一个分类是否存在
    @Override
    public boolean followInterestWhether(int interestId) {
        return !(interestMapper.selectByPrimaryKey(interestId) == null);
    }

    //获取分类的关注量
    @Override
    public Result usersFollowInterestNumbers(int interestId) {
        try {
            if (followInterestWhether(interestId)) {
                int i = interestfollowMapper.selectInterestFollowNumber(interestId);
                return Result.status(true).body(i);

            } else {
                return Result.status(false).code(404).msg("分类不存在");
            }
        } catch (Exception e) {
            return Result.status(false).code(500).msg(e.getMessage());
        }

    }

    //获取用户关注的分类
    @Override
    public List<Interest> getUserFollowInterest(int userId) {
        List<Interestfollow> interestfollows = interestfollowMapper.selectUserFollows(userId);
        List<Integer> ids = new ArrayList<>();
        for (Interestfollow item : interestfollows) {
            ids.add(item.getFollowInterestId());
        }
        if (ids.size() == 0) {
            return null;
        } else {
            return interestMapper.selectInterestByArray(ids);
        }
    }

    //获取随机推荐的分类
    @Override
    public List<Interest> getRecommendInterest() {
        return interestMapper.selectRand();
    }


}
