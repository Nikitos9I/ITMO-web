package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.form.NoticeMini;
import ru.itmo.wm4.form.validator.NoticeValidator;
import ru.itmo.wm4.service.NoticeService;

import javax.validation.Valid;

/**
 * ru.itmo.wm4.controller
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeValidator noticeValidator;

    public NoticePage(NoticeService noticeService, NoticeValidator noticeValidator) {
        this.noticeService = noticeService;
        this.noticeValidator = noticeValidator;
    }

    @InitBinder("NoticeMini")
    public void initRegisterFormBinder(WebDataBinder binder) {
        binder.addValidators(noticeValidator);
    }

    @GetMapping(path = "/notice")
    public String registerGet(Model model) {
        model.addAttribute("notice", new NoticeMini());
        return "NoticePage";
    }

    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("notice") Notice notice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.save(notice);

        return "redirect:/";
    }
}
