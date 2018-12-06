package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * ru.itmo.webmail.web.page
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class EnterPage extends GeneralPage {

    private UserService userService = new UserService();

    private void enter(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            userService.validateAuthorization(login, password);
            User user = userService.auth(login, password);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLogin", user.getLogin());
        } catch (ValidationException e) {
            view.put("login", login);
            view.put("password", password);
            view.put("error", e.getMessage());
            return;
        }

        throw new RedirectException("/index", "authorizationDone");
    }

    private void action() {
        // No operations.
    }

}
