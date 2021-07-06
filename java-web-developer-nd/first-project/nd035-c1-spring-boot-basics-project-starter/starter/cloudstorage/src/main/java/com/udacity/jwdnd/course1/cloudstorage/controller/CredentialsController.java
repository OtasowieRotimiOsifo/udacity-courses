package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

@Controller
public class CredentialsController {
	@Autowired
	private CredentialService credentialService;
	
	@PostMapping("/credentials")
	public String createOrUppdate(Authentication authentication, Credential credential) {
		String result = "";
		Integer output = -1;
		
		if(credentialService.credentialExists(credential.getCredentialid())) {
			output = credentialService.updatecredential(credential);
		} else {
			output = credentialService.addCredential(credential);
		}
		
		if(output < 0) {
			result = "redirect:/result?error";
		} else {
			result = "redirect:/result?success";
		}
		
		return result;
	}
	
	@GetMapping("/credentials/delete")
	public String deleteCredential(int credentialid) {
		
		String result = "";
		
		if(!credentialService.credentialExists(credentialid)) {
			result = "redirect:/result?error";
		}
		
		Integer output = credentialService.deleteCredential(credentialid);
		
		if(output >= 0) {
			result = "redirect:/result?success";
		}
		else {
			result = "redirect:/result?error";
		}
	
		return result;
	}
}
