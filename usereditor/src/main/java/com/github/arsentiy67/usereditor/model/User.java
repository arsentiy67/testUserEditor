package com.github.arsentiy67.usereditor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "users", schema = "public")
public class User {

	private Integer userId;
	private String email;
	private String name;
	private String password;
	private String timezone;
	private Date createDate;
	private Date updateDate;
	
	private List<UserRole> userRole = new ArrayList<UserRole>();
	private List<UserAddress> userAddress = new ArrayList<UserAddress>();
	
	public User() {}
	
	@Id
	@SequenceGenerator(name="pk_users_id",sequenceName="users_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_users_id")
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "name", unique = true, nullable = false, length = 255)
	public String getName() {
		return this.name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	@Column(name = "password", nullable = false, length = 60)
	public String getPassword() {
		return this.password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<UserRole> getUserRole() {
		return this.userRole;
	}
 
	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<UserAddress> getUserAddress() {
		return userAddress;
	}
	
	public void setUserAddress(List<UserAddress> userAddress) {
		this.userAddress = userAddress;
	}
	
	@Column(name = "email", nullable = true, length = 255)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "timezone", nullable = true, length = 20)
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Column(name = "create_date", nullable = true)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_date", nullable = true)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
