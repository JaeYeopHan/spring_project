package com.jbee.controller;

import com.jbee.domain.*;
import com.jbee.utils.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2016. 10. 21..
 */

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);
        Answer answer = new Answer(sessionedUser, contents, question);
        question.addAnswer();
        return answerRepository.save(answer);
        //save 메소드는 저장한 인자를 그대로 return 하고 있다. 그렇기 때문에 return 값으로 설정해줘도 무방하다.
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인 해야 합니다");
        }

        Answer answer = answerRepository.findOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if(!answer.isSameWriter(loginUser)) {
            return Result.fail("자신의 글만 삭제할 수 있습니다!");
        }

        answerRepository.delete(id);

        Question question = questionRepository.findOne(questionId);
        question.deleteAnswer();
        questionRepository.save(question);
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인 해야 합니다");
        }

        Answer answer = answerRepository.findOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if(!answer.isSameWriter(loginUser)) {
            return Result.fail("자신의 글만 수정할 수 있습니다!");
        }

        answerRepository.save(answer);
        return Result.ok();
    }
}
