package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;

import lombok.Getter;
import lombok.Setter;


@Mapper
@Repository
public interface NotesMapper {
	@Select("SELECT * FROM NOTES")
	List<Notes> findAllFiles();

	@Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
	Notes findByFileId(int noteid);

	@Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
	List<Notes> findByUserId(int noteid);
	
	@Select("SELECT * FROM NOTES WHERE notetitle = #{notetitle}")
	Notes findByFileName(String notetitle);
	/*
	 * ;
	private @Getter @Setter int ;
	private @Getter @Setter String notedescription
	 */
	@Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
	public int insertFiles(Notes notes);
	
	@Delete("DELETE FROM NOTES WHERE notetitle = #{notetitle}")
	public int deleteByFileId(String notetitle);
	
	@Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
	public int updateUser(Notes notes);
}
