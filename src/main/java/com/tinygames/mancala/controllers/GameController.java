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
    public Game createGame() {
        String uniqueID = UUID.randomUUID().toString();
        Game game = new Game(uniqueID);
        this.dao.create(game);
        return game;
    }

    @RequestMapping(value = "/{gameID}", method = RequestMethod.GET, produces = "application/json")
    public Game retrieveGame(@PathVariable String gameID) {
        return this.dao.retrieve(gameID);
    }

    @RequestMapping(value = "/{gameID}/add/{userID}", method = RequestMethod.POST, produces = "application/json")
    // TODO: return "success" = true/false
    public Game addUserToGame(@PathVariable String gameID, @PathVariable String userID) {
        Game game = this.dao.retrieve(gameID);
        if (game.getHost() != null && game.getGuest() != null) {
            return game;
        }
        if (game.getHost() == null) {
            game.setHost(userID);
        }
        else if (!game.getHost().equals(userID)) {
            game.setGuest(userID);
            // TODO: if player is already set, do not update it
            game.setPlayerInTurn(userID);
        }

        this.dao.update(game);
        return game;
    }

    @RequestMapping(value = "/{gameID}/move/{pit}", method = RequestMethod.POST, produces = "application/json")
    public Game makeMove(@PathVariable String gameID, @RequestParam("userID") String userID, @PathVariable int pit) {
        Game game = this.dao.retrieve(gameID);
        GameRules rules = new GameRules(game);
        if (rules.isMoveLegal(userID, pit)) {
            Game updatedGame = rules.executeMove(userID, pit, game);
            this.dao.update(updatedGame);
        }

        return game;
    }

}
