package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
@Repository
public interface NoteMapper {
	@Select("SELECT * FROM NOTES")
	List<Note> findAllNotes();

	@Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
	Note findByNoteId(int noteid);

	@Select("SELECT * FROM NOTES WHERE userid = #{userid}")
	List<Note> findByUserId(int userid);
	
	@Select("SELECT * FROM NOTES WHERE notetitle = #{notetitle}")
	Note findByTitle(String notetitle);
	
	@Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
	public int insertNotes(Note note);
	
	@Delete("DELETE FROM NOTES WHERE notetitle = #{notetitle}")
	public int deleteByTitle(String notetitle);
	
	@Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
	public int deleteById(int noteid);
	
	@Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
	public int updateNote(Note note);
}
