package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;

/**
 * ru.itmo.webmail.model.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class EventService {

    private EventRepository eventRepository = new EventRepositoryImpl();

    public void saveEvent(Event event) {
        eventRepository.setEvent(event);
    }

}
