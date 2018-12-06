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

public class MyArticlesPage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index", "accessDenied");
        }
    }

    public Map<String, Object> updateHiddenStatus(HttpServletRequest request, Map<String, Object> view) {
        Boolean newStatus = request.getParameter("articleHiddenStatus").equals("Show");
        long articleId = Long.parseLong(request.getParameter("articleId").trim());

        if (getArticleService().findArticleById(articleId - 1).getUserId() != getUser().getId()) {
            throw new RedirectException("/index", "accessDenied");
        }

        getArticleService().updateHiddenStatus(articleId - 1, newStatus);
        view.put("newStatus", newStatus? "Hide" : "Show");
        view.put("success", true);
        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", getArticleService().findArticlesByUserId(getUser().getId()));
    }

}
