package com.akhahaha.giftr.service.data.dao;

import java.util.List;

import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;

/**
 * Match DAO interface
 * Created by Alan on 4/30/2016.
 */
public interface MatchDAO extends DAO {
    /**
     * Inserts a new Match
     *
     * @param match Match data to insert
     * @return The generated Match ID
     */
    Integer insertMatch(Match match);

    void updateMatch(Match match);

    void deleteMatch(Integer matchID);

    Match getMatch(Integer matchID);

    List<Match> getAllMatches();

    /**
     * Finds Matches involving the User
     */
    List<Match> findMatchesByUser(Integer userID);

    /**
     * Finds Matches containing the User pair (order-independent)
     */
    List<Match> findMatchesByUserPair(Integer userID1, Integer userID2);

    MatchStatus getMatchStatus(Integer matchStatusID);
    
    GiftType getGiftType(Integer giftTypeID);
}
