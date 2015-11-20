package com.tinygames.mancala.controllers;

import com.tinygames.mancala.domain.Game;
import com.tinygames.mancala.domain.User;
import com.tinygames.mancala.service.GameManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class GameControllerTests {
    private MockMvc mockMvc;

    @Mock
    private GameManager manager;

    @InjectMocks
    private GameController gameController;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    public void create() throws Exception {
        Game game = new Game("gameId");
        when(manager.createGame()).thenReturn(game);
        mockMvc.perform(post("/games/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(game.getId()));

        verify(manager).createGame();
    }

    @Test
    public void retrieve() throws Exception {
        Game game = new Game("gameId");
        when(manager.retrieveGame("gameId")).thenReturn(game);
        mockMvc.perform(get("/games/gameId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(game.getId()));

        verify(manager).retrieveGame("gameId");
    }

    @Test
    public void addUser() throws Exception {
        Game game = new Game("gameId");
        User user = new User("userId");
        game.tryAddUser(user);
        when(manager.addUser("gameId", "userId")).thenReturn(game);
        mockMvc.perform(post("/games/gameId/add/userId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(game.getId()))
                .andExpect(jsonPath("$.host.id").value(user.getId()));

        verify(manager).addUser("gameId", "userId");
    }
}
