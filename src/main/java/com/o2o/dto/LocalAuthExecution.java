package com.o2o.dto;

import java.util.List;

import com.o2o.entity.LocalAuth;
import com.o2o.enums.LocalAuthStateEnum;

/**
 * Created by yukai
 * 2019/4/14 14:18
 */
public class LocalAuthExecution {
    private int state;
    private String stateInfo;
    private List<LocalAuth> list;
    private int count;
    private LocalAuth localAuth;

    public LocalAuthExecution() {
    }

    /**
     * 失败时调用的构造方法
     * @param stateEnum
     */
    public LocalAuthExecution(LocalAuthStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 店作成功时使用的构造器
     * @param stateEnum
     * @param
     */
    public LocalAuthExecution(LocalAuthStateEnum stateEnum, LocalAuth localAuth){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.localAuth = localAuth;
    }

    /**
     * 操作成功时使用的构造器(查询获取的店铺列表，返回给前端)
     * @param stateEnum
     * @param
     */
    public LocalAuthExecution(LocalAuthStateEnum stateEnum,List<LocalAuth> localAuthList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.list = localAuthList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<LocalAuth> getList() {
        return list;
    }

    public void setList(List<LocalAuth> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalAuth getLocalAuth() {
        return localAuth;
    }

    public void setLocalAuth(LocalAuth localAuth) {
        this.localAuth = localAuth;
    }
}
