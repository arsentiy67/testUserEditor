package com.github.arsentiy67.usereditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EditorController {

	@RequestMapping(value = "/edituser**", method = RequestMethod.GET)
	public ModelAndView editUser(
			@RequestParam(value = "name", required = false) String name) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", name);
		mv.setViewName("edituser");
		return mv;
	}
}
