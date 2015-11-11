package com.tinygames.mancala.controllers;

import com.tinygames.mancala.models.Game;
import com.tinygames.mancala.models.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String createUser() {
        String uniqueID = UUID.randomUUID().toString();
        User user = new User(uniqueID);
//        Game game = new Game(uniqueID, user);
//        this.dao.create(game);
        return "{\"userID\":\"" + uniqueID + "\"}";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/json")
    public String checkIfUserExists() {
        String uniqueID = UUID.randomUUID().toString();
        User user = new User(uniqueID);
//        Game game = new Game(uniqueID, user);
//        this.dao.create(game);
        return "{\"userID\":\"" + uniqueID + "\"}";
    }
}
