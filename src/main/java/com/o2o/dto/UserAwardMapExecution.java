package com.o2o.dto;

import java.util.List;

import com.o2o.entity.UserAwardMap;
import com.o2o.enums.UserAwardMapStateEnum;

public class UserAwardMapExecution {

    private int state;
    private String stateInfo;
    private int count;
    private UserAwardMap userAwardMap;
    private List<UserAwardMap> userAwardMapList;

    public UserAwardMapExecution() {}

    public UserAwardMapExecution(UserAwardMapStateEnum userAwardMapStateEnum) {
        this.state = userAwardMapStateEnum.getState();
        this.stateInfo = userAwardMapStateEnum.getStateInfo();
    }

    public UserAwardMapExecution(UserAwardMapStateEnum userAwardMapStateEnum, UserAwardMap userAwardMap) {
        this.state = userAwardMapStateEnum.getState();
        this.stateInfo = userAwardMapStateEnum.getStateInfo();
        this.userAwardMap = userAwardMap;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserAwardMap getUserAwardMap() {
        return userAwardMap;
    }

    public void setUserAwardMap(UserAwardMap userAwardMap) {
        this.userAwardMap = userAwardMap;
    }

    public List<UserAwardMap> getUserAwardMapList() {
        return userAwardMapList;
    }

    public void setUserAwardMapList(List<UserAwardMap> userAwardMapList) {
        this.userAwardMapList = userAwardMapList;
    }
}
