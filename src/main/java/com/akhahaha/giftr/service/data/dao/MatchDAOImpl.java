package com.akhahaha.giftr.service.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.Match;
import com.akhahaha.giftr.service.data.models.MatchStatus;

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
        String sql = "INSERT INTO `Match` (status, created, lastModified, priceMin, priceMax, " +
                "user1, user2, user1Transaction, user2Transaction, giftType) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, match.getStatus().getId());
            ps.setDate(2, new Date(match.getCreated().getTime()));
            ps.setDate(3, new Date(match.getLastModified().getTime()));
            ps.setInt(4, match.getPriceMin());
            ps.setInt(5, match.getPriceMax());
            ps.setInt(6, match.getUser1ID());
            ps.setInt(7, match.getUser2ID());
            ps.setInt(8, match.getUser1Transaction());
            ps.setInt(9, match.getUser2Transaction());
            ps.setInt(10,  match.getGiftType().getId());
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
        String sql = "UPDATE `Match` SET status = ?, lastModified = ?, priceMin = ?, priceMax = ?, " +
                "user1Transaction = ?, user2Transaction = ?, giftType = ? WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, match.getStatus().getId());
            ps.setDate(2, new Date(new java.util.Date().getTime()));
            ps.setInt(3, match.getPriceMin());
            ps.setInt(4, match.getPriceMax());
            ps.setInt(5, match.getUser1Transaction());
            ps.setInt(6, match.getUser2Transaction());
            ps.setInt(7, match.getGiftType().getId());
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
    public void deleteMatch(Integer matchID) {
        String sql = "DELETE FROM `Match` WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, matchID);
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
    public Match getMatch(Integer matchID) {
        String sql = "SELECT * FROM `Match` WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, matchID);
            Match match = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                match = new Match(
                        rs.getInt("id"),
                        getMatchStatus(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getInt("user1"),
                        rs.getInt("user2"),
                        rs.getInt("user1Transaction"),
                        rs.getInt("user2Transaction"),
                        getGiftType(rs.getInt("giftType")));
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
    public List<Match> getAllMatches() {
        String sql = "SELECT * FROM `Match`";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            List<Match> matches = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                matches.add(new Match(
                        rs.getInt("id"),
                        getMatchStatus(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getInt("user1"),
                        rs.getInt("user2"),
                        rs.getInt("user1Transaction"),
                        rs.getInt("user2Transaction"),
                        getGiftType(rs.getInt("giftType"))));
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
    public List<Match> findMatchesByUser(Integer userID) {
        String sql = "SELECT * FROM `Match` WHERE user1 = ? OR user2 = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setInt(2, userID);
            List<Match> matches = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                matches.add(new Match(
                        rs.getInt("id"),
                        getMatchStatus(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getInt("user1"),
                        rs.getInt("user2"),
                        rs.getInt("user1Transaction"),
                        rs.getInt("user2Transaction"),
                        getGiftType(rs.getInt("giftType"))));
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
    public List<Match> findMatchesByUserPair(Integer userID1, Integer userID2) {
        String sql = "SELECT * FROM `Match` WHERE (user1 = ? AND user2 = ?) OR (user1 = ? AND user2 = ?)";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID1);
            ps.setInt(2, userID2);
            ps.setInt(3, userID2);
            ps.setInt(4, userID1);
            List<Match> matches = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                matches.add(new Match(
                        rs.getInt("id"),
                        getMatchStatus(rs.getInt("status")),
                        rs.getDate("created"),
                        rs.getDate("lastModified"),
                        rs.getInt("priceMin"),
                        rs.getInt("priceMax"),
                        rs.getInt("user1"),
                        rs.getInt("user2"),
                        rs.getInt("user1Transaction"),
                        rs.getInt("user2Transaction"),
                        getGiftType(rs.getInt("giftType"))));
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
    public MatchStatus getMatchStatus(Integer matchStatusID) {
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
