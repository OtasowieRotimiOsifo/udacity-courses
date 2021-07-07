package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class FilesController {
	Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/files")
	public String addFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
		
		//(User) authentication.getPrincipal();
		logger.info("multipartFile = {}", multipartFile);
		logger.info("authentication = {}", authentication);
		String username = (String)authentication.getPrincipal();
		User user = userService.getUser(username);
		
		String result = "";
		 String signupError = null;
		 
		if(multipartFile != null && multipartFile.isEmpty()) {
			result = "redirect:/result?error";
			signupError = "input file is empty or null " + multipartFile.getOriginalFilename() + "!";
			redirectAttributes.addFlashAttribute("uploadError", signupError);
		}
		
		Integer output = fileService.addFile(multipartFile, user.getUserid());
		
		if(output >= 0) {
			signupError = "The file was successfully uploaded " + multipartFile.getOriginalFilename() + "!";
			redirectAttributes.addFlashAttribute("uploadSuccess", signupError);
			result = "redirect:/result?success";
		}
		else {
			signupError = "There was error in upload of the file: " + multipartFile.getOriginalFilename() + "!";
			redirectAttributes.addFlashAttribute("uploadError", signupError);
			result = "redirect:/result?error";
		}
	
		return result;
	}
	
	@PostMapping("/files/delete")
	public String deleteFile(String filename) {
		
		String result = "";
		
		if(filename.isBlank() && filename.isEmpty()) {
			result = "redirect:/result?error";
		}
		
		Integer output = fileService.deleteFile(filename);
		
		if(output >= 0) {
			result = "redirect:/result?success";
		}
		else {
			result = "redirect:/result?error";
		}
	
		return result;
	}
}
