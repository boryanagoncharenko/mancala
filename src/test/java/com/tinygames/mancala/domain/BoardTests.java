package com.tinygames.mancala.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTests {

    @Test
    public void testIsHostPitStart() throws Exception {
        Board board = new Board();
        boolean result = board.isHostPit(0);
        assertTrue(result);
    }

    @Test
    public void testIsHostPitEnd() throws Exception {
        Board board = new Board();
        boolean result = board.isHostPit(Board.hostKalah - 1);
        assertTrue(result);
    }

    @Test
    public void testIsHostPitKalah() throws Exception {
        Board board = new Board();
        boolean result = board.isHostPit(Board.hostKalah);
        assertFalse(result);
    }

    @Test
    public void testIsHostPitGuestPit() throws Exception {
        Board board = new Board();
        boolean result = board.isHostPit(Board.playerOffset);
        assertFalse(result);
    }

    @Test
    public void testIsHostPitGuestKalah() throws Exception {
        Board board = new Board();
        boolean result = board.isHostPit(Board.hostKalah + Board.playerOffset);
        assertFalse(result);
    }

    @Test
    public void testIsGuestPitStart() throws Exception {
        Board board = new Board();
        boolean result = board.isGuestPit(Board.playerOffset);
        assertTrue(result);
    }

    @Test
    public void testIsGuestPitEnd() throws Exception {
        Board board = new Board();
        boolean result = board.isGuestPit(Board.allPlayPits);
        assertTrue(result);
    }

    @Test
    public void testIsGuestPitKalah() throws Exception {
        Board board = new Board();
        boolean result = board.isGuestPit(Board.hostKalah + Board.playerOffset);
        assertFalse(result);
    }

    @Test
    public void testIsGuestPitHostPit() throws Exception {
        Board board = new Board();
        boolean result = board.isGuestPit(0);
        assertFalse(result);
    }

    @Test
    public void testIsGuestPitHostKalah() throws Exception {
        Board board = new Board();
        boolean result = board.isGuestPit(Board.hostKalah);
        assertFalse(result);
    }

    @Test
    public void testIsUsersPitHost() {
        Board board = new Board();
        boolean result = board.isUsersPit(0, Player.HOST);
        assertTrue(result);
    }

    @Test
    public void testIsUsersPitGuest() {
        Board board = new Board();
        boolean result = board.isUsersPit(0, Player.GUEST);
        assertFalse(result);
    }

    @Test
    public void testGetUserKalahHost() {
        Board board = new Board();
        int result = board.getUserKalah(Player.HOST);
        assertEquals(result, Board.hostKalah);
    }

    @Test
    public void testGetUserKalahGuest() {
        Board board = new Board();
        int result = board.getUserKalah(Player.GUEST);
        assertEquals(result, Board.hostKalah + Board.playerOffset);
    }

    @Test
    public void testGetStonesInPit() {
        Board board = new Board(Board.initialState);
        int result = board.getStonesInPit(0);
        assertEquals(result, Board.initialState[0]);
    }

    @Test(expected=AssertionError.class)
    public void testGetStonesInPitOutsideRange() {
        Board board = new Board(Board.initialState);
        board.getStonesInPit(Board.initialState.length);
    }

    @Test
    public void testIsPitEmptyFalse() {
        Board board = new Board(new int[] {1});
        boolean result = board.isPitEmpty(0);
        assertFalse(result);
    }

    @Test
    public void testIsPitEmpty() {
        Board board = new Board(new int[] {0});
        boolean result = board.isPitEmpty(0);
        assertTrue(result);
    }

    @Test
    public void testDistributionOfHostStonesRegular() {
        Board board = new Board(new int[] {3, 0, 1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 1, 5});
        MoveResult result = board.executeMove(0);
        assertFalse(result.isGameOver());
        assertEquals(3, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 1, 2, 3, 3, 4, 5, 0, 0, 0, 0, 0, 1, 5}, board);
    }

    @Test
    public void testDistributionOfHostStonesPastOppKalah() {
        Board board = new Board(new int[] {1, 1, 1, 1, 1, 12, 5, 0, 0, 0, 0, 0, 1, 5});
        MoveResult result = board.executeMove(5);
        assertFalse(result.isGameOver());
        assertEquals(4, result.getLastUpdatedPit());
        compareBoardState(new int[]{2, 2, 2, 2, 2, 0, 6, 1, 1, 1, 1, 1, 2, 5}, board);
    }

    @Test
    public void testDistributionOfHostStonesRotateTwice() {
        Board board = new Board(new int[] {1, 1, 1, 1, 1, 25, 5, 0, 0, 0, 0, 0, 1, 5});
        MoveResult result = board.executeMove(5);
        assertFalse(result.isGameOver());
        assertEquals(4, result.getLastUpdatedPit());
        compareBoardState(new int[]{3, 3, 3, 3, 3, 1, 7, 2, 2, 2, 2, 2, 3, 5}, board);
    }

    @Test
    public void testExecuteMoveHostEndsInKalah() {
        Board board = new Board(new int[] {6, 0, 1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 1, 5});
        MoveResult result = board.executeMove(0);
        assertFalse(result.isGameOver());
        assertEquals(6, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 1, 2, 3, 4, 5, 6, 0, 0, 0, 0, 0, 1, 5}, board);
    }

    @Test
    public void testExecuteMoveHostCaptures() {
        Board board = new Board(new int[] {1, 0, 0, 0, 0, 1, 5, 0, 0, 0, 0, 5, 1, 5});
        MoveResult result = board.executeMove(0);
        assertFalse(result.isGameOver());
        assertEquals(1, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 0, 0, 0, 0, 1, 11, 0, 0, 0, 0, 0, 1, 5}, board);
    }

    @Test
    public void testExecuteMoveHostNotCaptures() {
        Board board = new Board(new int[] {0, 0, 0, 0, 5, 3, 5, 0, 0, 0, 0, 0, 0, 5});
        MoveResult result = board.executeMove(5);
        assertFalse(result.isGameOver());
        assertEquals(8, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 0, 0, 0, 5, 0, 6, 1, 1, 0, 0, 0, 0, 5}, board);
    }

    @Test
    public void testExecuteMoveHostCapturesRotate() {
        Board board = new Board(new int[] {0, 0, 0, 0, 0, 9, 5, 0, 0, 0, 0, 5, 1, 5});
        MoveResult result = board.executeMove(5);
        assertFalse(result.isGameOver());
        assertEquals(1, result.getLastUpdatedPit());
        compareBoardState(new int[]{1, 0, 0, 0, 0, 0, 13, 1, 1, 1, 1, 0, 2, 5}, board);
    }

    @Test
    public void testGameEndsHost() {
        Board board = new Board(new int[] {0, 0, 0, 0, 0, 2, 5, 0, 2, 3, 4, 5, 6, 1});
        MoveResult result = board.executeMove(5);
        assertTrue(result.isGameOver());
        assertEquals(7, result.getLastUpdatedPit());
        compareBoardState(new int[]{ 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 22}, board);
    }

    @Test
    public void testGameEndsHostKalah() {
        Board board = new Board(new int[] {0, 0, 0, 0, 0, 1, 5, 0, 2, 3, 4, 5, 6, 1});
        MoveResult result = board.executeMove(5);
        assertTrue(result.isGameOver());
        assertEquals(6, result.getLastUpdatedPit());
        compareBoardState(new int[]{ 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 21}, board);
    }

    @Test
    public void testDistributionOfGuestStonesRegular() {
        Board board = new Board(new int[] {6, 0, 0, 0, 0, 0, 5, 3, 0, 1, 2, 3, 4, 5});
        MoveResult result = board.executeMove(7);
        assertFalse(result.isGameOver());
        assertEquals(10, result.getLastUpdatedPit());
        compareBoardState(new int[]{6, 0, 0, 0, 0, 0, 5, 0, 1, 2, 3, 3, 4, 5}, board);
    }

    @Test
    public void testDistributionOfGuestStonesPastOppKalah() {
        Board board = new Board(new int[] {0, 0, 0, 0, 0, 1, 5, 1, 1, 1, 1, 1, 12, 5});
        MoveResult result = board.executeMove(12);
        assertFalse(result.isGameOver());
        assertEquals(11, result.getLastUpdatedPit());
        compareBoardState(new int[]{1, 1, 1, 1, 1, 2, 5, 2, 2, 2, 2, 2, 0, 6}, board);
    }

    @Test
    public void testDistributionOfGuestStonesRotateTwice() {
        Board board = new Board(new int[] {0, 0, 0, 0, 0, 1, 5, 1, 1, 1, 1, 1, 25, 5});
        MoveResult result = board.executeMove(12);
        assertFalse(result.isGameOver());
        assertEquals(11, result.getLastUpdatedPit());
        compareBoardState(new int[]{2, 2, 2, 2, 2, 3, 5, 3, 3, 3, 3, 3, 1, 7}, board);
    }

    @Test
    public void testExecuteMoveGuestEndsInKalah() {
        Board board = new Board(new int[] {6, 0, 0, 0, 0, 0, 5, 6, 0, 1, 2, 3, 4, 5});
        MoveResult result = board.executeMove(7);
        assertFalse(result.isGameOver());
        assertEquals(13, result.getLastUpdatedPit());
        compareBoardState(new int[]{6, 0, 0, 0, 0, 0, 5, 0, 1, 2, 3, 4, 5, 6}, board);
    }

    @Test
    public void testExecuteMoveGuestCaptures() {
        Board board = new Board(new int[] {0, 0, 0, 0, 5, 1, 5, 1, 0, 0, 0, 0, 1, 5});
        MoveResult result = board.executeMove(7);
        assertFalse(result.isGameOver());
        assertEquals(8, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 0, 0, 0, 0, 1, 5, 0, 0, 0, 0, 0, 1, 11}, board);
    }

    @Test
    public void testExecuteMoveGuestNotCaptures() {
        Board board = new Board(new int[] {0, 0, 0, 0, 0, 1, 5, 0, 0, 0, 0, 5, 3, 5});
        MoveResult result = board.executeMove(12);
        assertFalse(result.isGameOver());
        assertEquals(1, result.getLastUpdatedPit());
        compareBoardState(new int[]{1, 1, 0, 0, 0, 1, 5, 0, 0, 0, 0, 5, 0, 6}, board);
    }

    @Test
    public void testExecuteMoveGuestCapturesRotate() {
        Board board = new Board(new int[] {0, 0, 0, 0, 5, 1, 5, 0, 0, 0, 0, 0, 9, 5});
        MoveResult result = board.executeMove(12);
        assertFalse(result.isGameOver());
        assertEquals(8, result.getLastUpdatedPit());
        compareBoardState(new int[]{1, 1, 1, 1, 0, 2, 5, 1, 0, 0, 0, 0, 0, 13}, board);
    }

    @Test
    public void testGameEndsGuest() {
        Board board = new Board(new int[] {0, 2, 3, 4, 5, 6, 1, 0, 0, 0, 0, 0, 2, 5});
        MoveResult result = board.executeMove(12);
        assertTrue(result.isGameOver());
        assertEquals(0, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 6}, board);
    }

    @Test
    public void testGameEndsGuestKalah() {
        Board board = new Board(new int[] {0, 2, 3, 4, 5, 6, 1, 0, 0, 0, 0, 0, 1, 5});
        MoveResult result = board.executeMove(12);
        assertTrue(result.isGameOver());
        assertEquals(13, result.getLastUpdatedPit());
        compareBoardState(new int[]{0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 6}, board);
    }

    public static void compareBoardState(int[] expectedState, Board board) {
        for (int i = 0; i < expectedState.length; i++) {
            assertEquals(expectedState[i], board.getStonesInPit(i));
        }
    }
}
