package com.akhahaha.giftr.service.data.dao.queryBuilder;

public class UserQueryBuilder {
	
	private String username;
	private Integer gender;
	private String location;
	private Integer giftType;
	private String interests;
	private Integer priceMin;
	private Integer priceMax;
	private Integer start;
	private Integer numResults;
	
	public UserQueryBuilder() {
		gender = 1;
		giftType = 1;
//		start = 0;
//		numResults = 10;
	}
	
	public UserQueryBuilder setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public UserQueryBuilder setGender(Integer gender) {
		this.gender = gender;
		return this;
	}
	
	public UserQueryBuilder setLocation(String location) {
		this.location = location;
		return this;
	}
	
	public UserQueryBuilder setGiftType(Integer giftType) {
		this.giftType = giftType;
		return this;
	}
	
	public UserQueryBuilder setInterests(String interests) {
		this.interests = interests;
		return this;
	}
	
	public UserQueryBuilder setPriceMin(Integer priceMin) {
		this.priceMin = priceMin;
		return this;
	}
	
	public UserQueryBuilder setPriceMax(Integer priceMax) {
		this.priceMax = priceMax;
		return this;
	}

	public UserQueryBuilder setStart(Integer start) {
		this.start = start;
		return this;
	}

	public UserQueryBuilder setNumResults(Integer numResults) {
		this.numResults = numResults;
		return this;
	}
	
	public String buildQuery() {
		String usernameCond = username!=null ? " AND username='" + username + "'" : "";
		String genderCond = gender!=1 ? " AND gender=" + Integer.toString(gender) : "";
		String locationCond = location!=null ? " AND location='" + location + "'" : "";
		String giftTypeCond = giftType!=1 ? " AND giftType=" + Integer.toString(giftType) : "";
		String interestsCond = interests!=null ?
				               " AND interests LIKE CONCAT('%','" + interests + "','%')" : "";
		String priceMinCond = priceMin!=null ? " AND priceMin <=" + Integer.toString(priceMin) : "";
		String priceMaxCond = priceMax!=null ? " AND priceMax >=" + Integer.toString(priceMax) : "";
		String listNumCond = (numResults!=null && start!=null) ? " LIMIT " + Integer.toString(start) + ", "
				+ Integer.toString(numResults) : "";
		
		return "SELECT * FROM User WHERE status<>3"
				+ usernameCond
				+ genderCond
				+ locationCond
				+ giftTypeCond
				+ interestsCond
				+ priceMinCond
				+ priceMaxCond
				+ listNumCond;
	}
}
