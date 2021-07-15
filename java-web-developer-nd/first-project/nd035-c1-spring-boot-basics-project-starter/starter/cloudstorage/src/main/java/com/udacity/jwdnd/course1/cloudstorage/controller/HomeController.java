package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

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
	public ModelAndView home(Authentication authentication) throws Exception {
		
		String username = (String)authentication.getPrincipal();
		User user = userService.getUser(username);
		
		ModelAndView modelAndView = new ModelAndView("home");
		if(user != null) {
			modelAndView.addObject("notes", noteService.getNotesByuser(user.getUserid()));
			modelAndView.addObject("credentials", credentialService.getCredentialsForUser(user.getUserid()));
			modelAndView.addObject("files", fileService.findByUserId(user.getUserid()));
			
		}
		
		
		return modelAndView;
	}
	
}
