package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Message;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

/**
 * ru.itmo.webmail.model.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class TalkService {

    private TalkRepository talkRepository = new TalkRepositoryImpl();

    public void sendMessage(Message message) {
        talkRepository.saveMessage(message);
    }

    public List<Message> findAll(Long userId) {
        return talkRepository.findAllMessages(userId);
    }
}
