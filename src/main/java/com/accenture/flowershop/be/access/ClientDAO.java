package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Client;

public interface ClientDAO {

    Client getByLogin(String login);

    Client add(Client client);

    Client update(Client client);

}
