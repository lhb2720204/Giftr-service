package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;

import java.util.List;

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
}
