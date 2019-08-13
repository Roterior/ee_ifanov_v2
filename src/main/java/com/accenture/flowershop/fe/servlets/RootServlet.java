package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.ClientService;
import com.accenture.flowershop.be.business.FlowerService;
import com.accenture.flowershop.be.business.PurchaseService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.ClientDTO;
import com.accenture.flowershop.fe.dto.FlowerDTO;
import com.accenture.flowershop.fe.dto.PurchaseDTO;
import com.accenture.flowershop.fe.enums.PurchaseStatus;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class RootServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private PurchaseService purchaseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ClientDTO client = (ClientDTO) session.getAttribute("client");

        if (client == null) {
            resp.sendRedirect("/login");
        }
        else {
            String act = req.getParameter("act") != null ? req.getParameter("act") : "";

            switch (act) {
                case "Search": {
                    flowerService.onSearchButton(req, resp);
                    doPost(req, resp);
                    break;
                }
                case "+": {
                    Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));
                    int quantityToBuy = req.getParameter("quantityToBuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantityToBuy"));

                    FlowerDTO flowerDTO = mapToFlowerDTO(flowerService.getById(id));

                    if (flowerDTO != null && quantityToBuy <= flowerDTO.getQuantity() && quantityToBuy > 0) {
                        flowerService.onAddButton(req, resp);
                        doPost(req, resp);
                    }
                    break;
                }
                case "x": {
                    flowerService.onRemoveButton(req, resp);
                    doPost(req, resp);
                    break;
                }
                case "Order": {
                    PurchaseDTO purchase = new PurchaseDTO();
                    purchase.setClient(client);

                    BigDecimal sum = BigDecimal.ZERO;
                    if (session.getAttribute("sum") != null) {
                        sum = new BigDecimal(session.getAttribute("sum").toString());
                        purchase.setTotalPrice(new BigDecimal(session.getAttribute("sum").toString()));
                    }

                    if (sum.compareTo(BigDecimal.ONE) > 0) {
                       purchaseService.onOrderButton(req, resp, purchase);
                        doPost(req, resp);
                    }
                    break;
                }
                case "pay": {
                    Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));

                    PurchaseDTO purchase = mapToPurchaseDTO(purchaseService.getById(id));
                    if (purchase != null && purchase.getStatus().equals(PurchaseStatus.CREATED)) {
                        if (client.getBalance().compareTo(purchase.getTotalPrice()) > 0) {
                            purchaseService.onPayButton(req, resp, purchase);
                            doPost(req, resp);
                        }
                    }
                    break;
                }
                default: {
                    ClientDTO clientTemp = mapToClientDTO(clientService.getByLogin(client.getLogin()));
                    session.setAttribute("client", clientTemp);

                    String searchName = (String) session.getAttribute("name");
                    BigDecimal searchFrom = (BigDecimal) session.getAttribute("from");
                    BigDecimal searchTo = (BigDecimal) session.getAttribute("to");

                    FlowerFilter flowerFilter = new FlowerFilter();
                    flowerFilter.setName(searchName);
                    flowerFilter.setFromPrice(searchFrom);
                    flowerFilter.setToPrice(searchTo);

                    List<FlowerDTO> flowersList = mapToFlowerDTOList(flowerService.getByFilter(flowerFilter));
                    if (flowersList != null && flowersList.size() != 0) {
                        session.setAttribute("flowersList", flowersList);
                    }
                    else {
                        session.setAttribute("flowersList", null);
                    }

                    List<PurchaseDTO> purchaseList = mapToPurchaseDTOList(purchaseService.getByLogin(client.getLogin()));
//                    List<PurchaseDTO> purchaseList = mapToPurchaseDTOList(clientService.getPurchaseList(mapToClient(client)));

                    if (purchaseList != null && purchaseList.size() != 0) {
                        session.setAttribute("purchaseList", purchaseList);
                    }
                    else {
                        session.setAttribute("purchaseList", null);
                    }
                    req.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(req, resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/");
    }

    private ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        if (client != null) {
            clientDTO.setLogin(client.getLogin());
            clientDTO.setPassword(client.getPassword());
            clientDTO.setRole(client.getRole());
            clientDTO.setlName(client.getlName());
            clientDTO.setfName(client.getfName());
            clientDTO.setmName(client.getmName());
            clientDTO.setAddress(client.getAddress());
            clientDTO.setPhoneNumber(client.getPhoneNumber());
            clientDTO.setBalance(client.getBalance());
            clientDTO.setDiscount(client.getDiscount());
            clientDTO.setPurchaseList(client.getPurchaseList());
            clientDTO.setBasketItemList(client.getBasketItemList());
        }
        else {
            return null;
        }
        return clientDTO;
    }

    private Client mapToClient(ClientDTO clientDTO) {
        Client client = new Client();
        if (clientDTO != null) {
            client.setLogin(clientDTO.getLogin());
            client.setPassword(clientDTO.getPassword());
            client.setRole(clientDTO.getRole());
            client.setlName(clientDTO.getlName());
            client.setfName(clientDTO.getfName());
            client.setmName(clientDTO.getmName());
            client.setAddress(clientDTO.getAddress());
            client.setPhoneNumber(clientDTO.getPhoneNumber());
            client.setBalance(clientDTO.getBalance());
            client.setDiscount(clientDTO.getDiscount());
            client.setPurchaseList(clientDTO.getPurchaseList());
            client.setBasketItemList(clientDTO.getBasketItemList());
        }
        else {
            return null;
        }
        return client;
    }
    
    private FlowerDTO mapToFlowerDTO(Flower flower) {
        FlowerDTO flowerDTO = new FlowerDTO();
        if (flower != null) {
            flowerDTO.setId(flower.getId());
            flowerDTO.setName(flower.getName());
            flowerDTO.setPrice(flower.getPrice());
            flowerDTO.setQuantity(flower.getQuantity());
        }
        else {
            return null;
        }
        return flowerDTO;
    }

    private List<FlowerDTO> mapToFlowerDTOList(List<Flower> flowerList) {
        List<FlowerDTO> flowerDTOList = new ArrayList<>(flowerList.size());
        for (Flower flower : flowerList) {
            flowerDTOList.add(mapToFlowerDTO(flower));
        }
        return flowerDTOList;
    }

    private PurchaseDTO mapToPurchaseDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        if (purchase != null) {
            purchaseDTO.setId(purchase.getId());
            purchaseDTO.setTotalPrice(purchase.getTotalPrice());
            purchaseDTO.setCreateDate(purchase.getCreateDate());
            purchaseDTO.setCloseDate(purchase.getCloseDate());
            purchaseDTO.setStatus(purchase.getStatus());
            purchaseDTO.setClient(mapToClientDTO(purchase.getClient()));
            purchaseDTO.setBasketItemList(purchase.getBasketItemList());
        }
        else {
            return null;
        }
        return purchaseDTO;
    }

    private Purchase mapToPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setId(purchaseDTO.getId());
        purchase.setTotalPrice(purchaseDTO.getTotalPrice());
        purchase.setCreateDate(purchaseDTO.getCreateDate());
        purchase.setCloseDate(purchaseDTO.getCloseDate());
        purchase.setStatus(purchaseDTO.getStatus());
        purchase.setClient(mapToClient(purchaseDTO.getClient()));
        purchase.setBasketItemList(purchaseDTO.getBasketItemList());
        return purchase;
    }

    private List<PurchaseDTO> mapToPurchaseDTOList(List<Purchase> purchaseList) {
        if (purchaseList != null) {
            List<PurchaseDTO> purchaseDTO = new ArrayList<>(purchaseList.size());
            for (Purchase purchase : purchaseList) {
                purchaseDTO.add(mapToPurchaseDTO(purchase));
            }
            return purchaseDTO;
        }
        else {
            return null;
        }
    }
}
