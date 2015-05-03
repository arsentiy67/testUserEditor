package com.github.arsentiy67.usereditor.dto;

import com.github.arsentiy67.usereditor.model.User;

public class UserDTO extends User {
	
	public static User userFromDTO(UserDTO userDTO) {
		User user = new User();
		user.setUserId(userDTO.getUserId());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		user.setTimezone(userDTO.getTimezone());
		user.setCreateDate(userDTO.getCreateDate());
		user.setUpdateDate(userDTO.getUpdateDate());
		user.setUserRole(userDTO.getUserRole());
		user.setUserAddress(userDTO.getUserAddress());
		return user;
	}
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		setUserId(user.getUserId());
		setEmail(user.getEmail());
		setName(user.getName());
		setTimezone(user.getTimezone());
		setCreateDate(user.getCreateDate());
		setUpdateDate(user.getUpdateDate());
		setUserRole(user.getUserRole());
		setUserAddress(user.getUserAddress());
	}

	private String createDateStr;
	private String updateDateStr;
	private String roleStr;
	
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getUpdateDateStr() {
		return updateDateStr;
	}
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}
	public String getRoleStr() {
		return roleStr;
	}
	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}
}
