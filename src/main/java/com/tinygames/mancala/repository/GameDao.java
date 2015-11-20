package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.Game;

public interface GameDao {
    void create(Game game);
    Game retrieve(String id);
    void update(Game game);
}
