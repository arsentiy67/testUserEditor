package com.github.arsentiy67.usereditor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
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
	@SequenceGenerator(name="pk_user_roles_id",sequenceName="user_roles_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_user_roles_id")
	@Column(name = "user_role_id", unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.userRoleId;
	}
 
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
 
	@ManyToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
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
	
	@Override
	public boolean equals(Object obj) {
		UserRole that = (UserRole) obj;
		return user.getUserId() == that.user.getUserId() && role.equals(that.role);
	}
	
	@Override
	public int hashCode() {
		if (user == null || user.getUserId() == null || role == null) {
			return super.hashCode();
		}
		return user.getUserId().hashCode() ^ role.hashCode();
	}
}
