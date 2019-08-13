package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.PurchaseDAO;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.*;
import com.accenture.flowershop.fe.enums.BasketItemStatus;
import com.accenture.flowershop.fe.enums.PurchaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDAO purchaseDAO;
    private final FlowerService flowerService;
    private final ClientService clientService;
    private final BasketItemService basketItemService;

    @Autowired
    public PurchaseServiceImpl(PurchaseDAO purchaseDAO, FlowerService flowerService, ClientService clientService, BasketItemService basketItemService) {
        this.purchaseDAO = purchaseDAO;
        this.flowerService = flowerService;
        this.clientService = clientService;
        this.basketItemService = basketItemService;
    }

    @Override
    public Purchase add(Purchase purchase) {
        purchase.setCreateDate(LocalDate.now());
        purchase.setStatus(PurchaseStatus.CREATED);
        purchase.setCurrency(CurrencyType.find("643"));
        return purchaseDAO.add(purchase);
    }

    @Override
    public List<Purchase> getAll() {
        try {
            return purchaseDAO.getAll();
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Purchase getById(Long id) {
        try {
            return purchaseDAO.getById(id);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Purchase> getByLogin(String clientLogin) {
        try {
            return purchaseDAO.getByLogin(clientLogin);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Purchase updateCloseDateAndStatus(Long id) {
        try {
            Purchase purchase = purchaseDAO.getById(id);
            purchase.setStatus(PurchaseStatus.PAID);
            purchase.setCloseDate(LocalDate.now());
            return purchaseDAO.update(purchase);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public Purchase updateStatus(Long id, PurchaseStatus status) {
        try {
            Purchase purchase = purchaseDAO.getById(id);
            purchase.setStatus(status);
            return purchaseDAO.update(purchase);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void onOrderButton(HttpServletRequest req, HttpServletResponse resp, PurchaseDTO purchase) {
//        purchase.setCurrency(CurrencyType.find("643"));
        HttpSession session = req.getSession();
        ClientDTO client = (ClientDTO) session.getAttribute("client");

        List<BasketItemDTO> basketItemList = (List<BasketItemDTO>) session.getAttribute("basketItemList");
        for (BasketItemDTO basketItemDTO : basketItemList) {
            int quantityBuy = basketItemDTO.getQuantityToBuy();
            FlowerDTO flowerDTO = mapToFlowerDTO(flowerService.getById(basketItemDTO.getId()));
            if (flowerDTO != null) {
                int finalQuantity = flowerDTO.getQuantity() - quantityBuy;
                flowerService.updateQuantity(basketItemDTO.getId(), finalQuantity);
            }

            basketItemService.updateStatus(basketItemDTO.getId(), BasketItemStatus.ORDERED);
        }

        add(mapToPurchase(purchase));
        List<PurchaseDTO> purchaseList1 = mapToPurchaseDTOList(getByLogin(client.getLogin()));

        session.setAttribute("purchaseList", purchaseList1);
        session.removeAttribute("basketItemList");
        session.removeAttribute("sum");
    }

    @Transactional
    @Override
    public void onPayButton(HttpServletRequest req, HttpServletResponse resp, PurchaseDTO purchase) {
        HttpSession session = req.getSession();
        ClientDTO client = (ClientDTO) session.getAttribute("client");

        Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));

        updateCloseDateAndStatus(id);
        List<PurchaseDTO> purchaseList = mapToPurchaseDTOList(getByLogin(client.getLogin()));

        if (purchaseList != null) {
            session.setAttribute("purchaseList", purchaseList);
        }

        BigDecimal finalBalance = client.getBalance().subtract(purchase.getTotalPrice());
        clientService.updateBalance(client.getLogin(), finalBalance);
        client.setBalance(finalBalance);

        session.setAttribute("client", client);
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
}
