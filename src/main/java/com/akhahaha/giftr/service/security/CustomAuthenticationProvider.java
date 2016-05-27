
package com.akhahaha.giftr.service.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.UserDAO;
import com.akhahaha.giftr.service.data.models.User;


 
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	private UserDAO userDAO = (UserDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.USER);
	
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString().trim();
        
        User user = userDAO.getUserByUsername(username);
   
          if (user != null) {
          	
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
