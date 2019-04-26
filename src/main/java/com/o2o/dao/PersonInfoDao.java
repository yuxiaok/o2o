package com.o2o.dao;

import com.o2o.entity.PersonInfo;

public interface PersonInfoDao {
	
	/**
	 * 通过用户id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	
	/**
	 * 添加用户信息
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);
}
