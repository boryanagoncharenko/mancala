package com.tinygames.mancala.controllers;

import com.tinygames.mancala.service.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    SessionManager sessionManager;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public String play(@PathVariable String gameId, ModelMap model, HttpServletRequest req) {
        String userId = this.sessionManager.retrieveUserId(req);
        model.addAttribute("userId", userId);
        model.addAttribute("gameId", gameId);

        return "game";
    }
}