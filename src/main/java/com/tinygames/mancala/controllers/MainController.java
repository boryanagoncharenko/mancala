package com.tinygames.mancala.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String printWelcome(ModelMap model, HttpSession session)
    {
//        session.setAttribute("success" , "successfully accessed");
        model.addAttribute("message", "Hello world!");
        return "main";
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public String play(ModelMap model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userID = (String) session.getAttribute("userID");
        if (userID == null) {
            String id = userController.createUser();
            session.setAttribute("userID" , id);
            model.addAttribute("userID", id);
        }

        return "game";
    }
}