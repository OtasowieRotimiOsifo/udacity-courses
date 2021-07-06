package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

@Controller
public class FilesController {
	@Autowired
	private FileService fileService;
	
	@PostMapping("/files")
	public String addFile(Authentication authentication, MultipartFile multipartFile) {
		
		User user = (User) authentication.getPrincipal();
		
		String result = "";
		
		if(multipartFile.isEmpty()) {
			result = "redirect:/result?error";
		}
		
		Integer output = fileService.addFile(multipartFile, user.getUserid());
		
		if(output >= 0) {
			result = "redirect:/result?success";
		}
		else {
			result = "redirect:/result?error";
		}
	
		return result;
	}
	
	@GetMapping("/files/delete")
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
