package com.github.arsentiy67.usereditor.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_roles", schema = "public", 
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "user_id" }))
public class UserRole {

	private Integer userRoleId;
	private User user;
	private String role;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", 
		unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.userRoleId;
	}
 
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}
 
	public void setUser(User user) {
		this.user = user;
	}
 
	@Column(name = "role", nullable = false, length = 40)
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
}
