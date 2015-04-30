package com.github.arsentiy67.usereditor.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.arsentiy67.usereditor.dao.UserDAO;
import com.github.arsentiy67.usereditor.dto.UserDTO;
import com.github.arsentiy67.usereditor.model.User;
import com.github.arsentiy67.usereditor.userdetails.UserDetailsExt;

@Controller
public class EditorController {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/edituser**", method = RequestMethod.GET)
	public ModelAndView editUser(
			@RequestParam(value = "id", required = true) Integer id) {
		ModelAndView mv = new ModelAndView("edituser");
		User user = userDAO.findByUserId(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsExt editorDetails = (UserDetailsExt) auth.getPrincipal();
		UserDTO userDTO = convertToEditorTimeZone(user, editorDetails.getUserTimeZone());
		mv.addObject("user", userDTO);
		return mv;
	}
	
	public UserDTO convertToEditorTimeZone(User user, String editorTZ) {
		UserDTO userDTO = new UserDTO(user);
		if (userDAO.hasRole(user.getUserId(), "ROLE_EDITOR")) {
			userDTO.setRoleStr("EDITOR");
		} else {
			userDTO.setRoleStr("USER");
		}
		
		DateFormat formatter= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss Z");
		formatter.setTimeZone(TimeZone.getTimeZone(editorTZ));
		userDTO.setCreateDateStr(formatter.format(user.getCreateDate()));
		userDTO.setUpdateDateStr(formatter.format(user.getUpdateDate()));
		
		return userDTO;
	}
}
