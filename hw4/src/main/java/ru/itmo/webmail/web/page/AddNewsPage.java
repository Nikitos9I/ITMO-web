package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.service.NewsService;
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

public class AddNewsPage extends GeneralPage {

    private NewsService newsService = new NewsService();

    private void addNews(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String[] text = request.getParameter("text-news").split("[\n]");
        Long userId = (long) session.getAttribute("userId");
        News news = new News();
        news.setUserId(userId);
        news.setText(getCurrentTextNews(text));

        newsService.save(news);

        throw new RedirectException("/index");
    }

    private String getCurrentTextNews(String[] literals) {
        StringBuilder sb = new StringBuilder();
        for (String current : literals) {
            sb.append(current).append("<br>");
        }

        return sb.toString();
    }

    private void action() {
        // No operations.
    }

}
