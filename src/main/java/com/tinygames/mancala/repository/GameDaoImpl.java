package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameDaoImpl implements GameDao {
    private static final String OBJECT_KEY = "GAME";

    @Autowired
    protected RedisTemplate<String, String> template;

    @Override
    public void create(Game game) {
        this.template.opsForHash().put(OBJECT_KEY, game.getId(), game);
    }

    @Override
    public Game retrieve(String id) {
        return (Game) this.template.opsForHash().get(OBJECT_KEY, id);
    }

    @Override
    public void update(Game game) {
        this.template.opsForHash().put(OBJECT_KEY, game.getId(), game);
    }
}
