package com.akhahaha.giftr.service.data.models;

import com.akhahaha.giftr.service.View;
import com.fasterxml.jackson.annotation.JsonView;

public class PendingMatch {
    @JsonView(View.Summary.class)
    private Integer id;
    @JsonView(View.Summary.class)
    private Integer userID;
    @JsonView(View.Summary.class)
    private GiftType giftType;
    @JsonView(View.Summary.class)
    private Integer priceMin;
    @JsonView(View.Summary.class)
    private Integer priceMax;
    
    public PendingMatch() {
    }
    
	public PendingMatch(Integer id, Integer userID, GiftType giftType, Integer priceMin, Integer priceMax) {
		this.id = id;
		this.userID = userID;
		this.giftType = giftType;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserID() {
		return userID;
	}
	
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	public GiftType getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftType giftType) {
		this.giftType = giftType;
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
}
