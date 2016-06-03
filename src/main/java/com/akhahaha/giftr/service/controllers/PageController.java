package com.akhahaha.giftr.service.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PageController {
	
	/**
	 * Depending on whether the user is already authenticated, dispatch the home page or the login page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView rootDispatcher(
			@RequestParam(value = "error", required = false) String error) {
		
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
	    if (auth instanceof AnonymousAuthenticationToken) {
	    	if (error != null)
	    		model.addObject("error", true);
	    	model.setViewName("index");
	    } else {
	    	model.setViewName("redirect:/about_me.html");
	    }
	    return model;
	}

	/**
	 * Depending on whether the user is already authenticated, dispatch the home page or the signup page
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUpDispatcher(
			@RequestParam(value = "error", required = false) String emptyUsername,
			@RequestParam(value = "error", required = false) String emptyPassword,
			@RequestParam(value = "error", required = false) String internalError){

		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
	    if (auth instanceof AnonymousAuthenticationToken) {
	    	if (emptyUsername != null) model.addObject("emptyUsername", true);
	    	if (emptyPassword != null) model.addObject("emptyPassword", true);
	    	if (internalError != null) model.addObject("internalError", true);
	    	model.setViewName("signup");
	    } else {
	    	model.setViewName("redirect:/");
	    }
	    return model;
	}
}
