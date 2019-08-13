package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.PurchaseService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.ClientDTO;
import com.accenture.flowershop.fe.dto.PurchaseDTO;
import com.accenture.flowershop.fe.enums.ClientRole;
import com.accenture.flowershop.fe.enums.PurchaseStatus;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {

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
        ClientDTO clientDTO = (ClientDTO) session.getAttribute("client");

        if (clientDTO != null && clientDTO.getRole().equals(ClientRole.ADMIN)) {
            List<PurchaseDTO> purchaseList = mapToPurchaseDTOList(purchaseService.getAll());
            session.setAttribute("purchaseListAll", purchaseList);

            String act = req.getParameter("act");
            if (act != null) {
                if ("close".equals(act)) {
                    Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));

                    PurchaseDTO purchaseDTO = mapToPurchaseDTO(purchaseService.getById(id));
                    if (purchaseDTO != null && purchaseDTO.getStatus().equals(PurchaseStatus.PAID)) {
                        purchaseService.updateStatus(id, PurchaseStatus.CLOSED);
                    }

                    List<PurchaseDTO> purchaseDTOList = mapToPurchaseDTOList(purchaseService.getAll());
                    session.setAttribute("purchaseListAll", purchaseDTOList);
                    doPost(req, resp);
                }
            }
            else {
                req.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(req, resp);
            }
        }
        else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/admin");
    }

    private PurchaseDTO mapToPurchaseDTO(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        if (purchase != null) {
            purchaseDTO.setId(purchase.getId());
            purchaseDTO.setClient(mapToClientDTO(purchase.getClient()));
            purchaseDTO.setTotalPrice(purchase.getTotalPrice());
            purchaseDTO.setCreateDate(purchase.getCreateDate());
            purchaseDTO.setCloseDate(purchase.getCloseDate());
            purchaseDTO.setStatus(purchase.getStatus());
        }
        else {
            return null;
        }
        return purchaseDTO;
    }

    private List<PurchaseDTO> mapToPurchaseDTOList(List<Purchase> purchaseList) {
        List<PurchaseDTO> purchaseDTO = new ArrayList<>(purchaseList.size());
        for (Purchase purchase : purchaseList) {
            purchaseDTO.add(mapToPurchaseDTO(purchase));
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
