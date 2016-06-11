package com.akhahaha.giftr.service.data.models;

import com.akhahaha.giftr.service.View;
import com.fasterxml.jackson.annotation.JsonView;

public class AuthenticationPair {
    @JsonView(View.Summary.class)
    private String username;
    @JsonView(View.Summary.class)
    private String password;
    
    public AuthenticationPair() {
    }
    
    public AuthenticationPair(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
