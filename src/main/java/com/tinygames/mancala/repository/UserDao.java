package com.tinygames.mancala.repository;

import com.tinygames.mancala.domain.UserEntity;

public interface UserDao {
    void create(UserEntity user);
    UserEntity retrieve(String id);
}
