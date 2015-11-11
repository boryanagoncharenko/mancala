package com.tinygames.mancala.models;

public class Game {
    private String id;
    private String host;
    private String guest;
    private int[] state;
    private String playerInTurn;

    public Game() {

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
}
