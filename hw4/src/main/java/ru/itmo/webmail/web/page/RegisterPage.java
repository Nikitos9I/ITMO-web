package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class RegisterPage extends GeneralPage {
    private UserService userService = new UserService();

    private void register(HttpServletRequest request, Map<String, Object> view) {
        HttpSession session = request.getSession();
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        try {
            userService.validateRegistration(user, password, confirmPassword);
        } catch (ValidationException e) {
            view.put("login", user.getLogin());
            view.put("email", user.getEmail());
            view.put("password", password);
            view.put("confirmPassword", confirmPassword);
            view.put("error", e.getMessage());
            return;
        }

        userService.register(user, password);
        session.setAttribute("userLogin", user.getLogin());
        session.setAttribute("userId", user.getId());
        throw new RedirectException("/index", "registrationDone");
    }

    private void action() {
        // No operations.
    }
}
