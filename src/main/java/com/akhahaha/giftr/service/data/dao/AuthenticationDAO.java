package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.AuthenticationPair;

public interface AuthenticationDAO extends DAO {
	
	void insertPair(AuthenticationPair pair);
	
	void updatePair(AuthenticationPair pair);
	
	AuthenticationPair getPairByUsername (String username);
}
