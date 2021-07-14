package com.udacity.jwdnd.course1.cloudstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger = LoggerFactory.getLogger(CredentialService.class);
	
	@Autowired
	private CredentialMapper credentialmapper;

	@Autowired
	private EncryptionService encryptionService;

	public CredentialService(CredentialMapper credentialmapper, UserMapper userMapper,
			EncryptionService encryptionService) {
		this.credentialmapper = credentialmapper;
		this.encryptionService = encryptionService;
	}

	private String decryptPassword(String encodedPassword, String key) {
		return encryptionService.decryptValue(encodedPassword, key);
	}

	public List<Credential> getCredentialsForUser(int userid) {
		List<Credential> credentials = credentialmapper.findByUserId(userid);
		for (Credential credential : credentials) {
			credential.setPassword(decryptPassword(credential.getPassword(), credential.getKey()));
		}
		return credentials;
	}

	public List<Credential> getCredentialsForUser(String username) {
		logger.info("username: {}", username);
		List<Credential> credentials = credentialmapper.findByUserName(username);
		logger.info("credentials: {}", credentials);
		for (Credential credential : credentials) {
			credential.setPassword(decryptPassword(credential.getPassword(), credential.getKey()));
		}
		return credentials;
	}

	public Credential findById(int id) {
		Credential credential = credentialmapper.findByCredentialsId(id);
		credential.setPassword(decryptPassword(credential.getPassword(), credential.getKey()));
		return credential;
	}

	public Integer addCredential(Credential credential, int userid) {
		String key = generateRandomSalt(16);
		String encryptedPassword = encryptPassword(credential.getPassword(), key);
		credential.setPassword(encryptedPassword);
		credential.setKey(key);
		credential.setUserid(userid);

		return credentialmapper.insertCredential(credential);
	}

	public Integer addCredential(Credential credential) {
		String key = generateRandomSalt(16);
		String encryptedPassword = encryptPassword(credential.getPassword(), key);
		credential.setPassword(encryptedPassword);
		credential.setKey(key);
		return credentialmapper.insertCredential(credential);
	}

	public Integer updatecredential(Credential credential, int userid) {
		String key = generateRandomSalt(16);
		String encryptedPassword = encryptPassword(credential.getPassword(), key);
		credential.setPassword(encryptedPassword);
		credential.setKey(key);
		credential.setUserid(userid);

		return credentialmapper.updateCredential(credential);
	}

	public Integer updatecredential(Credential credential) {
		String key = generateRandomSalt(16);
		String encryptedPassword = encryptPassword(credential.getPassword(), key);
		credential.setPassword(encryptedPassword);
		credential.setKey(key);

		return credentialmapper.updateCredential(credential);
	}

	public Integer deleteCredential(int credentialId) {
		return credentialmapper.deleteByCredentialId(credentialId);
	}

	public boolean credentialExists(int credentialid) {
		return credentialmapper.findByCredentialsId(credentialid) != null;
	}

	private String encryptPassword(String plainTextPassword, String key) {
		//String key = generateRandomBase64Token(16);
		return encryptionService.encryptValue(plainTextPassword, key);
	}

	private String generateRandomSalt(int byteLength) { // stackoverflow
		SecureRandom random = new SecureRandom();
        byte[] salt = new byte[byteLength];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
	}
	
	private String generateRandomBase64Token(int byteLength) { // stackoverflow
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[byteLength];
		secureRandom.nextBytes(token);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
	}

}
