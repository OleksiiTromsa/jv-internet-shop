package com.internet.shop.controllers.admin;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/users/delete")
public class DeleteUserController extends HttpServlet {
    public static final String USER_ID = "userId";
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = Long.valueOf(req.getParameter("id"));
        userService.delete(userId);
        shoppingCartService.delete(shoppingCartService.getByUser(userId).getId());
        HttpSession session = req.getSession();
        if (userId.equals(session.getAttribute(USER_ID))) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/user/login");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users/all");
    }
}
