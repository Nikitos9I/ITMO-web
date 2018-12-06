package ru.itmo.wm4.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.wm4.domain.Role;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.security.AnyRole;

import javax.servlet.http.HttpSession;

/**
 * ru.itmo.wm4.controller
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

@Component
public class CommentsPage extends Page {

    @AnyRole(Role.Name.ADMIN)
    @GetMapping(path = "/comments")
    public String notices(Model model, HttpSession httpSession) {
        User user = getUser(httpSession);
        model.addAttribute("comments", user.getComments());
        return "CommentsPage";
    }

}
