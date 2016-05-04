package com.akhahaha.giftr.service.controllers;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.MatchDAO;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.Match;
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

        List<Match> matches = null;
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
            @RequestBody Match matchInput) {
        // TODO Validate authorization

        if (userDAO.getUser(matchInput.getUser1ID()) == null ||
                userDAO.getUser(matchInput.getUser2ID()) == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Match Users not found");
        }

        Integer matchID = matchDAO.insertMatch(matchInput);
        Match match = matchDAO.getMatch(matchID);

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
            @PathVariable("matchID") Integer matchID) {
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
            @PathVariable("matchID") Integer matchID,
            @RequestBody Match matchInput) {
        // TODO Validate authorization

        Match match = matchDAO.getMatch(matchID);
        if (match == null) {
            throw new MatchNotFoundException(matchID);
        }

        match = matchInput;
        match.setId(matchID);
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
}
