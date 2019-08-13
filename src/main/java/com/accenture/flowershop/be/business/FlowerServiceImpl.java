package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.FlowerDAO;
import com.accenture.flowershop.be.entity.BasketItem;
import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import com.accenture.flowershop.fe.dto.BasketItemDTO;
import com.accenture.flowershop.fe.dto.ClientDTO;
import com.accenture.flowershop.fe.dto.FlowerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {

    private final FlowerDAO flowerDAO;
    private final BasketItemService basketItemService;

    @Autowired
    public FlowerServiceImpl(FlowerDAO flowerDAO, BasketItemService basketItemService) {
        this.flowerDAO = flowerDAO;
        this.basketItemService = basketItemService;
    }

    @Override
    public Flower getById(Long id) {
        if (id != null) {
            try {
                return flowerDAO.getById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Flower> getByFilter(FlowerFilter flowerFilter) {
        try {
            return flowerDAO.getByFilter(flowerFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public Flower updateQuantity(Long id, Integer quantity) {
        try {
            Flower flower = flowerDAO.getById(id);
            flower.setQuantity(quantity);
            return flowerDAO.update(flower);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onSearchButton(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        String searchName = req.getParameter("name").equals("") ? null : req.getParameter("name");
        BigDecimal searchFrom = req.getParameter("from").equals("") ? null : new BigDecimal(req.getParameter("from"));
        BigDecimal searchTo = req.getParameter("to").equals("") ? null : new BigDecimal(req.getParameter("to"));

        session.setAttribute("name", searchName);
        session.setAttribute("from", searchFrom);
        session.setAttribute("to", searchTo);

        FlowerFilter flowerFilter = new FlowerFilter();
        flowerFilter.setName(searchName);
        flowerFilter.setFromPrice(searchFrom);
        flowerFilter.setToPrice(searchTo);

        List<FlowerDTO> flowersList1 = mapToFlowerDTOList(getByFilter(flowerFilter));
        session.setAttribute("flowersList", flowersList1);
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

    @Override
    public void onAddButton(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        ClientDTO client = (ClientDTO) session.getAttribute("client");

        Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));
        int quantityToBuy = req.getParameter("quantityToBuy").equals("") ? 0 : Integer.parseInt(req.getParameter("quantityToBuy"));

        FlowerDTO flowerDTO = mapToFlowerDTO(getById(id));



        List<BasketItemDTO> basketItemList = new ArrayList<>();
        if (session.getAttribute("basketItemList") != null) {
            basketItemList = (List<BasketItemDTO>) session.getAttribute("basketItemList");
        }

        boolean isFound = false;
        for (BasketItemDTO basketItemDTO : basketItemList) {
            if (flowerDTO != null && basketItemDTO.getFlower().getName().equals(flowerDTO.getName())) {
                Integer tempQuantity = basketItemDTO.getQuantityToBuy() + quantityToBuy;
                basketItemDTO.setQuantityToBuy(tempQuantity);
                BigDecimal sum = basketItemDTO.getFlower().getPrice().multiply(new BigDecimal(basketItemDTO.getQuantityToBuy()));
                basketItemDTO.setSum(sum);

                basketItemService.updateQuantity(basketItemDTO.getId(), quantityToBuy);

                isFound = true;
                break;
            }
        }

        if (!isFound) {
            BasketItemDTO basketItem = new BasketItemDTO();
            basketItem.setFlower(flowerDTO);
            basketItem.setQuantityToBuy(quantityToBuy);
            BigDecimal sum = basketItem.getFlower().getPrice().multiply(new BigDecimal(basketItem.getQuantityToBuy()));
            basketItem.setSum(sum);

            BasketItem basketItemTemp = basketItemService.add(mapToBasket(basketItem));
            basketItemList.add(mapToBasketItemDTO(basketItemTemp));
        }

        BigDecimal summary = BigDecimal.ZERO;
        for (BasketItemDTO basketItemDTO : basketItemList) {
            summary = summary.add(basketItemDTO.getSum().subtract(((basketItemDTO.getSum().multiply(new BigDecimal(client.getDiscount()))).divide(new BigDecimal(100)))));
            session.setAttribute("sum", summary);
        }



        session.setAttribute("basketItemList", basketItemList);
    }

    @Override
    public void onRemoveButton(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        ClientDTO client = (ClientDTO) session.getAttribute("client");

        Long id = req.getParameter("id").equals("") ? null : Long.parseLong(req.getParameter("id"));

        List<BasketItemDTO> basketItemList = new ArrayList<>();
        if (session.getAttribute("basketItemList") != null) {
            basketItemList = (List<BasketItemDTO>) session.getAttribute("basketItemList");
        }

        for (BasketItemDTO basketItemDTO : basketItemList) {
            if (basketItemDTO.getId().equals(id)) {
                basketItemList.remove(basketItemDTO);

                basketItemService.remove(id);

                break;
            }
        }

        if (basketItemList.size() == 0) {
            basketItemList = null;
        }

        session.setAttribute("basketItemList", basketItemList);
        BigDecimal summary = BigDecimal.ZERO;
        if (basketItemList != null) {
            for (BasketItemDTO basketItemDTO : basketItemList) {
                summary = summary.add(basketItemDTO.getSum().subtract(((basketItemDTO.getSum().multiply(new BigDecimal(client.getDiscount()))).divide(new BigDecimal(100)))));
                session.setAttribute("sum", summary);
            }
        }
        else {
            session.setAttribute("sum", null);
        }
    }

    private BasketItem mapToBasket(BasketItemDTO basketItemDTO) {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantityToBuy(basketItemDTO.getQuantityToBuy());
        basketItem.setSum(basketItemDTO.getSum());
        basketItem.setFlower(mapToFlower(basketItemDTO.getFlower()));
        return basketItem;
    }

    private Flower mapToFlower(FlowerDTO flowerDTO) {
        Flower flower = new Flower();
        flower.setId(flowerDTO.getId());
        flower.setName(flowerDTO.getName());
        flower.setPrice(flowerDTO.getPrice());
        flower.setQuantity(flowerDTO.getQuantity());
        return flower;
    }

    private BasketItemDTO mapToBasketItemDTO(BasketItem basketItem) {
        BasketItemDTO basketItemDTO = new BasketItemDTO();
        basketItemDTO.setId(basketItem.getId());
        basketItemDTO.setQuantityToBuy(basketItem.getQuantityToBuy());
        basketItemDTO.setSum(basketItem.getSum());
        basketItemDTO.setFlower(mapToFlowerDTO(basketItem.getFlower()));
        return basketItemDTO;
    }
}
