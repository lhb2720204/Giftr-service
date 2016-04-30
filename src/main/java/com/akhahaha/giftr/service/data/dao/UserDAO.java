package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.Gender;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.User;
import com.akhahaha.giftr.service.data.models.UserStatus;

/**
 * User DAOType interface
 * Created by Alan on 4/29/2016.
 */
public interface UserDAO extends DAO {
    /**
     * Inserts a new User
     * @param user User data to insert
     * @return The generated User ID
     */
    Integer insertUser(User user);

    void updateUser(User user);

    User findUserByID(Integer userID);

    UserStatus findUserStatusByID(Integer userStatusID);

    Gender findGenderByID(Integer genderID);

    GiftType findGiftTypeByID(Integer giftTypeID);
}
