package com.github.arsentiy67.usereditor.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.arsentiy67.usereditor.dao.UserDAO;
import com.github.arsentiy67.usereditor.dto.UserDTO;
import com.github.arsentiy67.usereditor.model.User;
import com.github.arsentiy67.usereditor.model.UserAddress;
import com.github.arsentiy67.usereditor.model.UserRole;
import com.github.arsentiy67.usereditor.userdetails.UserDetailsExt;

@Controller
public class EditorController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/edituser**", method = RequestMethod.GET)
	public ModelAndView editUser(@RequestParam(value = "id", required = false) Integer id) {
		ModelAndView mv = new ModelAndView();
		User user = userDAO.findByUserId(id);
		UserDetailsExt editorDetails = getUserDetails();
		boolean editor = isEditor();
		if (user != null) { //update user
			if (editor || editorDetails.getUserId() == id) {
				UserDTO userDTO = convertToEditorTimeZone(user, editorDetails.getUserTimeZone());
				mv.addObject("user", userDTO);
				mv.setViewName("edituser");
			} else {
				mv.setViewName("403");
			}
		} else { //create new user
			UserDTO newUser = new UserDTO();
			newUser.setRoleStr("USER");
			mv.addObject("user", newUser);
			mv.setViewName("edituser");
		}
		mv.addObject("isEditor", editor);
		return mv;
	}
	
	@RequestMapping(value = "/saveuser**", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("user") UserDTO userFromRequest) {
		
		Integer userId = userFromRequest.getUserId();
		ModelAndView mv = new ModelAndView();
		
		boolean editor = isEditor();
		
		User userFromDb = userDAO.findByUserId(userId);
		
		UserDetailsExt editorDetails = getUserDetails();
		if (userFromDb != null) { //update
			if (!editor && editorDetails.getUserId() != userId) {
				mv.setViewName("403");
				return mv;
			} else {
				userFromDb.setEmail(userFromRequest.getEmail());
				userFromDb.setName(userFromRequest.getName());
				userFromDb.setTimezone(userFromRequest.getTimezone());
				String password = userFromRequest.getPassword();
				if (password != null && !"".equals(password)) {
					userFromDb.setPassword(passwordEncoder.encode(password));
				}
				userFromDb.setUserId(userId);
				userFromDb.setUpdateDate(new Date());
				
				for (int i = userFromDb.getUserAddress().size() - 1; i >= 0; i--) {
					UserAddress address = userFromDb.getUserAddress().get(i);
					userFromDb.getUserAddress().remove(address);
				}
				for (UserAddress address : userFromRequest.getUserAddress()) {
					address.setUser(userFromDb);
					userFromDb.getUserAddress().add(address);
				}
				updateRoles(userFromDb, userFromRequest.getRoleStr());
				userDAO.createUpdateUser(userFromDb);
			}
		} else { //create new
			if (!editor) {
				mv.setViewName("403");
				return mv;
			} else {
				User newUser = UserDTO.userFromDTO(userFromRequest);
				newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
				Date now = new Date();
				newUser.setCreateDate(now);
				newUser.setUpdateDate(now);
				UserRole roleUser = new UserRole();
				roleUser.setUser(newUser);
				roleUser.setRole("ROLE_USER");
				for (UserAddress address : newUser.getUserAddress()) {
					address.setUser(newUser);
				}
				updateRoles(newUser, userFromRequest.getRoleStr());
				userDAO.createUpdateUser(newUser);
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
