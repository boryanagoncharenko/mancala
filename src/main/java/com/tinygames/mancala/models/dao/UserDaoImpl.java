package com.tinygames.mancala.models.dao;

import com.tinygames.mancala.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends DaoBase implements UserDao
{
    private static final String OBJECT_KEY = "USER";

    public void create(User user) {
        this.template.opsForHash().put(OBJECT_KEY, user.getId(), user);
    }

    public User retrieve(String inst_key) {
        return (User) this.template.opsForHash().get(OBJECT_KEY, inst_key);
    }
}
