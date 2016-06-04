package com.akhahaha.giftr.service.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PageController {
	
	/**
	 * Depending on whether the user is already authenticated, dispatch the home page or the login page
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView rootDispatcher(
			@RequestParam(required = false) String error) {
		
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
	    if (auth instanceof AnonymousAuthenticationToken) {
	    	if (error != null)
	    		model.addObject("error", true);
	    	model.setViewName("index");
	    } else {
	    	model.setViewName("redirect:/about_me");
	    }
	    return model;
	}

	/**
	 * Depending on whether the user is already authenticated, dispatch the home page or the signup page
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUpDispatcher(){

		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
	    if (auth instanceof AnonymousAuthenticationToken) {
	    	model.setViewName("signup");
	    } else {
	    	model.setViewName("redirect:/about_me");
	    }
	    return model;
	}
	
	@RequestMapping(value = "/about_me", method = RequestMethod.GET)
	public ModelAndView aboutMeDispatcher(){
		ModelAndView model = new ModelAndView();
		model.setViewName("about_me");
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/about_me/image")
	public ModelAndView handleFileUpload(@RequestParam MultipartFile file,
								   Principal principal) {
		ModelAndView model = new ModelAndView();
		model.setViewName("about_me");
		
		String username = principal.getName();
		String filename = file.getOriginalFilename();
		String ext = "";

		// get file extension
		int i = filename.lastIndexOf('.');
		if (i > 0) {
		    ext = filename.substring(i+1);
		}
		
		if (filename.contains("/")) {
			model.addObject("separatorError", true);
			return model;
		}
		if (ext.equalsIgnoreCase("jpg")) {
			try {
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/webapp/image" + "/" + username + ".jpg")));
                FileCopyUtils.copy(file.getInputStream(), stream);
				stream.close();
			}
			catch (Exception e) {
				model.addObject("fileError", true);
				return model;
			}
		}
		else {
			model.addObject("fileError", true);
			return model;
		}

		model.addObject("fileSuccess", true);
		return model;
	}
}
