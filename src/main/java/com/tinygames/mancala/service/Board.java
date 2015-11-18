package com.tinygames.mancala.service;

public class Board {
    private final int[] initialState = new int[] {4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
    private int[] state;

    public Board() {
        this.state = initialState;
    }

    public Board(int[] state) {
        this.state = state;
    }

    public int[] getState() {
        return state;
    }

    public void setState(int[] state) {
        this.state = state;
    }
}
