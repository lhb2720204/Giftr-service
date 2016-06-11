package com.akhahaha.giftr.service.data.models;

import java.util.Date;

/**
 * Match model
 * Created by Alan on 4/29/2016.
 */
public class Match {
    private Integer id;
    private MatchStatus status;
    private Date created;
    private Date lastModified;
    private Integer priceMin;
    private Integer priceMax;
    private Integer user1ID;
    private Integer user2ID;
    private Integer user1Transaction;
    private Integer user2Transaction;
    private GiftType giftType;

	public Match() {
    }

    public Match(Integer user1ID, Integer user2ID) {
        this.status = MatchStatus.PENDING;
        Date currentDate = new Date();
        this.created = currentDate;
        this.lastModified = currentDate;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.priceMin = 0;
        this.priceMax = 0;
        this.giftType = GiftType.UNKNOWN;
    }

    public Match(Integer id, MatchStatus status, Date created, Date lastModified, Integer priceMin, Integer priceMax,
                 Integer user1ID, Integer user2ID, Integer user1Transaction, Integer user2Transaction, GiftType giftType) {
        this.id = id;
        this.status = status;
        this.created = created;
        this.lastModified = lastModified;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.user1Transaction = user1Transaction;
        this.user2Transaction = user2Transaction;
        this.giftType = giftType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
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

    public Integer getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(Integer user1ID) {
        this.user1ID = user1ID;
    }

    public Integer getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(Integer user2ID) {
        this.user2ID = user2ID;
    }

    public Integer getUser1Transaction() {
        return user1Transaction;
    }

    public void setUser1Transaction(Integer user1Transaction) {
        this.user1Transaction = user1Transaction;
    }

    public Integer getUser2Transaction() {
        return user2Transaction;
    }

    public void setUser2Transaction(Integer user2Transaction) {
        this.user2Transaction = user2Transaction;
    }
    
    public GiftType getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftType giftType) {
		this.giftType = giftType;
	}


}
