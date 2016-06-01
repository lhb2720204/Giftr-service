package com.akhahaha.giftr.service.data.models;

import com.akhahaha.giftr.service.View;
import com.fasterxml.jackson.annotation.JsonView;

public class TransactionStatus {
    public static final TransactionStatus PENDING = new TransactionStatus(1, "Pending");
    public static final TransactionStatus PLACED = new TransactionStatus(2, "Placed");
    public static final TransactionStatus SHIPPED = new TransactionStatus(3, "Shipped");
    public static final TransactionStatus DELIVERED = new TransactionStatus(4, "Delivered");
    
    @JsonView(View.Summary.class)
    private Integer id;
    @JsonView(View.Summary.class)
    private String name;
    
    public TransactionStatus() {
    }
    
    public TransactionStatus(Integer id) {
        this.id = id;
        switch (id) {
        case 1:
            this.name = "Pending";
            break;
        case 2:
            this.name = "Placed";
            break;
        case 3:
            this.name = "Shipped";
            break;
        case 4:
            this.name = "Delivered";
            break;
        default:
            this.name = "Pending";
            break;
        }
    }
    
    public TransactionStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
    
}
