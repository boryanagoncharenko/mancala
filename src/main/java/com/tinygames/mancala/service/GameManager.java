package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.GameEntity;

public interface GameManager {
    GameEntity createGame();
    GameEntity retrieveGame(String gameId);
    GameEntity addUser(String gameId, String userId);
    GameEntity makeMove(String gameId, String userId, int pit);
    boolean isMoveLegal(GameEntity game, String userId, int pit);
    GameEntity executeMove(String user, int pit, GameEntity game);
}
