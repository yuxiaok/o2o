package com.o2o.dao;

import java.util.List;

import com.o2o.entity.HeadLine;

public interface HeadLineDao {

    /**
     * 查询头条列表
     * 
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(HeadLine headLineCondition);
}
