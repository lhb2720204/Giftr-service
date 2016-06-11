package com.akhahaha.giftr.service.data.dao;

import java.util.List;

import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.PendingMatch;

public interface PendingMatchDAO extends DAO {
	Integer insertPendingMatch(PendingMatch pendingMatch);
	
	void updatePendingMatch(PendingMatch pendingMatch);
	
	void deletePendingMatch(Integer pendingMatchID);
	
	PendingMatch getPendingMatch(Integer pendingMatchID);
	
	PendingMatch searchPendingMatches(Integer giftType, Integer priceMin, Integer priceMax);
	
	List<PendingMatch> findPendingMatchesByUser(Integer userID);

	GiftType getGiftType(Integer giftTypeID);
}
