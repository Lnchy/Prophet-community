package com.nf147.seer.dao;

import com.nf147.seer.entity.Imgpath;
import java.util.List;

public interface ImgpathMapper {
    int deleteByPrimaryKey(Integer imgId);

    int insert(Imgpath record);

    Imgpath selectByPrimaryKey(Integer imgId);

    List<Imgpath> selectAll();

    int updateByPrimaryKey(Imgpath record);
}