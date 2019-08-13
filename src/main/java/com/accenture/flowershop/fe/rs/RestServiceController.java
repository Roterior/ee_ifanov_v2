package com.accenture.flowershop.fe.rs;

import com.accenture.flowershop.be.business.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/")
public class RestServiceController {

    private final ClientService clientService;

    @Autowired
    public RestServiceController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getClient/{login}")
    public boolean ifExist(@PathParam("login") String login) {
        return clientService.getByLogin(login) != null;
    }
}
