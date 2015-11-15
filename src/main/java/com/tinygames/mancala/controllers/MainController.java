package com.tinygames.mancala.controllers;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
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

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/{gameID}", method = RequestMethod.GET)
    public String play(@PathVariable String gameID, ModelMap model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userKey = "userID";
        String userID = (String) session.getAttribute(userKey);

        if (userID == null) {
            String id = Helpers.jsonFromString(this.requestUserID()).getString(userKey);
            session.setAttribute(userKey , id);
            model.addAttribute(userKey, id);
        }

        model.addAttribute("gameID", gameID);
        return "game";
    }

    private String requestUserID() {
        Client client = Client.create();
        String env = "http://localhost:8080/";
        WebResource webResource = client.resource(env + "users");
        ClientResponse response = webResource.accept("application/json")
                .post(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response.getEntity(String.class);
    }
}