package com.akhahaha.giftr.service.controllers;

import java.security.Principal;
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

import com.akhahaha.giftr.service.View;
import com.akhahaha.giftr.service.data.dao.AuthenticationDAO;
import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.MatchDAO;
import com.akhahaha.giftr.service.data.dao.PendingMatchDAO;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.dao.queryBuilder.UserQueryBuilder;
import com.akhahaha.giftr.service.data.models.AuthenticationPair;
import com.akhahaha.giftr.service.data.models.Gender;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.PendingMatch;
import com.akhahaha.giftr.service.data.models.User;
import com.akhahaha.giftr.service.data.models.UserStatus;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * User service controller
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
    private MatchDAO matchDAO = (MatchDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.MATCH);
    private PendingMatchDAO pendingMatchDAO = (PendingMatchDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.PENDINGMATCH);
    private AuthenticationDAO authenticationDAO = (AuthenticationDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.AUTHENTICATION);

    /**
     * Advanced search for users
     * If no parameter is provided, returns all users that are not deleted
     */
    @JsonView(View.Summary.class)
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer gender,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer giftType,
            @RequestParam(required = false) String interests,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer numResults) {
    	
        UserQueryBuilder userQueryBuilder = new UserQueryBuilder();
        setUserQueryBuilder(userQueryBuilder, username, gender, location, giftType,
                interests, priceMin, priceMax, start, numResults);
        List<User> users;
        users = userDAO.getUsersByAdvancedSearch(userQueryBuilder);
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
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addUser(
            @RequestParam String username,
            @RequestParam(defaultValue = "1") Integer status,
            @RequestParam(defaultValue = "1") Integer gender,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "1") Integer giftType,
            @RequestParam(required = false) String interests,
            @RequestParam(defaultValue = "0") Integer priceMin,
            @RequestParam(defaultValue = "0") Integer priceMax,
            @RequestParam(required = false) String email,
            @RequestParam String password) {

        User user = new User();
        setUserFields(user, null, username, status, gender, location, giftType, interests, priceMin, priceMax, email);
        
        if (username.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create user.");        	
        }

        Integer userID = userDAO.insertUser(user);
        if (userID == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create user.");
        }
        
        if (username != null && password != null) {
        	AuthenticationPair pair = new AuthenticationPair(username, password);
        	authenticationDAO.insertPair(pair);
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
        validateUserExists(userID);

        User user = userDAO.getDetailedUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(userID).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }
    
    /**
     * Returns the logged in User
     */
    @JsonView(View.Detailed.class)
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> currentUserName(Principal principal) {
        String username = principal.getName();

        User user = userDAO.getUserByUsername(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(user.getId()).toUri());
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
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        validateUserExists(userID);

        User user = userDAO.getUser(userID);
        setUserFields(user, userID, username, status, gender, location, giftType, interests, priceMin, priceMax, email);
        userDAO.updateUser(user);
        user = userDAO.getDetailedUser(userID);
        
        if (username != null && password != null) {
        	AuthenticationPair pair = new AuthenticationPair(username, password);
        	authenticationDAO.updatePair(pair);
        }

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
        validateUserExists(userID);

        List<Match> matches = matchDAO.findMatchesByUser(userID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(matches, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{userID}/pendingmatches", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserPendingMatches(
            @PathVariable Integer userID) {
        validateUserExists(userID);

        List<PendingMatch> pendingMatches = pendingMatchDAO.findPendingMatchesByUser(userID);
        		
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(pendingMatches, headers, HttpStatus.OK);
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

    private void setUserFields(User user, Integer userID, String username, Integer status, Integer gender, String location,
                               Integer giftType, String interests, Integer priceMin, Integer priceMax, String email) {
        if (userID != null) user.setId(userID);
        if (username != null) user.setUsername(username);
        if (status != null) user.setStatus(new UserStatus(status));
        if (gender != null) user.setGender(new Gender(gender));
        if (location != null) user.setLocation(location);
        if (giftType != null) user.setGiftType(new GiftType(giftType));
        if (interests != null) user.setInterests(interests);
        if (priceMin != null) user.setPriceMin(priceMin);
        if (priceMax != null) user.setPriceMax(priceMax);
        if (email != null) user.setEmail(email);
    }
    
	private void setUserQueryBuilder(UserQueryBuilder userQueryBuilder, String username, Integer gender, 
							   String location, Integer giftType, String interests, Integer priceMin, Integer priceMax,
                                     Integer start, Integer numResults) {
        if (username != null) userQueryBuilder.setUsername(username);
        if (gender != null) userQueryBuilder.setGender(gender);
        if (location != null) userQueryBuilder.setLocation(location);
        if (giftType != null) userQueryBuilder.setGiftType(giftType);
        if (interests != null) userQueryBuilder.setInterests(interests);
        if (priceMin != null) userQueryBuilder.setPriceMin(priceMin);
        if (priceMax != null) userQueryBuilder.setPriceMax(priceMax);
        if (start != null) userQueryBuilder.setStart(start);
        if (numResults != null) userQueryBuilder.setNumResults(numResults);
	}
}
