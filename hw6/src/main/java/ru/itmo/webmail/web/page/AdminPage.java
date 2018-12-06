package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ru.itmo.webmail.web.page
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class AdminPage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index", "accessDenied");
        }
    }

    private Boolean checkIsAdmin(HttpServletRequest request, Map<String, Object> view) {
        return getUser().isAdmin();
    }

    private Map<String, Object> changePrivilege(HttpServletRequest request, Map<String, Object> view) {
        Long userId = Long.parseLong(request.getParameter("userId").trim());
        Boolean isAdmin = request.getParameter("userAdminStatus").equals("enable");

        if (!getUser().isAdmin()) {
            throw new RedirectException("/index", "accessDenied");
        }

        getUserService().changePrivilege(userId, isAdmin);
        getUser().setAdmin(isAdmin);
        view.put("isCurrentUserAdmin", getUser().isAdmin());
        view.put("newStatus", isAdmin? "disable" : "enable");
        view.put("newStatusAdmin", isAdmin? "YES" : "NO");
        view.put("success", true);
        return view;
    }

    private List<User> find(HttpServletRequest request, Map<String, Object> view) {
        return getUserService().findAll();
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", getUserService().findAll());
        view.put("isCurrentUserAdmin", getUser().isAdmin());
    }

}
