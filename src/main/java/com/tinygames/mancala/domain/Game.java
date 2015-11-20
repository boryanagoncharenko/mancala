package com.tinygames.mancala.domain;

import java.io.Serializable;

public class Game implements Serializable {
    private String id;
    private User host;
    private User guest;
    private Board board;
    private Player inTurn;
    private Player winner;

    public Game(String id) {
        this.id = id;
        this.board = new Board();
    }

    public String getId() {
        return this.id;
    }

    public User getHost() {
        return this.host;
    }

    public User getGuest() {
        return this.guest;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getInTurn() {
        return this.inTurn;
    }

    public Player getWinner() {
        return this.winner;
    }

    public boolean tryAddUser(User user) {
        if (this.tryAddHost(user) || this.tryAddGuest(user)) {
            if (this.inTurn == null) {
                this.inTurn = Player.GUEST;
            }
            return true;
        }
        return false;
    }

    public boolean isMoveLegal(User user, int pit) {
        return this.isPlayerInTurn(user) &&
                this.board.isPitEmpty(pit) &&
                this.isUserAllowedToMovePit(user, pit);
    }

    public void makeMove(User user, int pit) {
        Player player = this.userToPlayer(user);
        MoveResult result = this.board.executeMove(pit, player);
        if (result.isGameOver()) {
            int hostResult = this.board.getStonesInPit(this.board.getUserKalah(Player.HOST));
            int guestResult = this.board.getStonesInPit(this.board.getUserKalah(Player.GUEST));
            this.winner = guestResult >= hostResult ? Player.GUEST : Player.HOST;
        }
        else {
            if (result.getLastUpdatedPit() != this.board.getUserKalah(player)) {
                this.inTurn = this.inTurn.getOpposite();
            }
        }
    }

    private boolean tryAddHost(User user) {
        if (this.host == null) {
            this.host = user;
        }
        return this.host != null;
    }

    private boolean tryAddGuest(User user) {
        if (!this.host.equals(user) && this.guest == null) {
            this.guest = user;
        }
        return this.guest != null;
    }

    private boolean isUserAllowedToMovePit(User user, int pit) {
        Player player = this.userToPlayer(user);
        return this.board.isUsersPit(pit, player);
    }

    private boolean isPlayerInTurn(User user) {
        return (user.getId().equals(this.getHost().getId()) && this.inTurn.isHost()) ||
               (user.getId().equals(this.getGuest().getId()) && this.inTurn.isGuest());
    }

    private Player userToPlayer(User user) {
        if (user.equals(this.host)) {
            return Player.HOST;
        }
        return Player.GUEST;
    }
}
