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

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private CredentialService credentialService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping()
	public ModelAndView getHomePage(Authentication authentication) throws Exception {
		User user = (User) authentication.getPrincipal();
		
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("Notes", noteService.getNotesByuser(user.getUsername()));
		modelAndView.addObject("credentials", credentialService.getCredentialsForUser(user.getUsername()));
		modelAndView.addObject("files", fileService.findByUserId(user.getUsername()));
		
		return modelAndView;
	}
}
