package com.jbee.controller;

import com.jbee.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/")
	public String mainpage(Model model){
		model.addAttribute("questions", questionRepository.findAll());
		return "/index";
	}
}
