package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileObject;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
	private final FileMapper fileMapper;
	private final UserMapper userMapper;

	public FileService(UserMapper userMapper, FileMapper fileMapper) {
		this.userMapper = userMapper;
		this.fileMapper = fileMapper;
	}

	public FileObject findByFileName(String filename) {
		return fileMapper.findByFileName(filename);
	}

	public List<FileObject> findByUserId(String username) {
		Integer userid = userMapper.findByUserName(username).getUserid();
		return fileMapper.findByUserId(userid);
	}

	public Integer addFile(MultipartFile multiFileObject, String username) {
		FileObject fileObject = new FileObject();
		try {

			fileObject.setContenttype(multiFileObject.getContentType());

			fileObject.setFileid(null);

			fileObject.setFiledata(multiFileObject.getBytes());

			fileObject.setFilename(multiFileObject.getOriginalFilename());

			Long fileSize = multiFileObject.getSize();
			fileObject.setFilesize(fileSize.toString());

			fileObject.setUserid(userMapper.findByUserName(username).getUserid());

			return fileMapper.insertFile(fileObject);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Integer deleteFile(String fileName) {
		return fileMapper.deleteByFileId(fileName);
	}
}
