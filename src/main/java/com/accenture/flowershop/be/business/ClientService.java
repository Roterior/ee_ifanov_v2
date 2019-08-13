package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.ClientDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public interface ClientService {

    Client login(String login, String password);

    Client register(Client client);

    Client updateBalance(String login, BigDecimal balance);

    Client getByLogin(String login);

    Integer getNewDiscount(String login);

     void loginPage(HttpServletRequest req, HttpServletResponse resp);

     List<Purchase> getPurchaseList(Client client);

}
