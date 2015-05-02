package com.github.arsentiy67.usereditor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.arsentiy67.usereditor.model.User;
import com.github.arsentiy67.usereditor.model.UserRole;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public User findByUserEmail(String email) {
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession().createQuery("from User where email=:email")
			.setString("email", email).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasRole(Integer userId, String role) {
		List<UserRole> roles = sessionFactory.getCurrentSession().createQuery("from UserRole where user_id = :userId and role = :role")
			.setInteger("userId", userId).setString("role", role).list();
		return roles.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public User findByUserId(Integer userId) {
		if (userId == null) {
			return null;
		}
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession().createQuery("from User where userId=:userId")
			.setInteger("userId", userId).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	public void createUpdateUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}
}
