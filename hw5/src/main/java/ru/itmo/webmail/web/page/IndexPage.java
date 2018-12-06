package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
        view.put("confirmation", getEmailConfirmationService().getSecret((Long) request.getSession().getAttribute("registeredUserId")));
    }

    private void confirmationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You successfully confirmed your email");
    }

    private void actionFault(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You should repeat your last action");
    }
}
