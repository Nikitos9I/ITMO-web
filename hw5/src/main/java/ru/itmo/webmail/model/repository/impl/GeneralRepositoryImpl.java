package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

/**
 * ru.itmo.webmail.model.repository.impl
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public abstract class GeneralRepositoryImpl<T> {

    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public void save(String sql, Object ... args) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].getClass().equals(Long.class)) {
                        statement.setLong(i + 1, (Long) args[i]);
                    }

                    if (args[i].getClass().equals(String.class)) {
                        statement.setString(i + 1, (String) args[i]);
                    }

                    if (args[i].getClass().equals(Long.class)) {
                        statement.setBoolean(i + 1, (Boolean) args[i]);
                    }
                }

                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        toInstance(statement.getMetaData(), generatedIdResultSet);
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

    public abstract T toInstance(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
}
