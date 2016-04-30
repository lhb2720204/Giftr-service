package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.Gender;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.User;
import com.akhahaha.giftr.service.data.models.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

/**
 * UserDAO JDBC Implementation
 * Created by Alan on 4/29/2016.
 */
public class UserDAOImpl implements UserDAO {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer insertUser(User user) {
        String sql = "INSERT INTO User (status, joinDate, lastActive, gender, location, giftType, interests) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getStatus().getId());
            ps.setDate(2, new Date(user.getJoinDate().getTime()));
            ps.setDate(3, new Date(user.getLastActive().getTime()));
            ps.setInt(4, user.getGender().getId());
            ps.setString(5, user.getLocation());
            ps.setInt(6, user.getGiftType().getId());
            ps.setString(7, user.getInterests());
            ps.executeUpdate();

            Integer id = null;
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            ps.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE User SET status = ?, joinDate = ?, lastActive = ?, gender = ?, location = ?," +
                "giftType = ?, interests = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getStatus().getId());
            ps.setDate(2, new Date(user.getJoinDate().getTime()));
            ps.setDate(3, new Date(user.getLastActive().getTime()));
            ps.setInt(4, user.getGender().getId());
            ps.setString(5, user.getLocation());
            ps.setInt(6, user.getGiftType().getId());
            ps.setString(7, user.getInterests());
            ps.setInt(8, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User findUserByID(Integer userID) {
        String sql = "SELECT * FROM User WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            User user = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        findUserStatusByID(rs.getInt("status")),
                        rs.getDate("joinDate"),
                        rs.getDate("lastActive"),
                        findGenderByID(rs.getInt("gender")),
                        rs.getString("location"),
                        findGiftTypeByID(rs.getInt("giftType")),
                        rs.getString("interests"));
            }

            rs.close();
            ps.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public UserStatus findUserStatusByID(Integer userStatusID) {
        String sql = "SELECT * FROM UserStatus WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userStatusID);
            UserStatus userStatus = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userStatus = new UserStatus(rs.getInt("id"), rs.getString("name"));
            }

            rs.close();
            ps.close();
            return userStatus;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public Gender findGenderByID(Integer genderID) {
        String sql = "SELECT * FROM Gender WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, genderID);
            Gender gender = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                gender = new Gender(rs.getInt("id"), rs.getString("name"));
            }

            rs.close();
            ps.close();
            return gender;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public GiftType findGiftTypeByID(Integer giftTypeID) {
        String sql = "SELECT * FROM GiftType WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, giftTypeID);
            GiftType giftType = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                giftType = new GiftType(rs.getInt("id"), rs.getString("name"));
            }

            rs.close();
            ps.close();
            return giftType;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
