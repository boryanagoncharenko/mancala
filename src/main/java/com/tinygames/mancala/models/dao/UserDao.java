package com.tinygames.mancala.models.dao;

import com.tinygames.mancala.models.User;

public interface UserDao {
    void create(User user);
    User retrieve(String id);
}
