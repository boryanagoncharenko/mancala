package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.GameEntity;
import com.tinygames.mancala.repository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameManagerImpl implements GameManager {

    private final int[] initialState = new int[] {4, 4, 4, 4, 4, 4, 0, 0,0,0,0,0, 1, 0};

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

        String next = this.getOpponentId(user, game);
        if (lastIndex == kalahIndex) {
            next = user;
        }
        else if (this.isStoneCapturing(lastIndex, kalahIndex, board)) {
            this.captureStones(lastIndex, kalahIndex, board);
        }

        if (this.isGameOver(board)) {
            String winner = user;
            if (board[kalahIndex] < board[opponentKalahIndex]) {
                winner = this.getOpponentId(user, game);
            }
            game.setWinner(winner);
        }
        else {
            game.setPlayerInTurn(next);
        }

        return game;
    }

    private boolean isStoneCapturing(int lastIndex, int kalahIndex, int[] board) {
        return lastIndex >= kalahIndex - 6 && // The stone lands on the player's pits
                board[lastIndex] == 1 && // The pit was empty
                board[12 - lastIndex] > 0; // The opposite pit is not empty
    }

    private void captureStones(int lastIndex, int kalahIndex, int[] board) {
        board[kalahIndex] += board[lastIndex] + board[12 - lastIndex];
        board[lastIndex] = 0;
        board[12 - lastIndex] = 0;
    }

    private void setWinner() {

    }

    private boolean isGameOver(int[] board) {
        if (this.isSideEmpty(board, 0)) {
            this.emptySide(board, 7);
            return true;
        }
        if (this.isSideEmpty(board, 7)) {
            this.emptySide(board, 0);
            return true;
        }
        return false;
    }

    private void emptySide(int[] board, int offset) {
        for (int i = 0; i < 6; i++) {
            board[offset + 6] += board[i+offset];
            board[i+offset] = 0;
        }
    }

    private boolean isSideEmpty(int[] board, int offset) {
        for (int i = offset; i < 6 + offset; i++) {
            if (board[i] > 0) {
                return false;
            }
        }
        return true;
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
