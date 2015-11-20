package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.Board;
import com.tinygames.mancala.domain.Game;
import com.tinygames.mancala.domain.Player;
import com.tinygames.mancala.domain.User;
import com.tinygames.mancala.repository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameManagerImpl implements GameManager {

    @Autowired
    private GameDao dao;

    @Autowired
    private UserManager userManager;

    public Game createGame() {
        String uniqueId = UUID.randomUUID().toString();
        Game gameEntity = new Game(uniqueId);
        this.dao.create(gameEntity);

        return gameEntity;
    }

    public Game retrieveGame(String gameId) {
        return this.dao.retrieve(gameId);
    }

    public Game addUser(String gameId, String userId) {
        Game gameEntity = this.dao.retrieve(gameId);
        User userEntity = this.userManager.retrieveUser(userId);
        if (gameEntity.tryAddUser(userEntity)) {
            this.dao.update(gameEntity);
        }
        return gameEntity;
    }

    public Game makeMove(String gameId, String userId, int pit) {
        Game game = this.dao.retrieve(gameId);
        User user = this.userManager.retrieveUser(userId);
        if (game.isMoveLegal(user, pit)) {
            game.makeMove(user, pit);
            this.dao.update(game);
        }

        return game;
    }
}
