package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutPage extends Page {
    private final String logoutKey = "LOGOUT";

    private void action(HttpServletRequest request, Map<String, Object> view) {
        Event event = new Event();
        event.setUserId((Long) request.getSession().getAttribute("userId"));
        event.setType(logoutKey);
        getEventService().saveEvent(event);
        request.getSession().removeAttribute(USER_ID_SESSION_KEY);
        throw new RedirectException("/index");
    }
}
