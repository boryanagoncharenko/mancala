package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.Game;
import com.tinygames.mancala.repository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameManagerImpl implements GameManager {

    @Autowired
    private GameDao dao;

    public GameManagerImpl() {

    }

    public Game createGame() {
        String uniqueId = UUID.randomUUID().toString();
        Game game = new Game(uniqueId);
        this.dao.create(game);
        return game;
    }

    public Game retrieveGame(String gameId) {
        return this.dao.retrieve(gameId);
    }

    public Game addUser(String gameId, String userId) {
        Game game = this.dao.retrieve(gameId);
        if (game.getHost() != null && game.getGuest() != null) {
            return game;
        }
        if (game.getHost() == null) {
            game.setHost(userId);
        }
        else if (!game.getHost().equals(userId)) {
            game.setGuest(userId);
            // TODO: if player is already set, do not update it
            game.setPlayerInTurn(userId);
        }

        this.dao.update(game);
        return game;
    }

    public Game makeMove(String gameId, String userId, int pit) {
        Game game = this.dao.retrieve(gameId);
        GameManagerImpl rules = new GameManagerImpl();
        if (rules.isMoveLegal(game, userId, pit)) {
            Game updatedGame = rules.executeMove(userId, pit, game);
            this.dao.update(updatedGame);
        }

        return game;
    }

    public boolean isMoveLegal(Game game, String userId, int pit) {
        return game.getPlayerInTurn() != null && game.getPlayerInTurn().equals(userId) &&
                game.getState()[pit] != 0 &&
                this.isUserAllowedToMovePit(game, userId, pit);
    }

    private boolean isUserAllowedToMovePit(Game game, String userId, int pit) {
        if (game.getHost().equals(userId)) {
            return pit >=0 && pit <= 5;
        }
        return pit >= 7 && pit <= 12;
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
        game.setPlayerInTurn(this.getOpponentId(user, game));

        return game;
    }

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

    private int getOwnKalahIndex(String user, Game game) {
        if (game.getHost().equals(user)) {
            return 6;
        }
        return 13;
    }

    private String getOpponentId(String user, Game game) {
        String host = game.getHost();
        if (host.equals(user)) {
            return game.getGuest();
        }
        return host;
    }
}
