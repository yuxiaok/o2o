package com.o2o.dto;

import java.util.List;

import com.o2o.entity.ShopAuthMap;
import com.o2o.enums.ShopAuthMapStateEnum;

public class ShopAuthMapExecution {
    private int state;
    private String stateInfo;
    private Integer count;
    private ShopAuthMap shopAuthMap;
    private List<ShopAuthMap> shopAuthMapList;

    public ShopAuthMapExecution() {}

    public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum) {
        this.state = stateEnum.getCode();
        this.stateInfo = stateEnum.getMsg();
    }

    public ShopAuthMapExecution(ShopAuthMapStateEnum stateEnum, ShopAuthMap shopAuthMap) {
        this.shopAuthMap = shopAuthMap;
        this.state = stateEnum.getCode();
        this.stateInfo = stateEnum.getMsg();
    }

    public ShopAuthMapExecution(int count, List<ShopAuthMap> shopAuthMapList) {
        this.shopAuthMapList = shopAuthMapList;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ShopAuthMap getShopAuthMap() {
        return shopAuthMap;
    }

    public void setShopAuthMap(ShopAuthMap shopAuthMap) {
        this.shopAuthMap = shopAuthMap;
    }

    public List<ShopAuthMap> getShopAuthMapList() {
        return shopAuthMapList;
    }

    public void setShopAuthMapList(List<ShopAuthMap> shopAuthMapList) {
        this.shopAuthMapList = shopAuthMapList;
    }
}
