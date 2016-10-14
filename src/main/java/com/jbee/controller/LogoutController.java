package com.jbee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2016. 10. 14..
 */

@Controller
@RequestMapping("/user/logout")
public class LogoutController {
    @GetMapping("")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:../";
    }

}