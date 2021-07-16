package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.util.List;

@Service
public class NoteService {
	private final NoteMapper noteMapper;
	
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
    
    public Note getNotesByTitle(String title) {
        return noteMapper.findByTitle(title);
    }
    
    public Note getNotesById(int noteid) {
        return noteMapper.findByNoteId(noteid);
    }
     
    public List<Note> getNotesByuser(int userid) {
        return noteMapper.findByUserId(userid);
    }
    
    public List<Note> getAllNotes() {
        return noteMapper.findAllNotes();
    }
    
    public Integer addNote(Note note) {      
        return noteMapper.insertNotes(new Note(null, note.getNotetitle(), note.getNotedescription(), note.getUserid()));
    }

    public Integer addNote(Note note, int userid) {      
        return noteMapper.insertNotes(new Note(null, note.getNotetitle(), note.getNotedescription(), userid));
    }
    
    public Integer updateNote(Note note,  int userid) { 
        note.setUserid(userid);
        return noteMapper.updateNote(note);
    }
    
    public Integer updateNote(Note note) { 
        return noteMapper.updateNote(note);
    }
    
    public Integer deleteNote(String title) {
    	return noteMapper.deleteByTitle(title);
    }
    
    public Integer deleteNote(int noteid) {
    	return noteMapper.deleteById(noteid);
    }
    
    public boolean noteExists(int noteid, String title) {
    	if(noteMapper.findByNoteId(noteid) != null) {
    		return true;
    	} else if (noteMapper.findByTitle(title) != null) {
    		return true;
    	}
    	return false;
    }
    
    public boolean noteExists(String title) {
    	if (noteMapper.findByTitle(title) != null) {
    		return true;
    	}
    	return false;
    }
    
    public boolean noteExists(int noteid) {
    	if(noteMapper.findByNoteId(noteid) != null) {
    		return true;
    	} 
    	return false;
    }
}
