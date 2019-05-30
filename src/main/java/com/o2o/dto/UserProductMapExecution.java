package com.o2o.dto;

import java.util.List;

import com.o2o.entity.UserProductMap;
import com.o2o.enums.UserProductMapStateEnum;

public class UserProductMapExecution {

    private int state;
    private String stateInfo;
    private int count;
    private UserProductMap userProductMap;
    private List<UserProductMap> userProductMapList;

    public UserProductMapExecution() {}

    public UserProductMapExecution(UserProductMapStateEnum userProductMapStateEnum) {
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
    }

    public UserProductMapExecution(UserProductMapStateEnum userProductMapStateEnum, UserProductMap userProductMap) {
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
        this.userProductMap = userProductMap;
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

    public UserProductMap getUserProductMap() {
        return userProductMap;
    }

    public void setUserProductMap(UserProductMap userProductMap) {
        this.userProductMap = userProductMap;
    }

    public List<UserProductMap> getUserProductMapList() {
        return userProductMapList;
    }

    public void setUserProductMapList(List<UserProductMap> userProductMapList) {
        this.userProductMapList = userProductMapList;
    }
}
