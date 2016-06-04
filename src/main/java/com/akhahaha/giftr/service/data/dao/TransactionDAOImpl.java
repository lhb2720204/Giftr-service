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

import com.akhahaha.giftr.service.data.models.Transaction;
import com.akhahaha.giftr.service.data.models.TransactionStatus;

public class TransactionDAOImpl implements TransactionDAO {
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Integer insertTransaction(Transaction transaction) {
		String sql = "INSERT INTO Transaction (buyerId, productSource, productSourceId, creditCard, paypal, venmo, " +
				"billingAddress, shippingAddress, senderMessage, status, created, lastModified) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, transaction.getBuyerId());
			ps.setInt(2, transaction.getProductSource());
			ps.setLong(3, transaction.getProductSourceId());
			ps.setLong(4, transaction.getCreditCard());
			ps.setString(5, transaction.getPaypal());
			ps.setString(6, transaction.getVenmo());
			ps.setString(7, transaction.getBillingAddress());
			ps.setString(8, transaction.getShippingAddress());
			ps.setString(9, transaction.getSenderMessage());
			ps.setInt(10, transaction.getStatus().getId());
			java.util.Date currentDate = new java.util.Date();
			ps.setDate(11, new Date(currentDate.getTime()));
			ps.setDate(12, new Date(currentDate.getTime()));
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
	public void updateTransaction(Transaction transaction) {
		String sql = "UPDATE Transaction SET buyerId = ?, productSource = ?, productSourceId = ?, creditCard = ?, " +
				"paypal = ?, venmo = ?, billingAddress = ?, shippingAddress = ?, senderMessage = ?, status = ?,  " +
				"lastModified = ? WHERE id = ?";
		
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transaction.getBuyerId());
            ps.setInt(2, transaction.getProductSource());
            ps.setLong(3, transaction.getProductSourceId());
            ps.setLong(4, transaction.getCreditCard());
            ps.setString(5, transaction.getPaypal());
            ps.setString(6, transaction.getVenmo());
            ps.setString(7, transaction.getBillingAddress());
            ps.setString(8, transaction.getShippingAddress());
            ps.setString(9, transaction.getSenderMessage());
            ps.setInt(10, transaction.getStatus().getId());
            ps.setDate(11, new Date(new java.util.Date().getTime()));
            ps.setInt(12, transaction.getId());
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
	public void deleteTransaction(Integer transactionID) {
        String sql = "DELETE FROM Transaction WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transactionID);
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
	public Transaction getTransaction(Integer transactionID) {
        String sql = "SELECT * FROM Transaction WHERE id = ?";

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transactionID);
            Transaction transaction = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	transaction = new Transaction(
            			rs.getInt("id"),
            			rs.getInt("buyerId"),
            			rs.getInt("productSource"),
            			rs.getLong("productSourceId"),
            			rs.getLong("creditCard"),
            			rs.getString("paypal"),
            			rs.getString("venmo"),
            			rs.getString("billingAddress"),
            			rs.getString("shippingAddress"),
            			rs.getString("senderMessage"),
            			getTransactionStatus(rs.getInt("status")),
            			rs.getDate("created"),
            			rs.getDate("lastModified")
            			);
            }

            rs.close();
            ps.close();
            return transaction;
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
	public List<Transaction> getTransactionsByBuyer(Integer buyerID) {
		String sql = "SELECT * FROM Transaction WHERE buyerId = ?";
		
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, buyerID);
            List<Transaction> transactions = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	transactions.add(new Transaction(
            			rs.getInt("id"),
            			rs.getInt("buyerId"),
            			rs.getInt("productSource"),
            			rs.getLong("productSourceId"),
            			rs.getLong("creditCard"),
            			rs.getString("paypal"),
            			rs.getString("venmo"),
            			rs.getString("billingAddress"),
            			rs.getString("shippingAddress"),
            			rs.getString("senderMessage"),
            			getTransactionStatus(rs.getInt("status")),
            			rs.getDate("created"),
            			rs.getDate("lastModified")));
            }
            rs.close();
            ps.close();
            return transactions;
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
	public TransactionStatus getTransactionStatus(Integer transactionStatusID) {
        String sql = "SELECT * FROM TransactionStatus WHERE id = ?";

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transactionStatusID);
            TransactionStatus transactionStatus = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	transactionStatus = new TransactionStatus(rs.getInt("id"), rs.getString("name"));
            }
            
            rs.close();
            ps.close();
            return transactionStatus;
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
