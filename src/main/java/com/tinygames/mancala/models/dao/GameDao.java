package com.tinygames.mancala.models.dao;

import com.tinygames.mancala.models.Game;

public interface GameDao {
    void create(Game game);
    Game retrieve(String id);
    void update(Game game);
}
