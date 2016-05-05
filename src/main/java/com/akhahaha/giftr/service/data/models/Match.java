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
    private String user1Transaction;
    private String user2Transaction;

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
    }

    public Match(Integer id, MatchStatus status, Date created, Date lastModified, Integer priceMin, Integer priceMax,
                 Integer user1ID, Integer user2ID, String user1Transaction, String user2Transaction) {
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

    public String getUser1Transaction() {
        return user1Transaction;
    }

    public void setUser1Transaction(String user1Transaction) {
        this.user1Transaction = user1Transaction;
    }

    public String getUser2Transaction() {
        return user2Transaction;
    }

    public void setUser2Transaction(String user2Transaction) {
        this.user2Transaction = user2Transaction;
    }
}
