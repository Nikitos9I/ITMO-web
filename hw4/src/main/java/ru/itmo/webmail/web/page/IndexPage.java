package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends GeneralPage {
    private void action() {
        // No operations.
    }

    private void authorizationDone(Map<String, Object> view) {
        view.put("message", "You have been authorized");
    }

    private void registrationDone(Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private void logoutDone(Map<String, Object> view) {
        view.put("message", "You have been logout");
    }
}
