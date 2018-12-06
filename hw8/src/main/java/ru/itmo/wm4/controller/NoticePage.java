package ru.itmo.wm4.controller;

import com.sun.deploy.net.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Role;
import ru.itmo.wm4.security.AnyRole;
import ru.itmo.wm4.security.Guest;
import ru.itmo.wm4.service.CommentService;
import ru.itmo.wm4.service.NoticeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final CommentService commentService;

    public NoticePage(NoticeService noticeService, CommentService commentService) {
        this.noticeService = noticeService;
        this.commentService = commentService;
    }

    @AnyRole(Role.Name.ADMIN)
    @GetMapping(path = "/notice")
    public String noticeGet(Model model) {
        model.addAttribute("notice", new Notice());
        return "NoticePage";
    }

    @AnyRole(Role.Name.ADMIN)
    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("notice") Notice notice,
                             BindingResult bindingResult, HttpSession httpSession, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        getNoticeService().save(notice, getUser(httpSession));
        getNoticeService().addTags(request.getParameter("tags").split(" "), notice.getId());
        return "redirect:/notices";
    }

    @AnyRole(Role.Name.ADMIN)
    @Guest
    @RequestMapping(value = "/notice/{noticeId}", method = RequestMethod.GET)
    public String noticeGetOne(Model model, @PathVariable Long noticeId) {
        Notice notice = noticeService.findById(noticeId);
        if (notice != null) {
            model.addAttribute("notice", notice);
            model.addAttribute("comments", notice.getComments());
            model.addAttribute("tags", notice.getTags());
            model.addAttribute("addComment", new Comment());
        } else {
            model.addAttribute("noSuchNotice", true);
        }

        return "CurrentNoticePage";
    }

    @AnyRole(Role.Name.ADMIN)
    @RequestMapping(value = "/notice/{noticeId}", method = RequestMethod.POST)
    public String noticePostOne(@Valid @ModelAttribute("comment") Comment comment, @PathVariable Long noticeId,
                                BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "CurrentNoticePage";
        }

        commentService.save(comment, noticeService.findById(noticeId), getUser(httpSession));

        return "redirect:/notice/{noticeId}";
    }
}
