package com.tinygames.mancala.domain;

import java.io.Serializable;
import java.util.Arrays;

public class Board implements Serializable {
    public static final int[] initialState = new int[] {4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
    public static final int allPits = 14;
    public static final int allPlayPits = allPits - 2;
    public static final int playerOffset = allPits / 2;
    public static final int hostKalah = 6;
    private int[] state;

    public Board() {
        this.state = initialState;
    }

    public Board(int[] state) {
        this.state = state;
    }

    public int[] getState() {
        return this.state;
    }

    public int getStonesInPit(int pit) {
        assert pit >= 0 && pit < allPits;
        return this.state[pit];
    }

    public boolean isPitEmpty(int pit) {
        return this.getStonesInPit(pit) == 0;
    }

    public boolean isUsersPit(int pit, Player player) {
        return player.isHost() ? this.isHostPit(pit) : this.isGuestPit(pit);
    }

    public boolean isHostPit(int pit) {
        return pit >= 0 && pit <= hostKalah - 1;
    }

    public boolean isGuestPit(int pit) {
        return pit >= playerOffset && pit <= allPlayPits;
    }

    public int getUserKalah(Player player) {
        if (player.isHost()) {
            return hostKalah;
        }
        return hostKalah + playerOffset;
    }

    public int getScore(Player player) {
        return this.getStonesInPit(this.getUserKalah(player));
    }

    public MoveResult executeMove(int pit) {
        int kalah = this.getUserKalahFromPit(pit);
        int lastIndex = this.distributeStones(pit, kalah);
        if (this.isStoneCapturing(pit, lastIndex)) {
            this.captureStones(lastIndex, kalah);
        }

        boolean isGameOver = this.checkForGameOver();
        return new MoveResult(isGameOver, lastIndex);
    }

    private int distributeStones(int pit, int kalah) {
        int oppKalah = (kalah + playerOffset) % allPits;
        int numberOfStones = this.state[pit];
        this.state[pit] = 0;
        int offset = 0;
        for (int i = 1; i <= numberOfStones; i++) {
            int index = (pit + i + offset) % allPits;
            if (index == oppKalah) {
                index = (index + 1) % allPits;
                offset++;
            }
            this.state[index]++;
        }
        // return index of the pit where the last stone was dropped
        return (pit + numberOfStones + offset) % allPits;
    }

    private boolean isStoneCapturing(int firstIndex, int lastIndex) {
        return this.areSameUserPits(firstIndex, lastIndex) && // The stone lands on the player's pits
               this.state[lastIndex] == 1 && // The pit was empty
               this.state[this.getOppositePit(lastIndex)] > 0; // The opposite pit is not empty
    }

    private boolean areSameUserPits(int firstPit, int secondPit) {
        return (this.isHostPit(firstPit) && this.isHostPit(secondPit)) ||
               (this.isGuestPit(firstPit) && this.isGuestPit(secondPit));
    }

    private void captureStones(int lastIndex, int kalahIndex) {
        int oppositePit = this.getOppositePit(lastIndex);
        this.state[kalahIndex] += this.state[lastIndex] + this.state[oppositePit];
        this.state[lastIndex] = 0;
        this.state[oppositePit] = 0;
    }

    private boolean checkForGameOver() {
        if (this.isSideEmpty(Player.HOST)) {
            this.emptySide(Player.GUEST);
            return true;
        }
        if (this.isSideEmpty(Player.GUEST)) {
            this.emptySide(Player.HOST);
            return true;
        }
        return false;
    }

    private boolean isSideEmpty(Player side) {
        int offset = side.isHost() ? 0 : playerOffset;
        for (int i = 0; i < allPlayPits / 2; i++) {
            if (this.state[offset + i] > 0) {
                return false;
            }
        }
        return true;
    }

    private void emptySide(Player side) {
        int offset = side.isHost() ? 0 : playerOffset;
        int kalahIndex = this.getUserKalah(side);
        for (int i = 0; i < allPlayPits / 2; i++) {
            this.state[kalahIndex] += this.state[offset + i];
            this.state[offset + i] = 0;
        }
    }

    private int getUserKalahFromPit(int pit) {
        if (pit < hostKalah) {
            return hostKalah;
        }
        return hostKalah + playerOffset;
    }

    private int getOppositePit(int pit) {
        assert this.isHostPit(pit) || this.isGuestPit(pit);
        return allPlayPits - pit;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Board && Arrays.equals(this.state, ((Board) obj).state);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(this.state);
    }
}
