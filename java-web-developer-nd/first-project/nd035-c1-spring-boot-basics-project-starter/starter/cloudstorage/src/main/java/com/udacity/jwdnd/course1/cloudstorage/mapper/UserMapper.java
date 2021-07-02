package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
@Repository
public interface UserMapper {
	@Select("SELECT * FROM USERS")
	List<User> findAllUsers();

	@Select("SELECT * FROM USERS WHERE userid = #{userid}")
	User findByUserId(int userid);

	@Select("SELECT * FROM USERS WHERE username = #{username}")
	User findByUserName(String username);
	
	@Insert("INSERT INTO USERS (username, password, salt, firstname, lastname) VALUES(#{username}, #{password}, #{salt}, #{firstname}, #{lastname})")
	public int insertUsers(User user);
	
	@Delete("DELETE FROM USERS WHERE username = #{username}")
	public int deleteByUserName(String username);
	
	@Update("UPDATE USERS SET username = #{username}, password = #{password}, salt = #{salt}, firstname = #{firstname}, lastname = #{lastname} WHERE userid = #{userid}")
	public int updateUser(User user);
}
