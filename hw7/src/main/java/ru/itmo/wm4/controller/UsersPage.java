package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public String main(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping(path = "/users")
    public String switchStatus(HttpServletRequest request) {
        User user = getUser(request.getSession());

        if (user == null) {
            return "redirect:/";
        }

        long userId = Long.parseLong(request.getParameter("userId").trim());
        User actualUser = userService.findById(userId);

        if (actualUser != null) {
            boolean newDisabled = !actualUser.getDisabled();
            userService.updateDisabledStatus(userId, newDisabled);
            if (newDisabled && user.getId() == userId) {
                unsetUser(request.getSession());
            }
        }

        return "redirect:/users";
    }
}
