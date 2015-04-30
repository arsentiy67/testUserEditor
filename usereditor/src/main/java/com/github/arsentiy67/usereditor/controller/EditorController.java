package com.github.arsentiy67.usereditor.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.arsentiy67.usereditor.dao.UserDAO;
import com.github.arsentiy67.usereditor.dto.UserDTO;
import com.github.arsentiy67.usereditor.model.User;
import com.github.arsentiy67.usereditor.model.UserRole;
import com.github.arsentiy67.usereditor.userdetails.UserDetailsExt;

@Controller
public class EditorController {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/edituser**", method = RequestMethod.GET)
	public ModelAndView editUser(@RequestParam("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		User user = userDAO.findByUserId(id);
		if (user != null) { //update user
			UserDetailsExt editorDetails = getUserDetails();
			boolean editor = isEditor();
			if (editor || editorDetails.getUserId() == id) {
				UserDTO userDTO = convertToEditorTimeZone(user, editorDetails.getUserTimeZone());
				mv.addObject("user", userDTO);
				mv.addObject("isEditor", editor);
				mv.setViewName("edituser");
			} else {
				mv.setViewName("403");
			}
		} //else - create new user
		return mv;
	}
	
	@RequestMapping(value = "/saveuser**", method = RequestMethod.POST)
	public ModelAndView saveUser(
			@RequestParam("user.userId") Integer userId,
			@RequestParam("user.email") String email,
			@RequestParam(value="user.name", required = false) String name,
			@RequestParam(value="user.password", required = false) String password,
			@RequestParam(value="user.roleStr", required = false) String roleStr
		) {
		
		ModelAndView mv = new ModelAndView();
		
		boolean editor = isEditor();
		
		User user = userDAO.findByUserId(userId);
		
		UserDetailsExt editorDetails = getUserDetails();
		
		if (user != null) { //update
			if (!editor && editorDetails.getUserId() != userId) {
				mv.setViewName("403");
				return mv;
			} else {
				user.setEmail(email);
				user.setName(name);
				if (password != null && !"".equals(password)) {
					user.setPassword(password);
				}
				user.setUserId(userId);
				user.setUpdateDate(new Date());
				updateRoles(user, roleStr);
				userDAO.createUpdateUser(user);
			}
		} else { //create new
			if (!editor) {
				mv.setViewName("403");
				return mv;
			} else {
				user = new User();
				user.setUserId(userId);
				user.setEmail(email);
				user.setName(name);
				user.setPassword(password);
				Date now = new Date();
				user.setCreateDate(now);
				user.setUpdateDate(now);
				userDAO.createUpdateUser(user);
			}
		}
		
		mv.setViewName("redirect:/userlist");
		return mv;
	}
	
	private void updateRoles(User user, String roleStr) {
		UserRole editor = new UserRole();
		editor.setRole("ROLE_EDITOR");
		editor.setUser(user);
		if ("USER".equals(roleStr)) {
			if (user.getUserRole().contains(editor)) {
				user.getUserRole().remove(editor);
			}
		} else if ("EDITOR".equals(roleStr)) {
			if (!user.getUserRole().contains(editor)) {
				user.getUserRole().add(editor);
			}
		}
	}
	
	private UserDetailsExt getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetailsExt) auth.getPrincipal();
	}
	
	private boolean isEditor() {
		for (GrantedAuthority auth : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if ("ROLE_EDITOR".equals(auth.getAuthority())) {
            	return true;
            }
        }
		return false;
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
