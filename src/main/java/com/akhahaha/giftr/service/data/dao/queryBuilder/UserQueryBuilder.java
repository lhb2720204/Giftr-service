package com.akhahaha.giftr.service.data.dao.queryBuilder;

import com.akhahaha.giftr.service.data.models.User;

public class UserQueryBuilder {
	
	private String username;
	private Integer gender;
	private String location;
	private Integer giftType;
	private String interests;
	private Integer priceMin;
	private Integer priceMax;
	
	// Use User object to pass in condition parameters
	public UserQueryBuilder(User conditionForm) {
		this.username = conditionForm.getUsername();
		this.gender = conditionForm.getGender().getId();
		this.location = conditionForm.getLocation();
		this.giftType = conditionForm.getGiftType().getId();
		this.interests = conditionForm.getInterests();
		this.priceMin = conditionForm.getPriceMin();
		this.priceMax = conditionForm.getPriceMax();
	}

	public String build() {
		String usernameCond = username!=null ? " AND username='" + username + "'" : "";
		String genderCond = gender!=1 ? " AND gender=" + Integer.toString(gender) : "";
		String locationCond = location!=null ? " AND location='" + location + "'" : "";
		String giftTypeCond = giftType!=1 ? " AND giftType=" + Integer.toString(giftType) : "";
		String interestsCond = interests!=null ?
				               " AND interests LIKE CONCAT('%','" + interests + "','%')" : "";
		String priceMinCond = priceMin!=0 ? " AND priceMin <=" + Integer.toString(priceMin) : "";
		String priceMaxCond = priceMax!=0 ? " AND priceMax >=" + Integer.toString(priceMax) : "";
		
		return "SELECT * FROM User WHERE status<>3"
				+ usernameCond
				+ genderCond
				+ locationCond
				+ giftTypeCond
				+ interestsCond
				+ priceMinCond
				+ priceMaxCond;
	}
}
