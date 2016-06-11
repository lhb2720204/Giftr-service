package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.dao.queryBuilder.UserQueryBuilder;
import com.akhahaha.giftr.service.data.models.Gender;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.User;
import com.akhahaha.giftr.service.data.models.UserStatus;

import java.util.List;

/**
 * User DAOType interface
 * Created by Alan on 4/29/2016.
 */
public interface UserDAO extends DAO {
    /**
     * Inserts a new User
     *
     * @param user User data to insert
     * @return The generated User ID
     */
    Integer insertUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userID);

    User getUser(Integer userID);

    User getUserByUsername(String username);

    User getDetailedUser(Integer userID);
    
    List<User> getUsersByAdvancedSearch(UserQueryBuilder userQueryBuilder);

    UserStatus getUserStatus(Integer userStatusID);

    Gender getGender(Integer genderID);

    GiftType getGiftType(Integer giftTypeID);
}
