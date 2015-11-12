package com.tinygames.mancala.models;

import java.io.Serializable;

public class Game implements Serializable {
    private String id;
    private String host;
    private String guest;
    private int[] state;
    private String playerInTurn;

//    public Game() {
//        this.state = new int[] {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
//    }

    public Game(String id) {
        this.id = id;
        this.state = new int[] {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
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
//        if (this.playerInTurn == null) {
//            this.playerInTurn = this.host;
//        }
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
