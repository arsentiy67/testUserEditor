package com.github.arsentiy67.usereditor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.arsentiy67.usereditor.model.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession().createQuery("from User where username=:username")
			.setString("username", username).list();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

}
