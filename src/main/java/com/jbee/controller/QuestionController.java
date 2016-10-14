package com.jbee.controller;

import com.jbee.domain.Question;
import com.jbee.domain.QuestionRepository;
import com.jbee.domain.User;
import com.jbee.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2016. 10. 14..
 */
@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    @GetMapping("/form")
    public String questionForm(HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session){
        if(!HttpSessionUtils.isLoginUser(session)){
            return "user/login";
        }
        User sessionedUsr = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionedUsr.getUserId(), title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }
}
