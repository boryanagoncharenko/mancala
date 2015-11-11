package com.tinygames.mancala.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model, HttpSession session)
    {
//        session.setAttribute("success" , "successfully accessed");
        model.addAttribute("message", "Hello world!");
        return "main";
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    public String play(ModelMap model)
    {
//        session.setAttribute("success" , "successfully accessed");

        return "game";
    }
}