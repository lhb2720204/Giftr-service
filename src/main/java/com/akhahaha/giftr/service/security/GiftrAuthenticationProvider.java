
package com.akhahaha.giftr.service.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.akhahaha.giftr.service.data.dao.AuthenticationDAO;
import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.AuthenticationPair;
import com.akhahaha.giftr.service.data.models.User;


 
@Component
public class GiftrAuthenticationProvider implements AuthenticationProvider{

	private AuthenticationDAO authenticationDAO = (AuthenticationDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.AUTHENTICATION);
	
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString().trim();
        
        AuthenticationPair pair = authenticationDAO.getPairByUsername(username);
   
        if (pair != null && pair.getPassword().equals(password)) {
        	return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } 
        else {
        	return null;
        }
    }

    public boolean supports(Class<?> authentication) {
    	return true;
    }
}
