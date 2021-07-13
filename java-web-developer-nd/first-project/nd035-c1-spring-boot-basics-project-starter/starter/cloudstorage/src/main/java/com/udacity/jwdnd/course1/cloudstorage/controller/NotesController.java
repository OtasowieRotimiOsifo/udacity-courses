package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
public class NotesController {
	
	Logger logger = LoggerFactory.getLogger(NotesController.class);
	
	@Autowired
	private NoteService notesService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/notes")
	public String createOrUppdate(Authentication authentication, Note note, Model model) {
		logger.info("Note: {}", note);
		
		String username = (String) authentication.getPrincipal();
		User user = userService.getUser(username);

		logger.info("User: {}", user);
		
		String signupError = null;

		if (user == null) {
			signupError = "No user found for this note! ";
			
			model.addAttribute("error", signupError);
		} 
	
		
		Integer output = -1;
		if(note.getNoteId() != null) {
			if(notesService.noteExists(note.getNoteId())) {
				
				output = notesService.updateNote(note);
				
			} else {
				
				output = notesService.addNote(note, user.getUserid());
				
			}
		} else {
			
			output = notesService.addNote(note, user.getUserid());
			
		}
		
		if(output < 0) {
			signupError = "Error on insert or update! ";
			
			model.addAttribute("error", signupError);
			return "result";
		} else {
			model.addAttribute("success", true);
			return "result";
		}
		
	}
	
	@GetMapping("/notes/delete")
	public String deleteCredential(int noteid) {
		
		String result = "";
		
		if(!notesService.noteExists(noteid)) {
			result = "redirect:/result?error";
		}
		
		Integer output = notesService.deleteNote(noteid);
		
		if(output >= 0) {
			result = "redirect:/result?success";
		}
		else {
			result = "redirect:/result?error";
		}
	
		return result;
	}
}
