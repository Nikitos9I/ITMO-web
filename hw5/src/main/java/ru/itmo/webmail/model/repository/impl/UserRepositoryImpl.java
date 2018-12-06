package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public User find(long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE id=?")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id.", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE login=?")) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by login.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE email=?")) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by email", e);
        }
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE login=? AND passwordSha=?")) {
                statement.setString(1, login);
                statement.setString(2, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id and passwordSha.", e);
        }
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE email=? AND passwordSha=?")) {
                statement.setString(1, email);
                statement.setString(2, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id and passwordSha.", e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User ORDER BY id")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        users.add(toUser(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all users.", e);
        }
        return users;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                user.setId(resultSet.getLong(i));
            } else if ("login".equalsIgnoreCase(columnName)) {
                user.setLogin(resultSet.getString(i));
            } else if ("email".equalsIgnoreCase(columnName)) {
                user.setEmail(resultSet.getString(i));
            } else if ("emailConfirmation".equalsIgnoreCase(columnName)) {
                user.setConfirmed(resultSet.getBoolean(i));
            } else if ("passwordSha".equalsIgnoreCase(columnName)) {
                // No operations.
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                user.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO User (login, email, emailConfirmation, passwordSha, creationTime) VALUES (?, ?, ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getEmail());
                statement.setBoolean(3, user.isConfirmed());
                statement.setString(4, passwordSha);
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        user.setId(generatedIdResultSet.getLong(1));
                        user.setCreationTime(findCreationTime(user.getId()));
                    } else {
                        throw new RepositoryException("Can't find id of saved User.");
                    }
                } else {
                    throw new RepositoryException("Can't save User.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public void updateConfirmStatus(Long userId, boolean value) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE User SET emailConfirmation=? WHERE id=? ",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setBoolean(1, value);
                statement.setLong(2, userId);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't update email confirmation status");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    private Date findCreationTime(long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM User WHERE id=?")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't find User.creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.creationTime by id.", e);
        }
    }
}
