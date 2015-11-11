package com.tinygames.mancala.models.dao;

import com.tinygames.mancala.models.Game;
import org.springframework.stereotype.Repository;

@Repository
public class GameDaoImpl extends DaoBase implements GameDao {
    private static final String OBJECT_KEY = "GAME";

    @Override
    public void create(Game game) {
        this.template.opsForHash().put(OBJECT_KEY, game.getId(), game);
    }

    public Game retrieve(String id) {
        return (Game) this.template.opsForHash().get(OBJECT_KEY, id);
    }
}
