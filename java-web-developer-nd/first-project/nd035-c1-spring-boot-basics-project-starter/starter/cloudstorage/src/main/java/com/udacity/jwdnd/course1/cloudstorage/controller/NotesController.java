package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@Controller
public class NotesController {
	
	@Autowired
	private NoteService notesService;
	
	@PostMapping("/notes")
	public String createOrUppdate(Authentication authentication, Note note) {
		String result = "";
		Integer output = -1;
		
		if(notesService.noteExists(note.getNoteId())) {
			output = notesService.updateNote(note);
		} else {
			output = notesService.addNote(note);
		}
		
		if(output < 0) {
			result = "redirect:/result?error";
		} else {
			result = "redirect:/result?success";
		}
		
		return result;
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
