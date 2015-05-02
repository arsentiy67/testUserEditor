package com.github.arsentiy67.usereditor.dto;

import com.github.arsentiy67.usereditor.model.User;

public class UserDTO extends User {
	
	public UserDTO() {}
	
	public UserDTO(User user) {
		setUserId(user.getUserId());
		setEmail(user.getEmail());
		setName(user.getName());
		setTimezone(user.getTimezone());
		setCreateDate(user.getCreateDate());
		setUpdateDate(user.getUpdateDate());
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
