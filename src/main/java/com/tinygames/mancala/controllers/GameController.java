package com.tinygames.mancala.controllers;

import com.tinygames.mancala.service.GameManager;
import com.tinygames.mancala.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameManager gameManager;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Game createGame() {
        return this.gameManager.createGame();
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET, produces = "application/json")
    public Game retrieveGame(@PathVariable String gameId) {
        return this.gameManager.retrieveGame(gameId);
    }

    @RequestMapping(value = "/{gameId}/add/{userId}", method = RequestMethod.POST, produces = "application/json")
    // TODO: return "success" = true/false
    public Game addUserToGame(@PathVariable String gameId, @PathVariable String userId) {
        return this.gameManager.addUser(gameId, userId);
    }

    @RequestMapping(value = "/{gameId}/move/{pit}", method = RequestMethod.POST, produces = "application/json")
    public Game makeMove(@PathVariable String gameId, @RequestParam("userId") String userId, @PathVariable int pit) {
        return this.gameManager.makeMove(gameId, userId, pit);
    }
}
