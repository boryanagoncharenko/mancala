package com.tinygames.mancala.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && this.getId().equals(((User) obj).getId());
    }

    @Override
    public int hashCode()
    {
        return this.getId().hashCode();
    }
}
