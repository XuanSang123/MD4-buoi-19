package org.example.baitap.controller;
import org.example.baitap.dao.order.IOrderDao;
import org.example.baitap.dao.orderdetails.OrderDetailsDao;
import org.example.baitap.dao.product1.IProductDao1;
import org.example.baitap.models.Order;
import org.example.baitap.models.OrderDetails;
import org.example.baitap.models.Product1;
import org.example.baitap.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class CartController {
    @Autowired
    private OrderDetailsDao orderDetailsDao;
    @Autowired
    private IProductDao1 iProductDao1;
    @Autowired
    private IOrderDao orderDao;

    @GetMapping("/add-to-cart/{id}")
    public String getForm(@PathVariable int id, Model model) {
        Product1 product1 = iProductDao1.findById(id);
        List<OrderDetails> orderDetails = orderDetailsDao.getAll();
        model.addAttribute("product1", product1);
        model.addAttribute("orderDetails", orderDetails);
        return "add-to-cart";
    }


    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable int id, @RequestParam int quantity, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        Product1 product1 = iProductDao1.findById(id);
        Order order = new Order();
        order.setUserId(userId);
        order.setTotals(product1.getPrice() * quantity);
        orderDao.save(order);

        int orderId = order.getId();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderId);
        orderDetails.setProductId(product1.getId());
        orderDetails.setQuantity(quantity);
        orderDetails.setPrice(product1.getPrice() * quantity);
        orderDetailsDao.save(orderDetails);
        List<OrderDetails> cartItems = (List<OrderDetails>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(orderDetails);
        session.setAttribute("cartItems", cartItems);

        return "redirect:/order/cart-order";
    }


    @GetMapping("/cart-order")
    public String viewCart(Model model, HttpSession session) {
        List<OrderDetails> cartItems = (List<OrderDetails>) session.getAttribute("cartItems");
        double totalAmount = 0.0;

        if (cartItems != null) {
            for (OrderDetails item : cartItems) {
                totalAmount += item.getPrice() * item.getQuantity();
                // Lấy thông tin sản phẩm từ ID
                Product1 product = iProductDao1.findById(item.getProductId());
                item.setProduct(product); // Gán sản phẩm vào thuộc tính
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "cart-order";
    }


    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        List<OrderDetails> cartItems = (List<OrderDetails>) session.getAttribute("cartItems");
        double totalAmount = 0.0;

        if (cartItems != null) {
            for (OrderDetails item : cartItems) {
                totalAmount += item.getPrice() * item.getQuantity();
            }
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "invoice";
    }

}

