package com.jbee.controller;

import com.jbee.domain.User;
import com.jbee.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jbee on 2016. 10. 28..
 */

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User show(@PathVariable Long id) {
        return userRepository.findOne(id);
    }
}