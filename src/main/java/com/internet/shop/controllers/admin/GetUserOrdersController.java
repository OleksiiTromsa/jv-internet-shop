package com.internet.shop.controllers.admin;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/users/orders")
public class GetUserOrdersController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = Long.valueOf(req.getParameter("id"));
        List<Order> orders = orderService.getUserOrders(userId);
        req.setAttribute("userName", userService.get(userId).getName());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/admin/users/orders.jsp").forward(req, resp);
    }
}
