package com.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.o2o.entity.LocalAuth;

/**
 * Created by yukai
 * 2019/4/14 10:22
 */
public interface LocalAuthDao {
    /**
     * 通过账号和密码查询对应信息，登录用
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

    /**
     * 通过用户id查询对应localAuth
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(long userId);

    /**
     * 添加平台账号
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 通过userId,username,password更改密码
     * @return
     */
    int updateLocalAuth(@Param("userId") long userId, @Param("username") String username, @Param("password") String password,
                        @Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEditTime);

}
