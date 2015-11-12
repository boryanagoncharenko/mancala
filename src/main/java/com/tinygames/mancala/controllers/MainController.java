package com.tinygames.mancala.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserController userController;

    @RequestMapping(method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public String play(@PathVariable String gameId, ModelMap model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userID = (String) session.getAttribute("userID");
        if (userID == null) {
            // create request to rest api (/users POST)
            String id = userController.createUser();
            // id contains json, parse it and return only the actual id
            session.setAttribute("userID" , id);
            model.addAttribute("userID", id);
        }

        model.addAttribute("gameID", gameId);
        return "game";
    }
}