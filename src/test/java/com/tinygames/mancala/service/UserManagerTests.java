package com.tinygames.mancala.service;


import com.tinygames.mancala.domain.User;
import com.tinygames.mancala.repository.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserManagerTests {
    @Mock
    private UserDao dao;

    @InjectMocks
    private UserManagerImpl manager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = manager.createUser();
        verify(dao).create(user);
        assertNotNull(user);
    }

    @Test
    public void testRetrieveUser() {
        String userId = "userId";
        User user = new User(userId);
        when(dao.retrieve(userId)).thenReturn(user);
        User retrievedUser = manager.retrieveUser(userId);
        assertEquals(user, retrievedUser);
    }
}
