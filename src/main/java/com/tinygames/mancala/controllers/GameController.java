package com.tinygames.mancala.controllers;

import com.tinygames.mancala.models.Game;
import com.tinygames.mancala.models.dao.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameDao dao;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String create() {
        String uniqueID = UUID.randomUUID().toString();
        Game game = new Game(uniqueID);
        this.dao.create(game);
        return "{\"gameID\":\"" + uniqueID + "\"}";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void assignUser(@PathVariable String userId) {
        // check if the user can be assigned to the game
        // return if it is successful
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
//    public Game get(@PathVariable String id) {
//        return this.dao.retrieve(id);
//    }
//
//    @RequestMapping(value = "/{id}/add/{user}", method = RequestMethod.POST)
//    public void addPlayer(@PathVariable String id, @PathVariable String user) {
//
//    }
//
    @RequestMapping(value = "/{id}/move", method = RequestMethod.POST)
    public void move(@PathVariable String id, @RequestParam("user") String user, @RequestParam("pit") int hole) {

    }

}
