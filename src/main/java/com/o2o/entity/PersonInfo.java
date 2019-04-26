package com.o2o.entity;

import java.util.Date;

/**
 * @Author yukai
 * @Date 2018年7月12日 用户实体
 */
public class PersonInfo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户性别
     */
    private String profileImg;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户标识（是否禁止该用户使用该系统）
     */
    private Integer enableStatus;
    /**
     * 用户角色 1.顾客 2.商家 3.超级管理员
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改的时间
     */
    private Date lastEditTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "PersonInfo [userId=" + userId + ", userName=" + userName + ", profileImg=" + profileImg + ", email="
            + email + ", gender=" + gender + ", enableStatus=" + enableStatus + ", userType=" + userType
            + ", createTime=" + createTime + ", lastEditTime=" + lastEditTime + "]";
    }

}
