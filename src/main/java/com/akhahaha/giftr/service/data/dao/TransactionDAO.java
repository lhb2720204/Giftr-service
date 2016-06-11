package com.akhahaha.giftr.service.data.dao;

import java.util.List;

import com.akhahaha.giftr.service.data.models.Transaction;
import com.akhahaha.giftr.service.data.models.TransactionStatus;

public interface TransactionDAO extends DAO {
    /**
     * Inserts a new Transaction
     *
     * @param user Transaction data to insert
     * @return The generated Transaction ID
     */
    Integer insertTransaction(Transaction transaction);
    
    void updateTransaction(Transaction transaction);
    
    void deleteTransaction(Integer transactionID);
    
    Transaction getTransaction(Integer transactionID);
    
    List<Transaction> getTransactionsByBuyer(Integer buyerID);
    
    TransactionStatus getTransactionStatus(Integer transactionStatusID);
}
