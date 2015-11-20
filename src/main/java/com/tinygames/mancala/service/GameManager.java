package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.Game;

public interface GameManager {
    Game createGame();
    Game retrieveGame(String gameId);
    Game addUser(String gameId, String userId);
    Game makeMove(String gameId, String userId, int pit);
}
