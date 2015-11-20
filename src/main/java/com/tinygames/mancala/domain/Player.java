package com.tinygames.mancala.domain;

public enum Player {
    HOST (true),
    GUEST (false);

    private final boolean isHost;

    private Player(boolean isHost) {
        this.isHost = isHost;
    }

    public boolean isHost() {
        return this.isHost;
    }

    public boolean isGuest() {
        return !this.isHost();
    }

    public Player getOpposite() {
        return this.isHost() ? Player.GUEST : Player.HOST;
    }
}
