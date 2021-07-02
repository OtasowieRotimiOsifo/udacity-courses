package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.security.SecureRandom;
//import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class CredentialService {
	@Autowired
	private CredentialMapper credentialmapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EncryptionService encryptionService;
	
	public CredentialService(CredentialMapper credentialmapper, UserMapper userMapper, EncryptionService encryptionService) {
		this.credentialmapper = credentialmapper;
		this.userMapper = userMapper; 
		this.encryptionService = encryptionService;
	}

	public String decryptPassword(String encodedPassword, String key) {
		return encryptionService.decryptValue(encodedPassword, key);
	}
	
	public List<Credential> getCredentialsForUser(int userid) {
		List<Credential> credentials = credentialmapper.findByUserId(userid);
	    //List<Credential> credentialsOut = new ArrayList<>();
	    for(Credential credential : credentials) {
	    	credential.setPassword(decryptPassword(credential.getPassword(), credential.getKey()));
	    }
	    return credentials;
	}
	
	public List<Credential> getCredentialsForUser(String username) {
		List<Credential> credentials = credentialmapper.findByUserName(username);
		//List<Credential> credentialsOut = new ArrayList<>();
	    for(Credential credential : credentials) {
	    	credential.setPassword(decryptPassword(credential.getPassword(), credential.getKey()));
	    }
	    return credentials;
	}
	
	public Integer addCredential(Credential credential) {
	
		String encryptedPassword = encryptPassword(credential.getPassword());
		credential.setPassword(encryptedPassword);
		
		Integer userid = userMapper.findByUserName(credential.getUsername()).getUserid();
		credential.setUserid(userid);
		
		return credentialmapper.insertCredential(credential);
	}
	
	public Integer updatecredential(Credential credential) {
		
		String encryptedPassword = encryptPassword(credential.getPassword());
		credential.setPassword(encryptedPassword);
		
		Integer userid = userMapper.findByUserName(credential.getUsername()).getUserid();
		credential.setUserid(userid);
		
		return credentialmapper.updateCredential(credential);
	}
	
	public Integer deleteCredential(int credentialId) {
		return credentialmapper.deleteByCredentialId(credentialId);
	}
	
	private String encryptPassword(String plainTextPassword) {
		String key = generateRandomBase64Token(16);
		return encryptionService.encryptValue(plainTextPassword, key);
	}
	
	private String generateRandomBase64Token(int byteLength) { //stackoverflow
	    SecureRandom secureRandom = new SecureRandom();
	    byte[] token = new byte[byteLength];
	    secureRandom.nextBytes(token);
	    return Base64.getUrlEncoder().withoutPadding().encodeToString(token); 
	}

}
