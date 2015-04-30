package com.github.arsentiy67.usereditor.dao;

import com.github.arsentiy67.usereditor.model.User;

public interface UserDAO {

	User findByUserName(String username);
}
