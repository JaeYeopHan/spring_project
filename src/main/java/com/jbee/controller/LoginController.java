package com.jbee.controller;

import com.jbee.domain.User;
import com.jbee.domain.UserRepository;
import com.jbee.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/login")
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("")
	public String login(){
		return "/user/login";
	}

	@PostMapping("")
	public String loginInfo(String userId, String password, HttpSession session){
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			return "redirect:/user/login";
		}
		if(HttpSessionUtils.isLoginUser(session)){
			return "redirect:/user/login";
		}

		if(!user.matchPassword(password)){
			return "redirect:/user/login";
		}

		session.setAttribute("sessionedUser", user);
		return "redirect:../";
	}
}
