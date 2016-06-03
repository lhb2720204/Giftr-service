package com.akhahaha.giftr.service.data.models;

import com.akhahaha.giftr.service.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.List;

/**
 * User model
 * Created by Alan on 4/29/2016.
 */
public class User {
    @JsonView(View.Summary.class)
    private Integer id;
    @JsonView(View.Summary.class)
    private String username;
    @JsonView(View.Summary.class)
    private UserStatus status;
    @JsonView(View.Summary.class)
    private Date joinDate;
    @JsonView(View.Summary.class)
    private Date lastActive;
    @JsonView(View.Summary.class)
    private Gender gender;
    @JsonView(View.Summary.class)
    private String location;
    @JsonView(View.Summary.class)
    private GiftType giftType;
    @JsonView(View.Summary.class)
    private String interests;
    @JsonView(View.Summary.class)
    private Integer priceMin;
    @JsonView(View.Summary.class)
    private Integer priceMax;
    @JsonView(View.Detailed.class)
    private String email;
    @JsonView(View.Detailed.class)
    private List<Integer> matches;

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
        priceMin = 0;
        priceMax = 0;
    }

    public User(Integer id, String username, UserStatus status, Date joinDate, Date lastActive, Gender gender, String location,
                GiftType giftType, String interests, Integer priceMin, Integer priceMax, String email) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.joinDate = joinDate;
        this.lastActive = lastActive;
        this.gender = gender;
        this.location = location;
        this.giftType = giftType;
        this.interests = interests;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Integer> getMatches() {
        return matches;
    }

    public void setMatches(List<Integer> matches) {
        this.matches = matches;
    }
}
