package com.github.arsentiy67.usereditor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.arsentiy67.usereditor.dao.UserDAO;
import com.github.arsentiy67.usereditor.dto.UserDTO;
import com.github.arsentiy67.usereditor.model.User;

@Controller
public class UserListController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/userlist**", method = RequestMethod.GET)
	public ModelAndView userList() {
		ModelAndView mv = new ModelAndView("userlist");
		
		List<User> allUsers = userDAO.getAllUsers();
		
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		for (User user : allUsers) {
			UserDTO userDTO = new UserDTO(user);
			if (userDAO.hasRole(user.getUserId(), "ROLE_EDITOR")) {
				userDTO.setRoleStr("EDITOR");
 			} else {
 				userDTO.setRoleStr("USER");
 			}
			users.add(userDTO);
		}
		mv.addObject("allUsers", users);
		
		return mv;
	}
}
