package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.UserEntity;
import com.tinygames.mancala.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao dao;

    public UserEntity createUser() {
        String uniqueId = UUID.randomUUID().toString();
        UserEntity user = new UserEntity();
        user.setId(uniqueId);
        this.dao.create(user);
        return user;
    }
}
