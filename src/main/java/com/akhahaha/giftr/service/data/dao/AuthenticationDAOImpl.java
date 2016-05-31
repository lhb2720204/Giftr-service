package com.akhahaha.giftr.service.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.akhahaha.giftr.service.data.models.AuthenticationPair;

public class AuthenticationDAOImpl implements AuthenticationDAO {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	@Override
	public void insertPair(AuthenticationPair pair) {
		String sql = "INSERT INTO Authentication (username, password) VALUES (?, ?)";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pair.getUsername());
            ps.setString(2, pair.getPassword());
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
	public void updatePair(AuthenticationPair pair) {
		String sql = "UPDATE Authentication SET password = ? WHERE username = ?";
		
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pair.getPassword());
            ps.setString(2, pair.getUsername());
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
	public AuthenticationPair getPairByUsername(String username) {
        String sql = "SELECT * FROM Authentication WHERE username = ?";
        
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            
            AuthenticationPair pair = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	pair = new AuthenticationPair(
            			rs.getString("username"),
            			rs.getString("password"));
            }
            rs.close();
            ps.close();
            return pair;
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
