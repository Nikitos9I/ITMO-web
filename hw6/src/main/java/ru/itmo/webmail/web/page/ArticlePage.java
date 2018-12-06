package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
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

public class ArticlePage extends Page {

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    private Map<String, Object> article(HttpServletRequest request, Map<String, Object> view) {
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        try {
            getArticleService().isValid(title);
            getArticleService().isValid(text);
        } catch (ValidationException e) {
            view.put("error", e.getMessage());
            view.put("success", false);
            return view;
        }

        Article article = new Article();
        article.setUserId(getUser().getId());
        article.setTitle(title);
        article.setHidden(false);
        article.setText(text);

        getArticleService().saveArticle(article);
        view.put("success", true);
        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

}
