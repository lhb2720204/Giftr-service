package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;
import com.akhahaha.giftr.service.data.models.User;

import java.util.List;

/**
 * Match DAO interface
 * Created by Alan on 4/30/2016.
 */
public interface MatchDAO extends DAO {
    /**
     * Inserts a new Match
     * @param match Match data to insert
     * @return The generated Match ID
     */
    Integer insertMatch(Match match);

    void updateMatch(Match match);

    Match findMatchByID(Integer matchID);

    /**
     * Finds Matches involving the User
     */
    List<Match> findMatchesByUser(User user);

    /**
     * Finds Matches containing the User pair (order-independent)
     */
    List<Match> findMatchesByUserPair(User user1, User user2);

    MatchStatus findMatchStatusByID(Integer matchStatusID);
}
