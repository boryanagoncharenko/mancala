package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao
{
    private static final String OBJECT_KEY = "USER";

    @Qualifier("redisTemplateUser")
    @Autowired
    protected RedisTemplate<String, User> template;

    public void create(User user) {
        this.template.opsForHash().put(OBJECT_KEY, user.getId(), user);
    }

    public User retrieve(String inst_key) {
        return (User) this.template.opsForHash().get(OBJECT_KEY, inst_key);
    }
}
