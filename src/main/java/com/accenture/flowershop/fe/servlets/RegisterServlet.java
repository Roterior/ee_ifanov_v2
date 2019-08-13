package com.accenture.flowershop.fe.servlets;

import com.accenture.flowershop.be.business.ClientService;
import com.accenture.flowershop.be.entity.Client;
import com.accenture.flowershop.fe.dto.ClientDTO;
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

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ClientDTO clientDTO = (ClientDTO) session.getAttribute("client");

        if (clientDTO == null) {
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").equals("") ? null : req.getParameter("login");
        String password = req.getParameter("password").equals("") ? null : req.getParameter("password");
        String fName = req.getParameter("fname").equals("") ? null : req.getParameter("fname");
        String lName = req.getParameter("lname").equals("") ? null : req.getParameter("lname");
        String mName = req.getParameter("mname").equals("") ? null : req.getParameter("mname");
        String phoneNumber = req.getParameter("phonenumber").equals("") ? null : req.getParameter("phonenumber");
        String address = req.getParameter("address").equals("") ? null : req.getParameter("address");

        ClientDTO clientDTO = mapToClientDTO(clientService.register(new Client(login, password, lName, fName, mName, address, phoneNumber)));
        if (clientDTO != null) {
            req.removeAttribute("error2");
            resp.sendRedirect("/login");
        }
        else {
            req.setAttribute("error2", "Something went wrong!");
            req.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(req, resp);
        }
    }

    private ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        if (client != null) {
            clientDTO.setLogin(client.getLogin());
            clientDTO.setPassword(client.getPassword());
//            clientDTO.setRole(client.getRole());
            clientDTO.setlName(client.getlName());
            clientDTO.setfName(client.getfName());
            clientDTO.setmName(client.getmName());
            clientDTO.setAddress(client.getAddress());
            clientDTO.setPhoneNumber(client.getPhoneNumber());
//            clientDTO.setBalance(client.getBalance());
            clientDTO.setDiscount(client.getDiscount());
        }
        else {
            return null;
        }
        return clientDTO;
    }
}
