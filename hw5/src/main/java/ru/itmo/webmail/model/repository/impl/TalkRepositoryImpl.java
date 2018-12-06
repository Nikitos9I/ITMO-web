package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Message;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ru.itmo.webmail.model.repository.impl
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class TalkRepositoryImpl implements TalkRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void saveMessage(Message message) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Talk (sourceUserId, targetUserId, text, creationTime) VALUES (?, ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, message.getSourceUserId());
                statement.setLong(2, message.getTargetUserId());
                statement.setString(3, message.getText());
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        message.setId(generatedIdResultSet.getLong(1));
                        message.setCreationTime(findCreationTime(message.getId()));
                    } else {
                        throw new RepositoryException("Can't find id of saved Message");
                    }
                } else {
                    throw new RepositoryException("Can't save Message");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Message", e);
        }
    }

    @Override
    public List<Message> findAllMessages(Long userId) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Talk WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime DESC")) {
                statement.setLong(1, userId);
                statement.setLong(2, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        messages.add(toMessage(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all messages", e);
        }

        return messages;
    }

    private Message toMessage(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Message message = new Message();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                message.setId(resultSet.getLong(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                message.setSourceUserId(resultSet.getLong(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                message.setTargetUserId(resultSet.getLong(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                message.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                message.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column Message." + columnName + "'.");
            }
        }

        return message;
    }

    private Date findCreationTime(long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM Talk WHERE id=?")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't find Message.creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Message.creationTime by id.", e);
        }
    }

}
