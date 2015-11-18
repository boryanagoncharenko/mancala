package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.GameEntity;
import com.tinygames.mancala.repository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameManagerImpl implements GameManager {

    private final int[] initialState = new int[] {4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};

    @Autowired
    private GameDao dao;

    public GameManagerImpl() {

    }

    public GameEntity createGame() {
        String uniqueId = UUID.randomUUID().toString();
        Game game = new Game(uniqueId);
        GameEntity gameEntity = game.toEntity();
        this.dao.create(gameEntity);

        return gameEntity;
    }

    public GameEntity retrieveGame(String gameId) {
        return this.dao.retrieve(gameId);
    }

    public GameEntity addUser(String gameId, String userId) {
        GameEntity gameEntity = this.dao.retrieve(gameId);
        Game game = new Game(gameEntity);
        User user = new User(userId);

        if (game.tryAddUser(user)) {
            game.setPlayerInTurn(game.getGuest());
            this.dao.update(game.toEntity());
        }

        return game.toEntity();
    }

    public GameEntity makeMove(String gameId, String userId, int pit) {
        GameEntity game = this.dao.retrieve(gameId);
        if (this.isMoveLegal(game, userId, pit)) {
            GameEntity updatedGame = this.executeMove(userId, pit, game);
            this.dao.update(updatedGame);
        }

        return game;
    }

    public boolean isMoveLegal(GameEntity game, String userId, int pit) {
        return game.getPlayerInTurn() != null && game.getPlayerInTurn().equals(userId) &&
                game.getState()[pit] != 0 &&
                this.isUserAllowedToMovePit(game, userId, pit);
    }

    private boolean isUserAllowedToMovePit(GameEntity game, String userId, int pit) {
        if (game.getHost().equals(userId)) {
            return pit >=0 && pit <= 5;
        }
        return pit >= 7 && pit <= 12;
    }

    public GameEntity executeMove(String user, int pit, GameEntity game) {
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
        int offset = 0;
        for (int i = 1; i <= numberOfStones; i++) {
            int index = (pit + i + offset) % 14;
            if (index == oppKalah) {
                index = (index + 1) % 14;
                offset++;
            }
            board[index]++;
        }
        // index of the last pit where a stone was dropped
        return (pit + numberOfStones + offset) % 14;
    }

    private int getOwnKalahIndex(String user, GameEntity game) {
        if (game.getHost().equals(user)) {
            return 6;
        }
        return 13;
    }

    private String getOpponentId(String user, GameEntity game) {
        String host = game.getHost();
        if (host.equals(user)) {
            return game.getGuest();
        }
        return host;
    }

    private boolean tryAddHost(GameEntity game, User player) {
        if (game.getHost() == null) {
            game.setHost(player.getId());
            return true;
        }
        return false;
    }

    private boolean tryAddGuest(GameEntity game, User player) {
        if (!game.getHost().equals(player.getId()) && game.getGuest() == null) {
            game.setGuest(player.getId());
            return true;
        }
        return false;
    }

    private void trySetPlayerInTurn(GameEntity game) {
        if (game.getHost() != null && game.getGuest() != null && game.getPlayerInTurn() == null) {
            game.setPlayerInTurn(game.getGuest());
        }
    }
}
