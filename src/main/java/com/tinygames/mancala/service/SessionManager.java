package com.tinygames.mancala.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class SessionManager {

    public String retrieveUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String userKey = "userId";
        String userId = (String) session.getAttribute(userKey);

        if (userId == null) {
            String a = this.requestUserId();
            userId = Helpers.jsonFromString(a).getString("id");
            session.setAttribute(userKey , userId);
        }

        return userId;
    }

    private String requestUserId() {
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
