package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.dao.queryBuilder.UserQueryBuilder;
import com.akhahaha.giftr.service.data.models.Gender;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.User;
import com.akhahaha.giftr.service.data.models.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO User (username, status, joinDate, lastActive, gender, location, " +
                "giftType, interests, priceMin, priceMax, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setInt(2, user.getStatus().getId());
            java.util.Date currentDate = new java.util.Date();
            ps.setDate(3, new Date(currentDate.getTime()));
            ps.setDate(4, new Date(currentDate.getTime()));
            ps.setInt(5, user.getGender().getId());
            ps.setString(6, user.getLocation());
            ps.setInt(7, user.getGiftType().getId());
            ps.setString(8, user.getInterests());
            ps.setInt(9, user.getPriceMin());
            ps.setInt(10, user.getPriceMax());
            ps.setString(11, user.getEmail());
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
        String sql = "UPDATE User SET username = ?, status = ?, lastActive = ?, gender = ?, location = ?," +
                "giftType = ?, interests = ?, priceMin = ?, priceMax = ?, email = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setInt(2, user.getStatus().getId());
            ps.setDate(3, new Date(new java.util.Date().getTime()));
            ps.setInt(4, user.getGender().getId());
            ps.setString(5, user.getLocation());
            ps.setInt(6, user.getGiftType().getId());
            ps.setString(7, user.getInterests());
            ps.setInt(8, user.getPriceMin());
            ps.setInt(9, user.getPriceMax());
            ps.setString(10, user.getEmail());
            ps.setInt(11, user.getId());
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
    public void deleteUser(Integer userID) {
        String sql = "DELETE FROM User WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
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
    public User getUser(Integer userID) {
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
                        rs.getString("username"),
                        getUserStatus(rs.getInt("status")),
                        rs.getDate("joinDate"),
                        rs.getDate("lastActive"),
                        getGender(rs.getInt("gender")),
                        rs.getString("location"),
                        getGiftType(rs.getInt("giftType")),
                        rs.getString("interests"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getString("email"));
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
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            User user = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        getUserStatus(rs.getInt("status")),
                        rs.getDate("joinDate"),
                        rs.getDate("lastActive"),
                        getGender(rs.getInt("gender")),
                        rs.getString("location"),
                        getGiftType(rs.getInt("giftType")),
                        rs.getString("interests"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getString("email"));
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
    public User getDetailedUser(Integer userID) {
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
                        rs.getString("username"),
                        getUserStatus(rs.getInt("status")),
                        rs.getDate("joinDate"),
                        rs.getDate("lastActive"),
                        getGender(rs.getInt("gender")),
                        rs.getString("location"),
                        getGiftType(rs.getInt("giftType")),
                        rs.getString("interests"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getString("email"));
                rs.close();
                ps.close();

                // Get User matches
                sql = "SELECT id FROM `Match` WHERE user1 = ? OR user2 = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, userID);
                ps.setInt(2, userID);
                List<Integer> matches = new ArrayList<>();
                rs = ps.executeQuery();
                while (rs.next()) {
                    matches.add(rs.getInt("id"));
                }
                user.setMatches(matches);
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
    public List<User> getUsersByAdvancedSearch(UserQueryBuilder userQueryBuilder) {
    	
    	Connection connection = null;
    	
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(userQueryBuilder.buildQuery());
            List<User> users = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        getUserStatus(rs.getInt("status")),
                        rs.getDate("joinDate"),
                        rs.getDate("lastActive"),
                        getGender(rs.getInt("gender")),
                        rs.getString("location"),
                        getGiftType(rs.getInt("giftType")),
                        rs.getString("interests"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getString("email")));
            }
            rs.close();
            ps.close();
            return users;
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
    public UserStatus getUserStatus(Integer userStatusID) {
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
    public Gender getGender(Integer genderID) {
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
    public GiftType getGiftType(Integer giftTypeID) {
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
