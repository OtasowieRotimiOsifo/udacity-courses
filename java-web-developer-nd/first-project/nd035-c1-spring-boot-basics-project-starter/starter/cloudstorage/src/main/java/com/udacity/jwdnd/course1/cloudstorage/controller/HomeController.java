package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private CredentialService credentialService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public ModelAndView getHomePage(Authentication authentication) throws Exception {
		//(User) authentication.getPrincipal();
		
		String username = (String)authentication.getPrincipal();
		User user = userService.getUser(username);
		
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("Notes", noteService.getNotesByuser(user.getUserid()));
		modelAndView.addObject("credentials", credentialService.getCredentialsForUser(username));
		modelAndView.addObject("files", fileService.findByUserId(username));
		
		return modelAndView;
	}
}
