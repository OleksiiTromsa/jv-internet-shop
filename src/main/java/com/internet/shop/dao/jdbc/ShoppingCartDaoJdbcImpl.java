package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getUserShoppingCart(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = FALSE";
        ShoppingCart cart = null;

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cart = getCartFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get shopping cart of user with id = "
                    + userId, ex);
        }
        if (cart != null) {
            cart.setProducts(getListOfProducts(cart.getId()));
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cart.setId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create shopping cart for user with id = "
                    + cart.getUserId(), ex);
        }
        insertProductsToShoppingCart(cart);
        return cart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ? AND deleted = FALSE";
        ShoppingCart cart = null;

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cart = getCartFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get shopping cart with id = "
                    + id, ex);
        }

        if (cart != null) {
            cart.setProducts(getListOfProducts(cart.getId()));
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts WHERE deleted = FALSE";
        List<ShoppingCart> carts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart cart = getCartFromResultSet(resultSet);
                carts.add(cart);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get list of all shopping carts", ex);
        }

        for (ShoppingCart cart: carts) {
            cart.setProducts(getListOfProducts(cart.getId()));
        }
        return carts;
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String query = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE cart_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cart.getUserId());
            statement.setLong(2, cart.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update shopping cart with id = "
                    + cart.getId(), ex);
        }

        removeAllProductsFromShoppingCart(cart.getId());
        insertProductsToShoppingCart(cart);
        return cart;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE shopping_carts SET deleted = TRUE WHERE cart_id = ?";
        int itemsDeleted = 0;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            itemsDeleted = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete shopping cart with id = "
                    + id, ex);
        }

        removeAllProductsFromShoppingCart(id);
        return itemsDeleted == 1;
    }

    private ShoppingCart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long cartId = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        return new ShoppingCart(cartId, userId);
    }

    private List<Product> getListOfProducts(Long cartId) {
        String query = "SELECT cp.product_id AS id, p.product_name AS name, p.price "
                + "FROM carts_products cp "
                + "INNER JOIN products p "
                + "ON cp.product_id = p.product_id "
                + "WHERE cp.cart_id = ?;";
        List<Product> products = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("id");
                String productName = resultSet.getString("name");
                Long price = resultSet.getLong("price");
                Product product = new Product(productId, productName, price);
                products.add(product);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get products from shopping cart with id = "
                    + cartId, ex);
        }
        return products;
    }

    private void removeAllProductsFromShoppingCart(Long cartId) {
        String query = "DELETE FROM carts_products WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't remove products from shopping cart with id = "
                    + cartId, ex);
        }
    }

    private void insertProductsToShoppingCart(ShoppingCart cart) {
        String query = "INSERT INTO carts_products (cart_id, product_id) VALUES (?, ?);";
        for (Product product: cart.getProducts()) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, cart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DataProcessingException("Can't insert products to shopping cart "
                        + "with id = " + cart.getId(), ex);
            }
        }
    }
}
