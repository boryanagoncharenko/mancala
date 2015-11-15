package com.tinygames.mancala.controllers;

import com.tinygames.mancala.models.User;
import com.tinygames.mancala.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao dao;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public User createUser() {
        String uniqueID = UUID.randomUUID().toString();
        User user = new User(uniqueID);
        this.dao.create(user);
        return user;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/json")
    public String retrieveUser() {
        String uniqueID = UUID.randomUUID().toString();
        User user = new User(uniqueID);
//        Game game = new Game(uniqueID, user);
//        this.dao.create(game);
        return "{\"userID\":\"" + uniqueID + "\"}";
    }
}
