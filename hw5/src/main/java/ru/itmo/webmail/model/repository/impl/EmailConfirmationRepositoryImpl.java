package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EmailConfirmationRepository;

import javax.sql.DataSource;
import java.sql.*;

/**
 * ru.itmo.webmail.model.repository.impl
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class EmailConfirmationRepositoryImpl implements EmailConfirmationRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(Long userId, String secret) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO EmailConfirmation (userId, secret, creationTime) VALUES (?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, userId);
                statement.setString(2, secret);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save confirmation");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save confirmation", e);
        }
    }

    @Override
    public String getSecret(Long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT secret FROM EmailConfirmation WHERE userId=?")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(1);
                    }
                }

                throw new RepositoryException("Can't find secret string by userId");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find secret string by userId", e);
        }
    }
}
