package com.tinygames.mancala.domain;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GameTests {
    private Game game;
    private User host;
    private User guest;
    private Board mockBoard;

    @Before
    public void setup() {
        mockBoard = mock(Board.class);
        this.game = new Game("game", mockBoard);
        this.host = new User("host");
        this.guest = new User("guest");
    }

    @Test
    public void testTryAddHost() {
        game.tryAddUser(host);
        assertEquals(host, game.getHost());
        assertNull(game.getGuest());
    }

    @Test
    public void testTryAddHostTwice() {
        game.tryAddUser(host);
        game.tryAddUser(host);
        assertEquals(host, game.getHost());
        assertNull(game.getGuest());
    }

    @Test
    public void testTryAddGuest() {
        game.tryAddUser(host);
        game.tryAddUser(guest);
        assertEquals(host, game.getHost());
        assertEquals(guest, game.getGuest());
    }

    @Test
    public void testTryAddThirdUser() {
        User visitor = new User("visitor");
        game.tryAddUser(host);
        game.tryAddUser(guest);
        boolean added = game.tryAddUser(visitor);
        assertFalse(added);
        assertEquals(host, game.getHost());
        assertEquals(guest, game.getGuest());
    }

    @Test
    public void testIsMoveLegal() {
        when(mockBoard.isPitEmpty(6)).thenReturn(false);
        when(mockBoard.isUsersPit(6, Player.GUEST)).thenReturn(true);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        boolean result = game.isMoveLegal(guest, 6);
        assertTrue(result);
    }

    @Test
    public void testIsMoveLegalNotInTurnHost() {
        game.tryAddUser(host);
        game.tryAddUser(guest);
        boolean result = game.isMoveLegal(host, 0);
        assertFalse(result);
    }

    @Test
    public void testIsMoveLegalNotInTurnVisitor() {
        game.tryAddUser(host);
        game.tryAddUser(guest);
        boolean result = game.isMoveLegal(new User("visitor"), 0);
        assertFalse(result);
    }

    @Test
    public void testIsMoveLegalEmptyPit() {
        when(mockBoard.isPitEmpty(6)).thenReturn(true);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        boolean result = game.isMoveLegal(guest, 6);
        assertFalse(result);
    }

    @Test
    public void testIsMoveLegalNotUsersPit() {
        when(mockBoard.isPitEmpty(4)).thenReturn(false);
        when(mockBoard.isUsersPit(4, Player.GUEST)).thenReturn(false);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        boolean result = game.isMoveLegal(guest, 4);
        assertFalse(result);
    }

    @Test
    public void testMakeMoveRegular() {
        when(mockBoard.executeMove(6)).thenReturn(new MoveResult(false, 7));
        when(mockBoard.getUserKalah(Player.GUEST)).thenReturn(13);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        game.makeMove(guest, 6);
        assertEquals(Player.HOST, game.getInTurn());
    }

    @Test
    public void testMakeMoveEndInKalah() {
        when(mockBoard.executeMove(6)).thenReturn(new MoveResult(false, 13));
        when(mockBoard.getUserKalah(Player.GUEST)).thenReturn(13);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        game.makeMove(guest, 6);
        assertEquals(Player.GUEST, game.getInTurn());
    }

    @Test
    public void testMakeMoveGameOverGuestWins() {
        when(mockBoard.executeMove(6)).thenReturn(new MoveResult(true, 7));
        when(mockBoard.getScore(Player.HOST)).thenReturn(1);
        when(mockBoard.getScore(Player.GUEST)).thenReturn(2);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        game.makeMove(guest, 6);
        assertEquals(Player.GUEST, game.getWinner());
    }

    @Test
    public void testMakeMoveGameOverHostWins() {
        when(mockBoard.executeMove(6)).thenReturn(new MoveResult(true, 7));
        when(mockBoard.getScore(Player.HOST)).thenReturn(2);
        when(mockBoard.getScore(Player.GUEST)).thenReturn(1);
        game.tryAddUser(host);
        game.tryAddUser(guest);
        game.makeMove(guest, 6);
        assertEquals(Player.HOST, game.getWinner());
    }
}
