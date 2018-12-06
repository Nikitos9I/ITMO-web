package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.service.UserService;

/**
 * ru.itmo.wm4.controller
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public String main(Model model, @PathVariable Long userId) {
        User actualUser = userService.findById(userId);
        if (actualUser != null) {
            model.addAttribute("user", actualUser);
        } else {
            model.addAttribute("noSuchUser", true);
        }
        return "UserPage";
    }
}
