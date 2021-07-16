package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.udacity.jwdnd.course1.cloudstorage.config.PropertiesUtility;
import com.udacity.jwdnd.course1.cloudstorage.model.FileObject;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class FilesController implements HandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Autowired
	private FileService fileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	PropertiesUtility propertiesUtility;
	
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
	public String addFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile,
			Model model) {
       try {
		logger.info("multipartFile file name: = {}", multipartFile.getOriginalFilename());
		logger.info("authentication = {}", authentication);
		String username = (String) authentication.getPrincipal();
		User user = userService.getUser(username);

		String uploadError = null;

		/*int max_size = Integer.parseInt( propertiesUtility.getProperty("spring.servlet.multipart.max-request-size").replaceAll("[^0-9]", ""));
		if(multipartFile.getSize() > max_size) {
			uploadError = "The file is larger than the allowed size! ";
			
			model.addAttribute("uploadError", uploadError);
			return "result";
		}*/
		
		if (user == null) {
			uploadError = "No user found for this file! ";
			
			model.addAttribute("error", uploadError);
			return "result";
		} else if (multipartFile != null && multipartFile.isEmpty()) {
			uploadError = "No file selected for upload! ";
			
			model.addAttribute("emptyUpload", uploadError);
			return "result";
		} else {
			// check for existing file or duplicate
			List<FileObject> fileObjects = fileService.findByUserId(user.getUserid());
			
			boolean exists = false;
			for (FileObject fileObject : fileObjects) {
		
				if (fileObject.getFilename().compareToIgnoreCase(multipartFile.getOriginalFilename()) == 0) {
					exists = true;
				}
			}
			
			if (exists == true) {
				uploadError = "The file already exists!";
			    
				model.addAttribute("error", uploadError);
				return "result";
			} else {
				Integer output = fileService.addFile(multipartFile, user.getUserid());
				if (output >= 0) {
					model.addAttribute("success", true);
					return "result";
				} else {
					uploadError = "There was error in upload of the file: " + multipartFile.getOriginalFilename() + "!";
					model.addAttribute("saveError", uploadError);
					return "result";
				}
			}
		}
       } catch(Exception e) {

			model.addAttribute("uploadError", "The file is larger than the allowed size! ");
			return "result";
       }
	}
	
	@Override
	public ModelAndView resolveException( //Baeldung
	  HttpServletRequest request,
	  HttpServletResponse response, 
	  Object object,
	  Exception exc) {   
	     
	    ModelAndView modelAndView = new ModelAndView("result");
	    String max_size = propertiesUtility.getProperty("spring.servlet.multipart.max-request-size");
	    if (exc instanceof MaxUploadSizeExceededException) {
	        modelAndView.getModel().put("uploadError", "File size exceeds limit configure limit of: " + max_size);
	    }
	    return modelAndView;
	}
	
	@RequestMapping(value = {"/files/delete/{id}"}, method = RequestMethod.GET)
	public String deleteFile(@PathVariable(name = "id") String id, Model model) {
		logger.info("File id = {}", id);
		String result = "";
		
		Integer idLoc = Integer.parseInt(id);
		Integer output = fileService.deleteFileById(idLoc);
		
		if(output >= 0) {
			model.addAttribute("success", true);
			result = "result";
		}
		else {
			model.addAttribute("error", "Error in deleting the file");
			result = "result";
		}
	
		return result;
	}
}
