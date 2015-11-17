package com.tinygames.mancala.controllers;

import com.tinygames.mancala.domain.User;
import com.tinygames.mancala.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public User createUser() {
        return this.userManager.createUser();
    }
}
