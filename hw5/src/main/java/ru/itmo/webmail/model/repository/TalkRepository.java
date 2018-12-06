package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Message;

import java.util.List;

public interface TalkRepository {
    void saveMessage(Message message);
    List<Message> findAllMessages(Long userId);
}
