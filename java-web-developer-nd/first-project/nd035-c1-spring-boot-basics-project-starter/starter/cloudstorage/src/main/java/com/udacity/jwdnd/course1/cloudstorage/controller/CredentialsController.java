package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class CredentialsController {
	Logger logger = LoggerFactory.getLogger(CredentialsController.class);
	
	@Autowired
	private CredentialService credentialService;
	

	@Autowired
	private UserService userService;
	
	@PostMapping("/credentials")
	public String createOrUppdate(Authentication authentication, Credential credential, Model model) {
	
		Integer output = -1;
		
		String username = (String) authentication.getPrincipal();
		User user = userService.getUser(username);
		String signupError = null;

		if (user == null) {
			signupError = "No user found for this credential! ";
			
			model.addAttribute("error", signupError);
			return "result";
		} 
		
		int userid = user.getUserid();
		credential.setUserid(userid);
		logger.info("credential: {}", credential);
		if(credentialService.credentialExists(credential.getCredentialid())) {
			output = credentialService.updatecredential(credential, userid);
		} else {
			output = credentialService.addCredential(credential);
		}
		
		if(output < 0) {
			signupError = "unable to save credential! ";
			model.addAttribute("error", signupError);
			
			return "result";
		} else {
			model.addAttribute("success", true);
			return "result";
		}
	}
	
	@GetMapping("/credentials/delete")
	public String deleteCredential(@RequestParam("id") int credentialid, Model model) {
		
		if(!credentialService.credentialExists(credentialid)) {
			model.addAttribute("error", "Credential does not exist");
			return "result";
		}
		
		Integer output = credentialService.deleteCredential(credentialid);
		
		if(output >= 0) {
			model.addAttribute("success", true);
			return "result";
		}
		else {
			model.addAttribute("error", "Unable to save your credential changes");
			return "result";
		}
	}
}
