package com.jbee.controller;

import com.jbee.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jbee.domain.User;
import com.jbee.domain.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //추가되는 데이터를 데이터베이스에 저장하는 코드
    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
//		users.add(user);
        System.out.println(user);
        return "redirect:/users";
    }

    //사용자 목록을 나타내는 페이지
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
//		model.addAttribute("users", users);
        return "/user/list";
    }

    //회원 정보를 수정하는 페이지
    @GetMapping("{id}/update")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)){
            return "redirect:/user/login";
        }
        if (!id.equals(HttpSessionUtils.getUserFromSession(session).getId())){
            throw new IllegalStateException("error access");
        }
        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    //수정된 정보를 다시 저장하는 코드
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updatedUser) {
        User user = userRepository.findOne(id);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}