package com.tinygames.mancala.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTests {
    @Test
    public void testHostIsHost() {
        Player player = Player.HOST;
        assertTrue(player.isHost());
    }

    @Test
    public void testGuestIsHost() {
        Player player = Player.GUEST;
        assertFalse(player.isHost());
    }

    @Test
    public void testGuestIsGuest() {
        Player player = Player.GUEST;
        assertTrue(player.isGuest());
    }

    @Test
    public void testHostIsGuest() {
        Player player = Player.HOST;
        assertFalse(player.isGuest());
    }

    @Test
    public void testOppositeHost() {
        Player player = Player.HOST;
        assertEquals(Player.GUEST, player.getOpposite());
    }

    @Test
    public void testOppositeGuest() {
        Player player = Player.GUEST;
        assertEquals(Player.HOST, player.getOpposite());
    }
}
