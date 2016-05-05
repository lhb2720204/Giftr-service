package com.akhahaha.giftr.service.controllers;

import com.akhahaha.giftr.service.View;
import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.MatchDAO;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @JsonView(View.Summary.class)
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
    @JsonView(View.Detailed.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addUser(
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(defaultValue = "1") Integer gender,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "1") Integer giftType,
            @RequestParam(required = false) String interests,
            @RequestParam(defaultValue = "0") Integer priceMin,
            @RequestParam(defaultValue = "0") Integer priceMax) {
        // TODO Validate authorization

        User user = new User();
        setUserFields(user, null, username, status, gender, location, giftType, interests, priceMin, priceMax);

        Integer userID = userDAO.insertUser(user);
        if (userID == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create user.");
        }

        user = userDAO.getDetailedUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userID}")
                .buildAndExpand(userID).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    /**
     * Returns the specified User
     */
    @JsonView(View.Detailed.class)
    @RequestMapping(value = "/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUser(
            @PathVariable Integer userID) {
        // TODO Validate authorization
        validateUserExists(userID);

        User user = userDAO.getDetailedUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(userID).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    /**
     * Updates the specified User
     */
    @JsonView(View.Detailed.class)
    @RequestMapping(value = "/{userID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateUser(
            @PathVariable Integer userID,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer gender,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer giftType,
            @RequestParam(required = false) String interests,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax) {
        // TODO Validate authorization
        validateUserExists(userID);

        User user = userDAO.getUser(userID);
        setUserFields(user, userID, username, status, gender, location, giftType, interests, priceMin, priceMax);
        userDAO.updateUser(user);
        user = userDAO.getDetailedUser(userID);

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
            @PathVariable Integer userID) {
        // TODO Validate authorization
        validateUserExists(userID);

        List<Match> matches = matchDAO.findMatchesByUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(matches, headers, HttpStatus.OK);
    }

    private void validateUserExists(Integer userID) {
        if (userDAO.getUser(userID) == null) {
            throw new UserNotFoundException(userID);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class UserNotFoundException extends RuntimeException {
        UserNotFoundException(Integer userID) {
            super("Could not find user '" + userID + "'.");
        }
    }

    private void setUserFields(User user, Integer userID, String username, Integer status, Integer gender,
                               String location, Integer giftType, String interests, Integer priceMin, Integer priceMax) {
        if (userID != null) user.setId(userID);
        if (username != null) user.setUsername(username);
        if (status != null) user.setStatus(new UserStatus(status));
        if (gender != null) user.setGender(new Gender(gender));
        if (location != null) user.setLocation(location);
        if (giftType != null) user.setGiftType(new GiftType(giftType));
        if (interests != null) user.setInterests(interests);
        if (priceMin != null) user.setPriceMin(priceMin);
        if (priceMax != null) user.setPriceMax(priceMax);
    }
}
