package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import java.util.List;

@Service
public class NoteService {
	private final NoteMapper noteMapper;
	private final UserMapper userMapper;
	
    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }
    
    public Note getNotesByTitle(String title) {
        return noteMapper.findByTitle(title);
    }
    
    public Note getNotesById(int noteid) {
        return noteMapper.findByNoteId(noteid);
    }
    
    public List<Note> getNotesByuser(String username) {
    	Integer userid = userMapper.findByUserName(username).getUserid(); 
        return noteMapper.findByUserId(userid);
    }
    
    public List<Note> getAllNotes() {
        return noteMapper.findAllNotes();
    }
    
    public Integer addNote(Note note) {      
        return noteMapper.insertNotes(new Note(null, note.getNotetitle(), note.getNotedescription(), note.getUserid()));
    }

    public Integer updateNote(Note note) {      
        return noteMapper.updateNote(note);
    }
    
    public Integer deleteNote(String title) {
    	return noteMapper.deleteByTitle(title);
    }
    
    public Integer deleteNote(int noteid) {
    	return noteMapper.dele;
    }
    
    public boolean noteExists(int noteid) {
    	return noteMapper.findByNoteId(noteid) != null;
    }
}
