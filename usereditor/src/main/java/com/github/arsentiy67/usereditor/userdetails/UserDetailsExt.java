package com.github.arsentiy67.usereditor.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsExt extends User {

	private static final long serialVersionUID = -8315535273249007840L;

	private Integer userId;
	private String userTimeZone;
	
	public UserDetailsExt(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	public UserDetailsExt(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String userTimeZone, Integer userId) {
		this(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		setUserTimeZone(userTimeZone);
		setUserId(userId);
	}

	public String getUserTimeZone() {
		return userTimeZone;
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
