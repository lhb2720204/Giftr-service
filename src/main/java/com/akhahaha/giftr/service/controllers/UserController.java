package com.akhahaha.giftr.service.controllers;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.MatchDAO;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * User service controller
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
    private MatchDAO matchDAO = (MatchDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.MATCH);

    /**
     * Searches on all users
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> searchUsers() {
        // TODO Validate authorization
        // TODO Implement search parameters

        List<User> users = userDAO.getAllUsers();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(users, headers, HttpStatus.OK);
    }

    /**
     * Creates or inserts a new User
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addUser(
            @RequestBody(required = false) User userInput) {
        // TODO Validate authorization

        User user = userInput != null ? userInput : new User();
        Integer userID = userDAO.insertUser(user);
        if (userID == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create user.");
        }

        user = userDAO.getUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userID}")
                .buildAndExpand(userID).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    /**
     * Returns the specified User
     */
    @RequestMapping(value = "/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUser(
            @PathVariable("userID") Integer userID) {
        // TODO Validate authorization

        User user = userDAO.getUser(userID);
        if (user == null) {
            throw new UserNotFoundException(userID);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(userID).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    /**
     * Updates the specified User
     */
    @RequestMapping(value = "/{userID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateUser(
            @PathVariable("userID") Integer userID,
            @RequestBody User userInput) {
        // TODO Validate authorization

        User user = userDAO.getUser(userID);
        if (user == null) {
            throw new UserNotFoundException(userID);
        }

        user = userInput;
        user.setId(userID);
        userDAO.updateUser(user);
        user = userDAO.getUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(userID).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    /**
     * Returns matches involving the specified User
     */
    @RequestMapping(value = "/{userID}/matches", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserMatches(
            @PathVariable("userID") Integer userID) {
        // TODO Validate authorization

        User user = userDAO.getUser(userID);
        if (user == null) {
            throw new UserNotFoundException(userID);
        }

        List<Match> matches = matchDAO.findMatchesByUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(matches, headers, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class UserNotFoundException extends RuntimeException {
        UserNotFoundException(Integer userID) {
            super("Could not find user '" + userID + "'.");
        }
    }
}
