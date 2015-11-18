package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao
{
    private static final String OBJECT_KEY = "USER";

    @Autowired
    protected RedisTemplate<String, String> template;

    public void create(UserEntity user) {
        this.template.opsForHash().put(OBJECT_KEY, user.getId(), user);
    }

    public UserEntity retrieve(String inst_key) {
        return (UserEntity) this.template.opsForHash().get(OBJECT_KEY, inst_key);
    }
}
