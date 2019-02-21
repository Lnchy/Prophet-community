package com.nf147.prophet.dao;

import com.nf147.prophet.entity.Imgpath;

import java.util.List;

public interface ImgpathMapper {
    int deleteByPrimaryKey(Integer imgId);

    int insert(Imgpath record);

    Imgpath selectByPrimaryKey(Integer imgId);

    List<Imgpath> selectAll();

    int updateByPrimaryKey(Imgpath record);
}