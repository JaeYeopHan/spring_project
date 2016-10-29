package com.jbee.controller;

import com.jbee.domain.Question;
import com.jbee.domain.QuestionRepository;
import com.jbee.utils.Result;
import com.jbee.domain.User;
import com.jbee.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String questionForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "user/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionedUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findOne(id);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getMessage());
            return "user/login";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        Question question = questionRepository.findOne(id);
        Result result = valid(session, question);
        if(!result.isValid()){
            model.addAttribute("errorMessage", result.getMessage());
            return "user/login";
        }
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

//    @PutMapping("/{id}")
//    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
//        try {
//            Question question = questionRepository.findOne(id);
//            hasPermission(session, question);
//            question.update(title, contents);
//            questionRepository.save(question);
//            return String.format("redirect:/questions/%d", id);
//        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "user/login";
//        }
//    }

//    @PutMapping("/{id}")
//    public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
//        if (!HttpSessionUtils.isLoginUser(session)) {
//            return "users/login";
//        }
//
//        Question question = questionRepository.findOne(id);
//        question.update(title, contents);
//        questionRepository.save(question);
//
//        return String.format("redirect:/questions/%d", id);
//    }

    @DeleteMapping("/{id}")
    public String questionDelete(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findOne(id);
            hasPermission(session, question);
            questionRepository.delete(id);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }

//    @DeleteMapping("/{id}")
//    public String questionDelete(@PathVariable Long id, HttpSession session) {
//        if (!HttpSessionUtils.isLoginUser(session)) {
//            return "user/login";
//        }
//
//        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
//        Question question = questionRepository.findOne(id);
//        if (!question.isSameWriter(sessionedUser)) {
//            return "user/login";
//        }
//        questionRepository.delete(id);
//        return "redirect:/";
//    }
//

    private void hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(sessionedUser)) {
            throw new IllegalStateException("자신의 글만 수정, 삭제가 가능합니다.");
        }
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(sessionedUser)) {
            return Result.fail("자신의 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

}
