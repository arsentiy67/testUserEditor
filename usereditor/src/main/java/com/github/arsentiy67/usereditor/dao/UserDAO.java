package com.github.arsentiy67.usereditor.dao;

import java.util.List;

import com.github.arsentiy67.usereditor.model.User;

public interface UserDAO {

	User findByUserEmail(String email);
	
	User findByUserId(Integer userId);
	
	List<User> getAllUsers();
	
	boolean hasRole(Integer userId, String role);
	
	void createUpdateUser(User user);
}
