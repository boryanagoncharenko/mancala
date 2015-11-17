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
//        String uniqueId = UUID.randomUUID().toString();
//        Game game = new Game(uniqueId);
//        this.dao.create(game);
//        return game;
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET, produces = "application/json")
    public Game retrieveGame(@PathVariable String gameId) {
        return this.gameManager.retrieveGame(gameId);
//        return this.dao.retrieve(gameId);
    }

    @RequestMapping(value = "/{gameId}/add/{userId}", method = RequestMethod.POST, produces = "application/json")
    // TODO: return "success" = true/false
    public Game addUserToGame(@PathVariable String gameId, @PathVariable String userId) {
        return this.gameManager.addUser(gameId, userId);
//        Game game = this.dao.retrieve(gameId);
//        if (game.getHost() != null && game.getGuest() != null) {
//            return game;
//        }
//        if (game.getHost() == null) {
//            game.setHost(userId);
//        }
//        else if (!game.getHost().equals(userId)) {
//            game.setGuest(userId);
//            // TODO: if player is already set, do not update it
//            game.setPlayerInTurn(userId);
//        }
//
//        this.dao.update(game);
//        return game;
    }

    @RequestMapping(value = "/{gameId}/move/{pit}", method = RequestMethod.POST, produces = "application/json")
    public Game makeMove(@PathVariable String gameId, @RequestParam("userId") String userId, @PathVariable int pit) {
        return this.gameManager.makeMove(gameId, userId, pit);
//        Game game = this.dao.retrieve(gameId);
//        GameRules rules = new GameRules();
//        if (rules.isMoveLegal(game, userId, pit)) {
//            Game updatedGame = rules.executeMove(userId, pit, game);
//            this.dao.update(updatedGame);
//        }
//
//        return game;
    }

}
