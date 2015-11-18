package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.GameEntity;

public interface GameDao {
    void create(GameEntity game);
    GameEntity retrieve(String id);
    void update(GameEntity game);
}
