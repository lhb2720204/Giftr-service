package com.akhahaha.giftr.service.data.models;

import java.util.Date;

import com.akhahaha.giftr.service.View;
import com.fasterxml.jackson.annotation.JsonView;

public class Transaction {
    @JsonView(View.Summary.class)
	private Integer id;
    @JsonView(View.Summary.class)
	private Integer buyerId;
    @JsonView(View.Summary.class)
	private Integer productSource;
    @JsonView(View.Summary.class)
	private Long productSourceId;
    @JsonView(View.Summary.class)
	private Long creditCard;
    @JsonView(View.Summary.class)
	private String paypal;
    @JsonView(View.Summary.class)
	private String venmo;
    @JsonView(View.Summary.class)
	private String billingAddress;
    @JsonView(View.Summary.class)
	private String shippingAddress;
    @JsonView(View.Summary.class)
	private String senderMessage;
    @JsonView(View.Summary.class)
	private TransactionStatus status;
    @JsonView(View.Summary.class)
	private Date created;
    @JsonView(View.Summary.class)
	private Date lastModified;
    
    public Transaction() {
    	status = TransactionStatus.PENDING;
        Date currentDate = new Date();
        created = currentDate;
        lastModified = currentDate;
    }
    
	public Transaction(Integer id, Integer buyerId, Integer productSource, Long productSourceId, Long creditCard,
			String paypal, String venmo, String billingAddress, String shippingAddress, String senderMessage,
			TransactionStatus status, Date created, Date lastModified) {
		this.id = id;
		this.buyerId = buyerId;
		this.productSource = productSource;
		this.productSourceId = productSourceId;
		this.creditCard = creditCard;
		this.paypal = paypal;
		this.venmo = venmo;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
		this.senderMessage = senderMessage;
		this.status = status;
		this.created = created;
		this.lastModified = lastModified;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBuyerId() {
		return buyerId;
	}
	
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
	public Integer getProductSource() {
		return productSource;
	}
	
	public void setProductSource(Integer productSource) {
		this.productSource = productSource;
	}
	
	public Long getProductSourceId() {
		return productSourceId;
	}
	
	public void setProductSourceId(Long productSourceId) {
		this.productSourceId = productSourceId;
	}
	
	public Long getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(Long creditCard) {
		this.creditCard = creditCard;
	}
	
	public String getPaypal() {
		return paypal;
	}
	
	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
	
	public String getVenmo() {
		return venmo;
	}
	
	public void setVenmo(String venmo) {
		this.venmo = venmo;
	}
	
	public String getBillingAddress() {
		return billingAddress;
	}
	
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	public String getShippingAddress() {
		return shippingAddress;
	}
	
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	public String getSenderMessage() {
		return senderMessage;
	}
	
	public void setSenderMessage(String senderMessage) {
		this.senderMessage = senderMessage;
	}
	
	public TransactionStatus getStatus() {
		return status;
	}
	
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
