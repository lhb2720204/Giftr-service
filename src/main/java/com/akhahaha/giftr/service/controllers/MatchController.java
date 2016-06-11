package com.akhahaha.giftr.service.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.MatchDAO;
import com.akhahaha.giftr.service.data.dao.PendingMatchDAO;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;
import com.akhahaha.giftr.service.data.models.PendingMatch;

/**
 * Match service controller
 * Created by Alan on 5/4/2016.
 */
@CrossOrigin
@RestController
@RequestMapping("/matches")
public class MatchController {
    private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
    private MatchDAO matchDAO = (MatchDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.MATCH);
    private PendingMatchDAO pendingMatchDAO = (PendingMatchDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.PENDINGMATCH);

    /**
     * Searches on all matches
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllMatches(
            @RequestParam(value = "userID[]", required = false) Integer[] userIDs) {

        List<Match> matches;
        if (userIDs == null || userIDs.length == 0) {
            matches = matchDAO.getAllMatches();
        } else if (userIDs.length == 1) {
            matches = matchDAO.findMatchesByUser(userIDs[0]);
        } else if (userIDs.length == 2) {
            matches = matchDAO.findMatchesByUserPair(userIDs[0], userIDs[1]);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Too many User IDs.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(matches, headers, HttpStatus.OK);
    }

    /**
     * Creates a new Match
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createMatch(
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(defaultValue = "0") Integer priceMin,
            @RequestParam(defaultValue = "0") Integer priceMax,
            @RequestParam Integer user1ID,
            @RequestParam Integer user2ID,
            @RequestParam(defaultValue = "0") Integer user1Transaction,
            @RequestParam(defaultValue = "0") Integer user2Transaction,
            @RequestParam(defaultValue = "1") Integer giftType) {

        if (userDAO.getUser(user1ID) == null || userDAO.getUser(user2ID) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Match Users not found");
        }

        Match match = new Match(user1ID, user2ID);
        setMatchFields(match, null, status, priceMin, priceMax, user1ID, user2ID,
                user1Transaction, user2Transaction, giftType);

        Integer matchID = matchDAO.insertMatch(match);
        match = matchDAO.getMatch(matchID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{matchID}")
                .buildAndExpand(matchID).toUri());
        return new ResponseEntity<>(match, headers, HttpStatus.CREATED);
    }

    /**
     * Returns the specified Match
     */
    @RequestMapping(value = "/{matchID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getMatch(
            @PathVariable Integer matchID) {

        Match match = matchDAO.getMatch(matchID);
        if (match == null) {
            throw new MatchNotFoundException(matchID);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(matchID).toUri());
        return new ResponseEntity<>(match, headers, HttpStatus.OK);
    }

    /**
     * Updates the specified Match
     */
    @RequestMapping(value = "/{matchID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateMatch(
            @PathVariable Integer matchID,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) Integer user1Transaction,
            @RequestParam(required = false) Integer user2Transaction,
            @RequestParam(required = false) Integer giftType) {

        Match match = matchDAO.getMatch(matchID);
        if (match == null) {
            throw new MatchNotFoundException(matchID);
        }

        setMatchFields(match, matchID, status, priceMin, priceMax, null, null,
                user1Transaction, user2Transaction, giftType);
        matchDAO.updateMatch(match);
        match = matchDAO.getMatch(matchID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(matchID).toUri());
        return new ResponseEntity<>(match, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/attempt", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> attemptMatch(
    		@RequestParam Integer userID,
			@RequestParam Integer giftType,
			@RequestParam Integer priceMin,
			@RequestParam Integer priceMax) {
    	PendingMatch pendingMatch = pendingMatchDAO.searchPendingMatches(giftType, priceMin, priceMax);
    	
    	Match match = new Match();
    	
    	if (pendingMatch != null && userID != pendingMatch.getUserID()) {
    		pendingMatchDAO.deletePendingMatch(pendingMatch.getId());
    		
            match = new Match(userID, pendingMatch.getUserID());
            setMatchFields(match, null, 1, priceMin, priceMax, userID, pendingMatch.getUserID(),
                    0, 0, giftType);

            Integer matchID = matchDAO.insertMatch(match);
            match = matchDAO.getMatch(matchID);
    	}
    	else if (pendingMatch == null || userID != pendingMatch.getUserID()) {
    		pendingMatch = new PendingMatch();
    		pendingMatch.setUserID(userID);
    		pendingMatch.setGiftType(new GiftType(giftType));
    		pendingMatch.setPriceMin(priceMin);
    		pendingMatch.setPriceMax(priceMax);
    		pendingMatchDAO.insertPendingMatch(pendingMatch);
    	}
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(match, headers, HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class MatchNotFoundException extends RuntimeException {
        MatchNotFoundException(Integer matchID) {
            super("Could not find match '" + matchID + "'.");
        }
    }

    private void setMatchFields(Match match, Integer matchID, Integer status, Integer priceMin,
    							Integer priceMax,Integer user1ID, Integer user2ID, Integer user1Transaction,
                                Integer user2Transaction, Integer giftType) {
        if (matchID != null) match.setId(matchID);
        if (status != null) match.setStatus(new MatchStatus(status));
        if (priceMin != null) match.setPriceMin(priceMin);
        if (priceMax != null) match.setPriceMax(priceMax);
        if (user1ID != null) match.setUser1ID(user1ID);
        if (user2ID != null) match.setUser2ID(user2ID);
        if (user1Transaction != null) match.setUser1Transaction(user1Transaction);
        if (user2Transaction != null) match.setUser2Transaction(user2Transaction);
        if (giftType != null) match.setGiftType(new GiftType(giftType));
    }
}
