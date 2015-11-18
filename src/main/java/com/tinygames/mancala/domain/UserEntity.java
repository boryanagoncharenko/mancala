package com.tinygames.mancala.domain;

import java.io.Serializable;

public class UserEntity implements Serializable {
    private String id;

    public UserEntity() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
