package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
@Repository
public interface CredentialMapper {
	@Select("SELECT * FROM CREDENTIALS")
	List<Credential> findAllCredentials();

	@Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
	Credential findByCredentialsId(int credentialid);

	@Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}") //login user id
	List<Credential> findByUserId(int userid);
	
	@Select("SELECT * FROM CREDENTIALS WHERE username = #{usernameInCredential}") //user name for the credential
	Credential findByUserNameInCredential(String usernameInCredential); // returns just one credential
	
	@Select("SELECT * FROM CREDENTIALS WHERE key = #{key}")
	Credential findByCredentialKey(String key);
	
	@Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
	public int insertCredential(Credential credential);
	
	@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
	public int deleteByCredentialId(int credentialId);
	
	@Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userid} WHERE credentialid = #{credentialid}")
	public int updateCredential(Credential credential);
}
