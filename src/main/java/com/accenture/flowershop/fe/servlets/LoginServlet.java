package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        clientService.loginPage(req, resp);

//        HttpSession session = req.getSession();
//        ClientDTO clientDTO = (ClientDTO) session.getAttribute("client");
//
//        if (clientDTO != null) {
//            resp.sendRedirect("/");
//        }
//        else {
//            String act = req.getParameter("act") != null ? req.getParameter("act") : "";
//
//            if (act.equals("Login")) {
//                String login = req.getParameter("login");
//                String password = req.getParameter("password");
//
//                clientDTO = mapToClientDTO(clientService.login(login, password));
//
//                if (clientDTO != null) {
//                    session.setAttribute("client", clientDTO);
//                    session.removeAttribute("error");
//
//                    if (clientDTO.getRole() != null && clientDTO.getRole().equals(ClientRole.ADMIN)) {
//                        resp.sendRedirect("/admin");
//                    }
//                    else {
//                        resp.sendRedirect("/");
//                    }
//                }
//                else {
//                    session.setAttribute("error", "Login or Password wrong!");
//                    resp.sendRedirect("/login");
//                }
//            }
//            else {
//                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
//            }
//        }
    }

//    private ClientDTO mapToClientDTO(Client client) {
//        ClientDTO clientDTO = new ClientDTO();
//        if (client != null) {
//            clientDTO.setLogin(client.getLogin());
//            clientDTO.setPassword(client.getPassword());
//            clientDTO.setRole(client.getRole());
//            clientDTO.setlName(client.getlName());
//            clientDTO.setfName(client.getfName());
//            clientDTO.setmName(client.getmName());
//            clientDTO.setAddress(client.getAddress());
//            clientDTO.setPhoneNumber(client.getPhoneNumber());
//            clientDTO.setBalance(client.getBalance());
//            clientDTO.setDiscount(client.getDiscount());
//            clientDTO.setPurchaseList(client.getPurchaseList());
//            clientDTO.setBasketItemList(client.getBasketItemList());
//        }
//        else {
//            return null;
//        }
//        return clientDTO;
//    }
}
