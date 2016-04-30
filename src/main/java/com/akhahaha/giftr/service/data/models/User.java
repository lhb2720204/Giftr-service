package com.akhahaha.giftr.service.data.models;

import java.util.Date;

/**
 * User model
 * Created by Alan on 4/29/2016.
 */
public class User {
    private Integer id;
    private UserStatus status;
    private Date joinDate;
    private Date lastActive;
    private Gender gender;
    private String location;
    private GiftType giftType;
    private String interests;

    /**
     * Creates a new User with default properties.
     */
    public User() {
        status = UserStatus.ACTIVE;
        Date currentDate = new Date();
        joinDate = currentDate;
        lastActive = currentDate;
        gender = Gender.UNKNOWN;
        giftType = GiftType.UNKNOWN;
    }

    public User(Integer id, UserStatus status, Date joinDate, Date lastActive, Gender gender, String location,
                GiftType giftType, String interests) {
        this.id = id;
        this.status = status;
        this.joinDate = joinDate;
        this.lastActive = lastActive;
        this.gender = gender;
        this.location = location;
        this.giftType = giftType;
        this.interests = interests;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public void setGiftType(GiftType giftType) {
        this.giftType = giftType;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
