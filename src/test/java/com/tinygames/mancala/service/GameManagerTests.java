package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.Game;
import com.tinygames.mancala.repository.GameDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameManagerTests {
    @Mock
    private GameDao dao;

    @InjectMocks
    private GameManagerImpl manager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateGame() {
        Game game = manager.createGame();
        verify(dao).create(game);
        assertNotNull(game);
    }

    @Test
    public void testRetrieveUser() {
        String gameId = "gameId";
        Game game = new Game(gameId);
        when(dao.retrieve(gameId)).thenReturn(game);
        Game retrievedGame = manager.retrieveGame(gameId);
        assertEquals(game, retrievedGame);
    }
}
