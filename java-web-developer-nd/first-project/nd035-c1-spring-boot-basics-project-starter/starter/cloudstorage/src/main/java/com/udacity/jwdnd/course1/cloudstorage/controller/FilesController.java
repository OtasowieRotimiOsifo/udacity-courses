package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.model.FileObject;
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
	
	@GetMapping()
    public String resultView() {
        return "redirect:/result";
    }
	
	
	@RequestMapping(value = {"/files/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> view(@PathVariable(name = "id") String id) {
    	
    	FileObject fileObject = fileService.findByFileId(Integer.parseInt(id));
        byte[] fileContents = fileObject.getFiledata();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(fileObject.getContenttype()));
        String fileName = fileObject.getFilename();
        httpHeaders.setContentDispositionFormData(fileName, fileName);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        ResponseEntity<byte[]> serverResponse = new ResponseEntity<byte[]>(fileContents, httpHeaders, HttpStatus.OK);
        return serverResponse;
    }

	
	@PostMapping("/files")
	public String addFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, Model model) {
		
		logger.info("multipartFile file name: = {}", multipartFile.getOriginalFilename());
		logger.info("authentication = {}", authentication);
		String username = (String)authentication.getPrincipal();
		User user = userService.getUser(username);
		
		 String signupError = null;
		 
		if(multipartFile != null && multipartFile.isEmpty()) {
			signupError = "input file is empty or null " + multipartFile.getOriginalFilename() + "!";
			model.addAttribute("error", signupError);
		} else {
			Integer output = fileService.addFile(multipartFile, user.getUserid());
			if(output >= 0) {
				signupError = null; 
			}
			else {
				signupError = "There was error in upload of the file: " + multipartFile.getOriginalFilename() + "!";
				model.addAttribute("saveError", signupError);
			}
		}
		
		
		if (signupError == null) {
            model.addAttribute("success", true);
        } 
		
		return "result";
	}
	
	 @RequestMapping(value = {"/files/delete/{id}"}, method = RequestMethod.GET)
	public String deleteFile(@PathVariable(name = "id") String id) {
		
		String result = "";
		
		Integer idLoc = Integer.parseInt(id);
		Integer output = fileService.deleteFileById(idLoc);
		
		if(output >= 0) {
			result = "home";
		}
		else {
			result = "home";
		}
	
		return result;
	}
}
