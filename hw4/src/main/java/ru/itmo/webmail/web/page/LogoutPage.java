package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ru.itmo.webmail.web.page
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class LogoutPage extends GeneralPage {

    private void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        try {
            session.removeAttribute("userId");
            session.removeAttribute("userLogin");
        } catch (NullPointerException e) {
            throw new RedirectException("/index", "noActiveUser");
        }

        throw new RedirectException("/index", "logoutDone");
    }

    private void action() {
        // No operations.
    }
}
