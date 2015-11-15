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
    public String createGame() {
        String uniqueID = UUID.randomUUID().toString();
        Game game = new Game(uniqueID);
        this.dao.create(game);
        return Helpers.buildJson("gameID", uniqueID);
    }

    @RequestMapping(value = "/{gameID}", method = RequestMethod.GET)
    public Game retrieveGame(@PathVariable String gameID) {
        return this.dao.retrieve(gameID);
    }

    @RequestMapping(value = "/{gameID}/add/{userID}", method = RequestMethod.POST, produces = "application/json")
    public Game addUserToGame(@PathVariable String gameID, @PathVariable String userID) {
        Game game = this.dao.retrieve(gameID);
        if (game.getHost() == null) {
            game.setHost(userID);
        }
        else if (game.getGuest() == null && !game.getHost().equals(userID)) {
            game.setGuest(userID);
            game.setPlayerInTurn(userID);
        }
        this.dao.update(game);

        return game;
    }

    @RequestMapping(value = "/{gameID}/move", method = RequestMethod.POST)
    public Game makeMove(@PathVariable String gameID, @RequestParam("userID") String userID, @RequestParam("pit") int pit) {
        Game game = this.dao.retrieve(gameID);
        if (game.getPlayerInTurn().equals(userID) && this.isUserAllowedToPlayPit(game, userID, pit)) {
            Game updatedGame = this.executeMove(userID, pit, game);
            this.dao.update(updatedGame);
        }

        return game;
    }

    private boolean isUserAllowedToPlayPit(Game game, String userID, int pit) {
        if (game.getHost().equals(userID)) {
            return pit >=0 && pit <= 5;
        }

        return pit >= 7 && pit <= 12;
    }

    // TODO: handle this in the js file
//    public int getPitNumber(int pit, String user, Game game) {
//        if (game.getGuest().equals(user)) {
//            return pit + 7;
//        }
//        return pit;
//    }

    public int distributeStones(int[] board, int pit, int oppKalah) {
        int numberOfStones = board[pit];
        board[pit] = 0;
        int offset = 1;
        for (int i = 0; i < numberOfStones; i++) {
            int index = (pit + i + offset) % 14;
            if (index == oppKalah) {
                index++;
                offset++;
            }
            board[index]++;
        }
        // index of the last pit where a stone was dropped
        return (pit + numberOfStones + offset - 1) % 14;
    }

    public Game executeMove(String user, int pit, Game game) {
        int[] board = game.getState();
        int kalahIndex = this.getOwnKalahIndex(user, game);
        int opponentKalahIndex = (kalahIndex + 7) % 14;
        int lastIndex = this.distributeStones(board, pit, opponentKalahIndex);
        game.setState(board);

        if (lastIndex == kalahIndex) {
            game.setPlayerInTurn(user);
            return game;
        }

        if (board[lastIndex] == 1 && board[12 - lastIndex] > 0) {
            board[kalahIndex] += board[lastIndex] + board[12 - lastIndex];
            board[lastIndex] = 0;
            board[12 - lastIndex] = 0;
        }
        game.setPlayerInTurn(this.getOpponentID(user, game));

        return game;
    }

    private int getOwnKalahIndex(String user, Game game) {
        if (game.getHost().equals(user)) {
            return 6;
        }
        return 13;
    }

    private String getOpponentID(String user, Game game) {
        String host = game.getHost();
        if (host.equals(user)) {
            return game.getGuest();
        }
        return host;
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

}
