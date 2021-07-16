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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Note>> getNotes() {
    	List<Note> notes = notesService.getAllNotes();
    	 HttpHeaders httpHeaders = new HttpHeaders();
         httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
         
         ResponseEntity<List<Note>> serverResponse = new ResponseEntity<List<Note>>(notes, httpHeaders, HttpStatus.OK);
         return serverResponse;
    }
    
	@PostMapping("/notes")
	public String createOrUppdate(Authentication authentication, Note note, Model model) {

		String username = (String) authentication.getPrincipal();
		User user = userService.getUser(username);

		String createError = null;

		if (user == null) {
			createError = "No user found for this note! ";
			
			model.addAttribute("error", createError);
		} 
	
		
		Integer output = -1;
		if (note.getNoteid() != null) { // create or update?
			logger.info("Note in update: {}", note);
			note.setUserid(user.getUserid());
			output = notesService.updateNote(note);

		} else {
			 if(notesService.noteExists(note.getNotetitle())) {
				 Note noteLoc = notesService.getNotesByTitle(note.getNotetitle());
				 noteLoc.setNotedescription(note.getNotedescription());
				 logger.info("Note in update second logging: {}", noteLoc);
				 output = notesService.updateNote(noteLoc);
			 } else {
				 logger.info("Note in add: {}", note);
					note.setUserid(user.getUserid());
					output = notesService.addNote(note, user.getUserid()); 
			 }
		}
		
		if(output < 0) {
			createError = "Error on insert or update! ";
			
			model.addAttribute("error", createError);
			return "result";
		} else {
			model.addAttribute("success", true);
			return "result";
		}
		
	}
	
	@GetMapping("/notes/delete")
	public String deleteCredential(@RequestParam("id") int id, Model model) {
		
		logger.info("Note id in delete: {}", id);
	
		if(!notesService.noteExists(id)) {
			model.addAttribute("error", "Note does not exist");
		    return "result";
		}
		
		Integer output = notesService.deleteNote(id);
		
		if(output >= 0) {
			model.addAttribute("success", true);
			return "result";
		}
		else {
			model.addAttribute("error", "Delete failed");
			return "result";
		}
	}
}
