package com.tinygames.mancala.domain;

import java.io.Serializable;

public class GameEntity implements Serializable {
    private String id;
    private String host;
    private String guest;
    private int[] state;
    private String playerInTurn;
    private String winner;

    public GameEntity() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public int[] getState() {
        return state;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public String getPlayerInTurn() {
        return playerInTurn;
    }

    public void setPlayerInTurn(String playerInTurn) {
        this.playerInTurn = playerInTurn;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}