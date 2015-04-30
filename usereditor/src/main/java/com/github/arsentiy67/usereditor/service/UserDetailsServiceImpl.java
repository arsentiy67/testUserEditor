package com.github.arsentiy67.usereditor.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.arsentiy67.usereditor.dao.UserDAO;
import com.github.arsentiy67.usereditor.model.User;
import com.github.arsentiy67.usereditor.model.UserRole;
import com.github.arsentiy67.usereditor.userdetails.UserDetailsExt;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;
	
	public UserDetails loadUserByUsername(final String email)
			throws UsernameNotFoundException {
		User user = userDAO.findByUserEmail(email);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
		return buildUserForAuthentication(user, authorities);
	}

	private UserDetails buildUserForAuthentication(User user,
			List<GrantedAuthority> authorities) {
		return new UserDetailsExt(
			user.getName(), 
			user.getPassword(),	
			true, true, true, true, 
			authorities,
			user.getTimezone(),
			user.getUserId());
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		 
		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
 
		return new ArrayList<GrantedAuthority>(setAuths);
	}

}
