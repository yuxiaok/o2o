package com.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2o.dao.LocalAuthDao;
import com.o2o.dto.LocalAuthExecution;
import com.o2o.entity.LocalAuth;
import com.o2o.enums.LocalAuthStateEnum;
import com.o2o.exceptions.LocalAuthOperationException;
import com.o2o.service.LocalAuthService;
import com.o2o.util.MD5;

/**
 * Created by yukai
 * 2019/4/14 14:26
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;


    @Override
    public LocalAuth getLocalAuthBuUsernameAndPwd(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(username,MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Transactional
    @Override
    public LocalAuthExecution bingLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
        //空值判断，传入的localAuth账号密码，用户信息特别是userId不能为空
        if (null==localAuth||null==localAuth.getPassword()||
                null==localAuth.getUserName()||null==localAuth.getPersonInfo()||
                null==localAuth.getPersonInfo().getUserId()){
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }

        //查询此用户是否已绑定过平台账号
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if (null != tempAuth){
            //如果绑定过，直接退出，以保证平台账号的唯一性
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }

        try {
            //如果之前没有绑定过平台账号，则创建一个平台账号与该用户绑定
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            //对密码进行MD加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int insertNum = localAuthDao.insertLocalAuth(localAuth);
            if (insertNum<1){
                throw new LocalAuthOperationException("账号绑定失败");
            }
            return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,localAuth);
        }catch (Exception e){
            throw new LocalAuthOperationException("insertLocalAuth error:"+e.getMessage());
        }
    }


    @Transactional
    @Override
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationException {
        //非空判断，判断传入的用户id，账号，新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if (null==userId||null == username || null==password||null==newPassword||password.equals(newPassword)){
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        try{
            //更新密码，并对新密码进行MD5加密
            int updateNum = localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
            if (updateNum<1){
                throw new LocalAuthOperationException("更新密码失败");
            }
            return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
        }catch (Exception e){
            throw new LocalAuthOperationException("更新密码失败"+e.toString());
        }
    }
}
