package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ? AND deleted = FALSE;";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get user with login " + login, ex);
        }

        if (user != null) {
            user.setRoles(getUserRolesFromResultSet(user.getId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create new user", ex);
        }

        insertUserRoles(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ? AND deleted = FALSE;";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get user with id = " + id, ex);
        }

        if (user != null) {
            user.setRoles(getUserRolesFromResultSet(user.getId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE deleted = FALSE;";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all users", ex);
        }

        for (User user: users) {
            user.setRoles(getUserRolesFromResultSet(user.getId()));
        }
        return users;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name = ?, login = ?, password = ? "
                + "WHERE user_id = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update user with id = "
                    + user.getId(), ex);
        }

        removeUserRoles(user.getId());
        insertUserRoles(user);
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET deleted = TRUE WHERE user_id = ?;";
        int itemsDeleted = 0;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            itemsDeleted = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete product with id = " + id, ex);
        }
        return (itemsDeleted == 1 && removeUserRoles(id));
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String userName = resultSet.getString("user_name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        return new User(userId, userName, login, password);
    }

    private Set<Role> getUserRolesFromResultSet(Long userId) {
        String query = "SELECT ur.role_id AS id, role_name AS name "
                + "FROM users_roles ur "
                + "INNER JOIN roles r ON ur.role_id = r.role_id "
                + "WHERE user_id = ?";
        Set<Role> roles = new HashSet<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("name"));
                role.setId(resultSet.getLong("id"));
                roles.add(role);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get roles of user with id = "
                    + userId, ex);
        }
        return roles;
    }

    private boolean insertUserRoles(User user) {
        for (Role role: user.getRoles()) {
            String query = "SELECT role_id FROM roles WHERE role_name = ?;";
            try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, role.getRoleName().name());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    role.setId(resultSet.getLong("role_id"));
                }
            } catch (SQLException ex) {
                throw new DataProcessingException("Can't get user role's id", ex);
            }

            String secondQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
            try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(secondQuery);
                statement.setLong(1, user.getId());
                statement.setLong(2, role.getId());
                int rolesInserted = statement.executeUpdate();
                if (rolesInserted > 0) {
                    return true;
                }
            } catch (SQLException ex) {
                throw new DataProcessingException("Can't add role"
                        + role.getRoleName().name() + " to new user", ex);
            }
        }
        return false;
    }

    private boolean removeUserRoles(Long userId) {
        String query = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            int rolesDeleted = statement.executeUpdate();
            if (rolesDeleted > 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete roles of user with id = "
                    + userId, ex);
        }
        return false;
    }
}
