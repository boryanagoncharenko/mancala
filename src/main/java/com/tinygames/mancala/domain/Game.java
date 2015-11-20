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

    public Game(String id, Board board)
    {
        this(id);
        this.board = board;
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
            this.updateInTurn();
            return true;
        }
        return false;
    }

    public boolean isMoveLegal(User user, int pit) {
        return this.isPlayerInTurn(user) &&
               !this.board.isPitEmpty(pit) &&
               this.isUserAllowedToMovePit(user, pit);
    }

    public void makeMove(User user, int pit) {
        Player player = this.userToPlayer(user);
        MoveResult result = this.board.executeMove(pit);
        if (result.isGameOver()) {
            this.setWinner();
        }
        else if (result.getLastUpdatedPit() != this.board.getUserKalah(player)) {
            this.inTurn = this.inTurn.getOpposite();
        }
    }

    private boolean tryAddHost(User user) {
        if (this.host == null) {
            this.host = user;
            return true;
        }
        return false;
    }

    private boolean tryAddGuest(User user) {
        if (!this.host.equals(user) && this.guest == null) {
            this.guest = user;
            return true;
        }
        return false;
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

    private void updateInTurn() {
        if (this.inTurn == null && this.guest != null) {
            this.inTurn = Player.GUEST;
        }
    }

    private void setWinner() {
        int hostResult = this.board.getScore(Player.HOST);
        int guestResult = this.board.getScore(Player.GUEST);
        this.winner = Player.GUEST;
        if (hostResult > guestResult) {
            this.winner = Player.HOST;
        }
    }
}
