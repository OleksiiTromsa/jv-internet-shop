package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
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
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ?;";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all orders of user with id = "
                    + userId, ex);
        }

        for (Order order: orders) {
            order.setProducts(getListOfProducts(order.getId()));
        }
        return orders;
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create new order of user with id = "
                    + order.getUserId(), ex);
        }
        insertProductsToOrder(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ? AND deleted = FALSE";
        Order order = null;

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get order with id = "
                    + id, ex);
        }

        if (order != null) {
            order.setProducts(getListOfProducts(order.getId()));
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders WHERE deleted = FALSE";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get list of all orders", ex);
        }

        for (Order order: orders) {
            order.setProducts(getListOfProducts(order.getId()));
        }
        return orders;
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update order with id = "
                    + order.getId(), ex);
        }

        removeAllProductsFromOrder(order.getId());
        insertProductsToOrder(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE orders SET deleted = TRUE WHERE order_id = ?";
        int itemsDeleted = 0;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            itemsDeleted = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete order with id = "
                    + id, ex);
        }

        removeAllProductsFromOrder(id);
        return itemsDeleted == 1;
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        return new Order(orderId, userId);
    }

    private List<Product> getListOfProducts(Long orderId) {
        String query = "SELECT op.product_id AS id, p.product_name AS name, p.price "
                + "FROM orders_products op "
                + "INNER JOIN products p "
                + "ON op.product_id = p.product_id "
                + "WHERE op.order_id = ?;";
        List<Product> products = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("id");
                String productName = resultSet.getString("name");
                Long price = resultSet.getLong("price");
                Product product = new Product(productId, productName, price);
                products.add(product);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get products from order with id = "
                    + orderId, ex);
        }
        return products;
    }

    private void removeAllProductsFromOrder(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't remove products from order with id = "
                    + orderId, ex);
        }
    }

    private void insertProductsToOrder(Order order) {
        String query = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        for (Product product: order.getProducts()) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DataProcessingException("Can't insert products to order with id = "
                        + order.getId(), ex);
            }
        }
    }
}
