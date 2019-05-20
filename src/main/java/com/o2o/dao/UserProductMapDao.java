package com.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.UserProductMap;

public interface UserProductMapDao {
    /**
     * 根据查询条件分页返回用户购买商品的记录列表
     * 
     * @param userProductCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserProductMap> queryUserProductMapList(@Param("userProductCondition") UserProductMap userProductCondition,
        @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 列表总数
     * 
     * @param userProductCondition
     * @return
     */
    int queryUserProductMapCount(@Param("userProductCondition") UserProductMap userProductCondition);

    /**
     * 增加
     * 
     * @param userProductMap
     * @return
     */
    int insertUserProductMap(UserProductMap userProductMap);
}
