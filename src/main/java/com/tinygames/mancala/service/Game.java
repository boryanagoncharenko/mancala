package com.tinygames.mancala.service;

import com.tinygames.mancala.domain.GameEntity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Game implements Serializable {
    private String id;
    private Board board;
    private User host;
    private User guest;
    private User playerInTurn;
    private User winner;

    public Game(String id) {
        this.id = id;
        this.board = new Board();
    }

    public Game(GameEntity entity) {
        this.id = entity.getId();
        this.board = new Board(entity.getState());
        if (entity.getHost() != null) {
            this.host = new User(entity.getHost());
        }
        if (entity.getGuest() != null) {
            this.guest = new User(entity.getGuest());
        }
        if (entity.getPlayerInTurn() != null) {
            this.playerInTurn = new User(entity.getPlayerInTurn());
        }
        if (entity.getWinner() != null) {
            this.winner = new User(entity.getWinner());
        }
    }

    public String getId() {
        return this.id;
    }

    public GameEntity toEntity() {
        GameEntity entity = new GameEntity();
        entity.setId(this.id);
        entity.setState(this.board.getState());

        if (this.host != null) {
            entity.setHost(this.host.getId());
        }

        if (this.guest != null) {
            entity.setGuest(this.guest.getId());
        }

        if (this.playerInTurn != null) {
            entity.setPlayerInTurn(this.playerInTurn.getId());
        }

        if (this.winner != null) {
            entity.setWinner(this.winner.getId());
        }

        return entity;
    }

    public boolean tryAddUser(User user) {
        return this.tryAddHost(user) || this.tryAddGuest(user);
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

    public void setPlayerInTurn(User user) {
        this.playerInTurn = user;
    }

    public User getGuest() {
        return this.guest;
    }
}
