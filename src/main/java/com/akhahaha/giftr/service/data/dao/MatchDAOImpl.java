package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;
import com.akhahaha.giftr.service.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MatchDAO JDBC Implementation
 * Created by Alan on 4/30/2016.
 */
public class MatchDAOImpl implements MatchDAO {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer insertMatch(Match match) {
        String sql = "INSERT INTO `Match` (status, created, lastModified, user1, user2, " +
                "user1Transaction, user2Transaction) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, match.getStatus().getId());
            ps.setDate(2, new Date(match.getCreated().getTime()));
            ps.setDate(3, new Date(match.getLastModified().getTime()));
            ps.setInt(4, match.getUser1().getId());
            ps.setInt(5, match.getUser2().getId());
            ps.setString(6, match.getUser1Transaction());
            ps.setString(7, match.getUser2Transaction());
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
    public void updateMatch(Match match) {
        String sql = "UPDATE `Match` SET status = ?, created = ?, lastModified = ?, user1 = ?, user2 = ?," +
                "user1Transaction = ?, user2Transaction = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, match.getStatus().getId());
            ps.setDate(2, new Date(match.getCreated().getTime()));
            ps.setDate(3, new Date(match.getLastModified().getTime()));
            ps.setInt(4, match.getUser1().getId());
            ps.setInt(5, match.getUser2().getId());
            ps.setString(6, match.getUser1Transaction());
            ps.setString(7, match.getUser2Transaction());
            ps.setInt(8, match.getId());
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
    public Match findMatchByID(Integer matchID) {
        String sql = "SELECT * FROM `Match` WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, matchID);
            Match match = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
                match = new Match(
                        rs.getInt("id"),
                        findMatchStatusByID(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        userDAO.findUserByID(rs.getInt("user1")),
                        userDAO.findUserByID(rs.getInt("user2")),
                        rs.getString("user1Transaction"),
                        rs.getString("user2Transaction"));
            }

            rs.close();
            ps.close();
            return match;
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
    public List<Match> findMatchesByUser(User user) {
        String sql = "SELECT * FROM `Match` WHERE user1 = ? OR user2 = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            List<Match> matches = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
            while (rs.next()) {
                matches.add(new Match(
                        rs.getInt("id"),
                        findMatchStatusByID(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        userDAO.findUserByID(rs.getInt("user1")),
                        userDAO.findUserByID(rs.getInt("user2")),
                        rs.getString("user1Transaction"),
                        rs.getString("user2Transaction")));
            }

            rs.close();
            ps.close();
            return matches;
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
    public List<Match> findMatchesByUserPair(User user1, User user2) {
        String sql = "SELECT * FROM `Match` WHERE (user1 = ? AND user2 = ?) OR (user1 = ? AND user2 = ?)";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user1.getId());
            ps.setInt(2, user2.getId());
            ps.setInt(3, user2.getId());
            ps.setInt(4, user1.getId());
            List<Match> matches = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
            while (rs.next()) {
                matches.add(new Match(
                        rs.getInt("id"),
                        findMatchStatusByID(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        userDAO.findUserByID(rs.getInt("user1")),
                        userDAO.findUserByID(rs.getInt("user2")),
                        rs.getString("user1Transaction"),
                        rs.getString("user2Transaction")));
            }

            rs.close();
            ps.close();
            return matches;
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
    public MatchStatus findMatchStatusByID(Integer matchStatusID) {
        String sql = "SELECT * FROM MatchStatus WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, matchStatusID);
            MatchStatus matchStatus = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                matchStatus = new MatchStatus(rs.getInt("id"), rs.getString("name"));
            }

            rs.close();
            ps.close();
            return matchStatus;
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
