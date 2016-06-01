package com.akhahaha.giftr.service.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.akhahaha.giftr.service.data.dao.TransactionDAO;
import com.akhahaha.giftr.service.data.models.Transaction;
import com.akhahaha.giftr.service.data.models.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonView;
import com.akhahaha.giftr.service.View;
import com.akhahaha.giftr.service.data.dao.DAOManager;

@CrossOrigin
@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private TransactionDAO transactionDAO = (TransactionDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.TRANSACTION);
    
    /**
     * Search for transactions by buyer
     */
    @JsonView(View.Summary.class)
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> searchTransactionsByBuyer(@RequestParam Integer buyerID) {
    	List<Transaction> transactions;
    	
    	transactions = transactionDAO.getTransactionsByBuyer(buyerID);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(transactions, headers, HttpStatus.OK);

    }
    		
    @JsonView(View.Summary.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTransaction(
    		@RequestParam Integer buyerID,
    		@RequestParam Integer productSource,
    		@RequestParam Long productSourceId,
    		@RequestParam (required = false) Long creditCard,
    		@RequestParam (required = false) String paypal,
    		@RequestParam (required = false) String venmo,
    		@RequestParam (required = false) String billingAddress,
    		@RequestParam String shippingAddress,
    		@RequestParam (required = false) String senderMessage,
    		@RequestParam (defaultValue = "1") Integer status
    		) {
    	if (creditCard == null && paypal == null && venmo == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create transaction. No payment options provided.");
    	}
    	
    	Transaction transaction = new Transaction();
    	setTransactionFields(transaction, buyerID, productSource, productSourceId, creditCard, paypal,
    			venmo, billingAddress, shippingAddress, senderMessage, status);
    	
    	Integer transactionID = transactionDAO.insertTransaction(transaction);
    	if (transactionID == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create transaction.");
        }
    	
    	transaction = transactionDAO.getTransaction(transactionID);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{transactionID}")
                .buildAndExpand(transactionID).toUri());
        return new ResponseEntity<>(transaction, headers, HttpStatus.CREATED);
    }
    
    /**
     * Returns the specified Transaction
     */
    @JsonView(View.Detailed.class)
    @RequestMapping(value = "/{transactionID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getTransaction(
            @PathVariable Integer transactionID) {
        validateTransactionExists(transactionID);

        Transaction transaction = transactionDAO.getTransaction(transactionID);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(transactionID).toUri());
        return new ResponseEntity<>(transaction, headers, HttpStatus.OK);
    }
    
    /**
     * Updates the specified Transaction
     */
    @JsonView(View.Detailed.class)
    @RequestMapping(value = "/{transactionID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateTransaction(
            @PathVariable Integer transactionID,
            @RequestParam(required = false) Integer buyerID,
            @RequestParam(required = false) Integer productSource,
            @RequestParam(required = false) Long productSourceId,
            @RequestParam(required = false) Long creditCard,
            @RequestParam(required = false) String paypal,
            @RequestParam(required = false) String venmo,
            @RequestParam(required = false) String billingAddress,
            @RequestParam(required = false) String shippingAddress,
            @RequestParam(required = false) String senderMessage,
            @RequestParam(required = false) Integer status
            ) {
    	
    	validateTransactionExists(transactionID);
    	
    	Transaction transaction = transactionDAO.getTransaction(transactionID);
    	setTransactionFields(transaction, buyerID, productSource, productSourceId, creditCard, paypal, venmo,
    			billingAddress, shippingAddress, senderMessage, status);
    	transactionDAO.updateTransaction(transaction);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(transactionID).toUri());
        return new ResponseEntity<>(transaction, headers, HttpStatus.OK);

    }

    private void validateTransactionExists(Integer transactionID) {
    	if (transactionDAO.getTransaction(transactionID) == null) {
    		throw new TransactionNotFoundException(transactionID);
    	}
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class TransactionNotFoundException extends RuntimeException {
    	TransactionNotFoundException(Integer transactionID) {
    		super("Could not find transaction '" + transactionID + "'.");
    	}
    }
    
    private void setTransactionFields(Transaction transaction, Integer buyerID, Integer productSource,
    		Long productSourceId, Long creditCard, String paypal, String venmo, String billingAddress,
    		String shippingAddress, String senderMessage, Integer status) {
    	if (buyerID != null) transaction.setBuyerId(buyerID);
    	if (productSource != null) transaction.setProductSource(productSource);
    	if (productSourceId != null) transaction.setProductSourceId(productSourceId);
    	if (creditCard != null) transaction.setCreditCard(creditCard);
    	if (paypal != null) transaction.setPaypal(paypal);
    	if (venmo != null) transaction.setVenmo(venmo);
    	if (billingAddress != null) transaction.setBillingAddress(billingAddress);
    	if (shippingAddress != null) transaction.setShippingAddress(shippingAddress);
    	if (senderMessage != null) transaction.setSenderMessage(senderMessage);
    	if (status != null) transaction.setStatus(new TransactionStatus(status));
    }
}
