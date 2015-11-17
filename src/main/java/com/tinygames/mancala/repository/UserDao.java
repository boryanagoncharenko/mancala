package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.User;

public interface UserDao {
    void create(User user);
    User retrieve(String id);
}
