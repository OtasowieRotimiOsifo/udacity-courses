package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.udacity.jwdnd.course1.cloudstorage.model.FileObject;

@Mapper
@Repository
public interface FileMapper {
	@Select("SELECT * FROM FILES")
	List<FileObject> findAllFiles();

	@Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
	FileObject findByFileId(int fileid);

	@Select("SELECT * FROM FILES WHERE userid = #{userid}")
	List<FileObject> findByUserId(int userid);
	
	@Select("SELECT * FROM FILES WHERE filename = #{filename}")
	FileObject findByFileName(String filename);
	
	@Insert("INSERT INTO FILES (filename, filesize, contenttype, userid, filedata) VALUES(#{filename}, #{filesize}, #{contenttype}, #{userid}, #{filedata})")
	public int insertFile(FileObject fileObject);
	
	@Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
	public int deleteByFileId(int id);
	
	@Update("UPDATE FILES SET filename = #{filename}, filesize = #{filesize}, contenttype = #{contenttype}, userid = #{userid}, lastname = #{lastname} WHERE fileid = #{fileid}")
	public int updateFile(FileObject fileObject);
}
