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

@Entity
@Table(name = "user_address", schema = "public")
public class UserAddress {

	private Integer userAddressId;
	private User user;
	private String country;
	private String city;
	
	public UserAddress() {}
	
	@Id
	@SequenceGenerator(name="pk_user_address_id",sequenceName="user_address_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_user_address_id")
	@Column(name = "user_address_id", unique = true, nullable = false)
	public Integer getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(Integer userAddressId) {
		this.userAddressId = userAddressId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "country", nullable = false, length = 255)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Column(name = "city", nullable = false, length = 255)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
