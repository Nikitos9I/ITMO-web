package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ru.itmo.webmail.web.page
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class ConfirmPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        try {
            String actualSecret = request.getParameter("secret");
            Long userId = (Long) request.getSession().getAttribute("registeredUserId");
            String expectedSecret = getEmailConfirmationService().getSecret(userId);

            if (actualSecret.equals(expectedSecret)) {
                getUserService().find(userId).setConfirmed(true);
                getUserService().updateConfirmStatus(userId, true);
                throw new RedirectException("/index", "confirmationDone");
            }
        } catch (NullPointerException e) {
            throw new RedirectException("/index", "actionFault");
        }
    }

}
