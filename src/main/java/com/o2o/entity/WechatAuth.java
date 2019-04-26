package com.o2o.entity;

import java.util.Date;

/**
 * @Author yukai
 * @Date 2018年7月12日 微信账号
 */
public class WechatAuth {
    /**
     * 微信账号id
     */
    private Long wechatAuthId;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 外键
     */
    private PersonInfo personInfo;

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    @Override
    public String toString() {
        return "WechatAuth [wechatAuthId=" + wechatAuthId + ", openId=" + openId + ", createTime=" + createTime
            + ", personInfo=" + personInfo + "]";
    }

}
