package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.NewsService;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServlet;
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

public class GeneralPage {

    private UserService userService = new UserService();
    private NewsService newsService = new NewsService();

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        view.put("userLogin",request.getSession().getAttribute("userLogin"));
        view.put("userId", request.getSession().getAttribute("userId"));
        view.put("newsList", newsService.findAll());
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }

}
