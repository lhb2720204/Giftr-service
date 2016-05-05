package com.akhahaha.giftr.service.controllers;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.MatchDAO;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Match service controller
 * Created by Alan on 5/4/2016.
 */
@RestController
@RequestMapping("/matches")
public class MatchController {
    private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
    private MatchDAO matchDAO = (MatchDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.MATCH);

    /**
     * Searches on all matches
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllMatches(
            @RequestParam(value = "userID[]", required = false) Integer[] userIDs) {
        // TODO Validate authorization

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
            @RequestParam(defaultValue = "0") Integer user2Transaction) {
        // TODO Validate authorization

        if (userDAO.getUser(user1ID) == null || userDAO.getUser(user2ID) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Match Users not found");
        }

        Match match = new Match(user1ID, user2ID);
        setMatchFields(match, null, status, priceMin, priceMax, user1ID, user2ID,
                user1Transaction, user2Transaction);

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
        // TODO Validate authorization

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
            @RequestParam(required = false) Integer user1ID,
            @RequestParam(required = false) Integer user2ID,
            @RequestParam(required = false) Integer user1Transaction,
            @RequestParam(required = false) Integer user2Transaction) {
        // TODO Validate authorization

        Match match = matchDAO.getMatch(matchID);
        if (match == null) {
            throw new MatchNotFoundException(matchID);
        }

        setMatchFields(match, matchID, status, priceMin, priceMax, user1ID, user2ID,
                user1Transaction, user2Transaction);
        matchDAO.updateMatch(match);
        match = matchDAO.getMatch(matchID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(matchID).toUri());
        return new ResponseEntity<>(match, headers, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class MatchNotFoundException extends RuntimeException {
        MatchNotFoundException(Integer matchID) {
            super("Could not find match '" + matchID + "'.");
        }
    }

    private void setMatchFields(Match match, Integer matchID, Integer status, Integer priceMin, Integer priceMax,
                                Integer user1ID, Integer user2ID, Integer user1Transaction, Integer user2Transaction) {
        if (matchID != null) match.setId(matchID);
        if (status != null) match.setStatus(new MatchStatus(status));
        if (priceMin != null) match.setPriceMin(priceMin);
        if (priceMax != null) match.setPriceMax(priceMax);
        if (user1ID != null) match.setUser1ID(user1ID);
        if (user2ID != null) match.setUser2ID(user2ID);
        if (user1Transaction != null) match.setUser1Transaction(user1Transaction);
        if (user2Transaction != null) match.setUser2Transaction(user2Transaction);
    }
}
