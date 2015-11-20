package com.tinygames.mancala.domain;

public class MoveResult {
    private boolean isGameOver;
    private int lastUpdatedPit;

    public MoveResult(boolean isGameOver, int lastUpdatedPit) {
        this.isGameOver = isGameOver;
        this.lastUpdatedPit = lastUpdatedPit;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getLastUpdatedPit() {
        return lastUpdatedPit;
    }
}
