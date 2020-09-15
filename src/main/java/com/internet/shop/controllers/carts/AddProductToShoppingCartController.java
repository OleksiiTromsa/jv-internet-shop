package com.internet.shop.controllers.carts;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productIdString = req.getParameter("id");
        Long productId = Long.valueOf(productIdString);
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        shoppingCartService.addProduct(shoppingCartService.getByUser(userId),
                productService.get(productId));
        req.setAttribute("addProductSuccessMessage", "Product was added to the shopping cart.");
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }
}
