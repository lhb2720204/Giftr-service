package com.akhahaha.giftr.service.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.PendingMatch;

public class PendingMatchDAOImpl implements PendingMatchDAO {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	@Override
	public Integer insertPendingMatch(PendingMatch pendingMatch) {
		String sql = "INSERT INTO PendingMatch (userId, giftType, priceMin, priceMax) VALUES (?, ?, ?, ?)";
		
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pendingMatch.getUserID());
            ps.setInt(2, pendingMatch.getGiftType().getId());
            ps.setInt(3, pendingMatch.getPriceMin());
            ps.setInt(4, pendingMatch.getPriceMax());
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
	public void updatePendingMatch(PendingMatch pendingMatch) {
        String sql = "UPDATE PendingMatch SET userId = ?, giftType = ?, priceMin = ?, priceMax = ?, WHERE id = ?";
        
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pendingMatch.getUserID());
            ps.setInt(2, pendingMatch.getGiftType().getId());
            ps.setInt(3, pendingMatch.getPriceMin());
            ps.setInt(4, pendingMatch.getPriceMax());
            ps.setInt(5, pendingMatch.getId());
            ps.executeQuery();
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
	public void deletePendingMatch(Integer pendingMatchId) {
        String sql = "DELETE FROM PendingMatch WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pendingMatchId);
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
	public PendingMatch getPendingMatch(Integer pendingMatchID) {
        String sql = "SELECT * FROM PendingMatch WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pendingMatchID);
            PendingMatch pendingMatch = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	pendingMatch = new PendingMatch(
            			rs.getInt("id"),
            			rs.getInt("userId"),
            			getGiftType(rs.getInt("giftType")),
            			rs.getInt("priceMin"),
            			rs.getInt("priceMax"));
            }            
            
            rs.close();
            ps.close();
            return pendingMatch;
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
	public PendingMatch searchPendingMatches(Integer giftType, Integer priceMin, Integer priceMax) {
		String sql = "SELECT * FROM PendingMatch WHERE giftType = ? and priceMin = ? and priceMax = ?";
		
    	Connection connection = null;
    	
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, giftType);
            ps.setInt(2, priceMin);
            ps.setInt(3, priceMax);
            PendingMatch pendingMatch = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	pendingMatch = new PendingMatch(
            			rs.getInt("id"),
            			rs.getInt("userId"),
            			getGiftType(rs.getInt("giftType")),
            			rs.getInt("priceMin"),
            			rs.getInt("priceMax"));
            }            
            rs.close();
            ps.close();
            return pendingMatch;
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
	public List<PendingMatch> findPendingMatchesByUser(Integer userID) {
		String sql = "SELECT * FROM PendingMatch WHERE userId = ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            List<PendingMatch> pendingMatches = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	pendingMatches.add(new PendingMatch(
            			rs.getInt("id"),
            			rs.getInt("userId"),
            			getGiftType(rs.getInt("giftType")),
            			rs.getInt("priceMin"),
            			rs.getInt("priceMax")));
            }

            rs.close();
            ps.close();
            return pendingMatches;
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
