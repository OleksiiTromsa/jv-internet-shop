package com.internet.shop.web.filters;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/admin/menu", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/users/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/users/orders", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/products/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/products/add", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/products/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/orders/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/admin/orders/delete", List.of(Role.RoleName.ADMIN));

        protectedUrls.put("/orders/complete", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-carts/get", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-carts/products/add", List.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-carts/products/delete", List.of(Role.RoleName.USER));
        protectedUrls.put("/users/orders", List.of(Role.RoleName.USER));
        protectedUrls.put("/products/all", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestUrl = req.getServletPath();

        if (!protectedUrls.containsKey(requestUrl)) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(requestUrl))) {
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole: authorizedRoles) {
            for (Role userRole: user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
