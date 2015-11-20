package com.tinygames.mancala.service;

import javax.servlet.http.HttpServletRequest;

public interface SessionManager {
    String retrieveUserId(HttpServletRequest req);
}
